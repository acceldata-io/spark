/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.sql.hive.thriftserver

import java.util.Locale
import java.util.concurrent.atomic.AtomicBoolean

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

import org.apache.hadoop.hive.conf.HiveConf
import org.apache.hadoop.hive.conf.HiveConf.ConfVars
import org.apache.hadoop.hive.ql.session.SessionState
import org.apache.hive.service.cli.thrift.{ThriftBinaryCLIService, ThriftHttpCLIService}
import org.apache.hive.service.server.HiveServer2

import org.apache.spark.SparkContext
import org.apache.spark.annotation.DeveloperApi
import org.apache.spark.internal.Logging
import org.apache.spark.scheduler.{SparkListener, SparkListenerApplicationEnd, SparkListenerJobStart}
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.hive.HiveUtils
import org.apache.spark.sql.hive.thriftserver.ReflectionUtils._
import org.apache.spark.sql.hive.thriftserver.ui.ThriftServerTab
import org.apache.spark.sql.internal.SQLConf
import org.apache.spark.util.{ShutdownHookManager, Utils}

/**
 * The main entry point for the Spark SQL port of HiveServer2.  Starts up a `SparkSQLContext` and a
 * `HiveThriftServer2` thrift server.
 */
object HiveThriftServer2 extends Logging {
  var uiTab: Option[ThriftServerTab] = None
  var listener: HiveThriftServer2Listener = _

  /**
   * :: DeveloperApi ::
   * Starts a new thrift server with the given context.
   */
  @DeveloperApi
  /**
   * The HiveConf that HiveServer2.init() needs.
   *
   * This used to be taken from HiveUtils.newClientForExecution(...).conf: Spark stood up a
   * complete "execution Hive" client - SessionState plus an embedded in-memory Derby metastore -
   * purely to read one HiveConf off it, then discarded the client. Nothing else ever used it;
   * the only two references were the `server.init(executionHive.conf)` calls below.
   *
   * ODP-7072/ODP-6988: creating that client is not merely wasteful, on this stack it is fatal.
   * Whenever spark.sql.hive.metastore.jars enables IsolatedClientLoader isolation, bringing up
   * the embedded metastore fails during schema creation with
   *   NucleusException: The java type java.lang.Long (jdbc-type="", sql-type="")
   *                     cant be mapped for this datastore. No mapping is available.
   *   -> RuntimeException: Unable to instantiate SessionHiveMetaStoreClient
   * and HiveThriftServer2 exits before binding a port. That was the only reason the Thrift
   * Server had to be pointed at an inert (empty) metastore-jars directory, which in turn left it
   * with Spark's builtin Hive 1.2.1 client and therefore unable to call get_table against a
   * Hive 4 metastore. spark-shell/spark-submit/pyspark never call newClientForExecution, which
   * is why only the Thrift Server was affected. Spark 3 removed executionHive entirely.
   *
   * The conf is assembled exactly as HiveClientImpl.newState() does - HiveConf + hadoopConf +
   * every Spark conf entry + HiveUtils.newTemporaryConfiguration - so the value handed to
   * HiveServer2 is unchanged. The only step skipped is instantiating the client, and with it the
   * throwaway metastore.
   */
  private def newExecutionHiveConf(sqlContext: SQLContext): HiveConf = {
    val hiveConf = new HiveConf(sqlContext.sessionState.newHadoopConf(), classOf[SessionState])
    (sqlContext.sparkContext.conf.getAll.toMap
      ++ HiveUtils.newTemporaryConfiguration(useInMemoryDerby = true)).foreach {
      case (k, v) => hiveConf.set(k, v)
    }
    hiveConf
  }

  def startWithContext(sqlContext: SQLContext): Unit = {
    val server = new HiveThriftServer2(sqlContext)

    server.init(newExecutionHiveConf(sqlContext))
    server.start()
    listener = new HiveThriftServer2Listener(server, sqlContext.conf)
    sqlContext.sparkContext.addSparkListener(listener)
    uiTab = if (sqlContext.sparkContext.getConf.getBoolean("spark.ui.enabled", true)) {
      Some(new ThriftServerTab(sqlContext.sparkContext))
    } else {
      None
    }
  }

  def main(args: Array[String]) {
    Utils.initDaemon(log)
    val optionsProcessor = new HiveServer2.ServerOptionsProcessor("HiveThriftServer2")
    optionsProcessor.parse(args)

    logInfo("Starting SparkContext")
    SparkSQLEnv.init()

    ShutdownHookManager.addShutdownHook { () =>
      SparkSQLEnv.stop()
      uiTab.foreach(_.detach())
    }

    try {
      val server = new HiveThriftServer2(SparkSQLEnv.sqlContext)
      server.init(newExecutionHiveConf(SparkSQLEnv.sqlContext))
      server.start()
      logInfo("HiveThriftServer2 started")
      listener = new HiveThriftServer2Listener(server, SparkSQLEnv.sqlContext.conf)
      SparkSQLEnv.sparkContext.addSparkListener(listener)
      uiTab = if (SparkSQLEnv.sparkContext.getConf.getBoolean("spark.ui.enabled", true)) {
        Some(new ThriftServerTab(SparkSQLEnv.sparkContext))
      } else {
        None
      }
      // If application was killed before HiveThriftServer2 start successfully then SparkSubmit
      // process can not exit, so check whether if SparkContext was stopped.
      if (SparkSQLEnv.sparkContext.stopped.get()) {
        logError("SparkContext has stopped even if HiveServer2 has started, so exit")
        System.exit(-1)
      }
    } catch {
      case e: Exception =>
        logError("Error starting HiveThriftServer2", e)
        System.exit(-1)
    }
  }

  private[thriftserver] class SessionInfo(
      val sessionId: String,
      val startTimestamp: Long,
      val ip: String,
      val userName: String) {
    var finishTimestamp: Long = 0L
    var totalExecution: Int = 0
    def totalTime: Long = {
      if (finishTimestamp == 0L) {
        System.currentTimeMillis - startTimestamp
      } else {
        finishTimestamp - startTimestamp
      }
    }
  }

  private[thriftserver] object ExecutionState extends Enumeration {
    val STARTED, COMPILED, FAILED, FINISHED = Value
    type ExecutionState = Value
  }

  private[thriftserver] class ExecutionInfo(
      val statement: String,
      val sessionId: String,
      val startTimestamp: Long,
      val userName: String) {
    var finishTimestamp: Long = 0L
    var executePlan: String = ""
    var detail: String = ""
    var state: ExecutionState.Value = ExecutionState.STARTED
    val jobId: ArrayBuffer[String] = ArrayBuffer[String]()
    var groupId: String = ""
    def totalTime: Long = {
      if (finishTimestamp == 0L) {
        System.currentTimeMillis - startTimestamp
      } else {
        finishTimestamp - startTimestamp
      }
    }
  }


  /**
   * An inner sparkListener called in sc.stop to clean up the HiveThriftServer2
   */
  private[thriftserver] class HiveThriftServer2Listener(
      val server: HiveServer2,
      val conf: SQLConf) extends SparkListener {

    override def onApplicationEnd(applicationEnd: SparkListenerApplicationEnd): Unit = {
      server.stop()
    }
    private var onlineSessionNum: Int = 0
    private val sessionList = new mutable.LinkedHashMap[String, SessionInfo]
    private val executionList = new mutable.LinkedHashMap[String, ExecutionInfo]
    private val retainedStatements = conf.getConf(SQLConf.THRIFTSERVER_UI_STATEMENT_LIMIT)
    private val retainedSessions = conf.getConf(SQLConf.THRIFTSERVER_UI_SESSION_LIMIT)
    private var totalRunning = 0

    def getOnlineSessionNum: Int = synchronized { onlineSessionNum }

    def getTotalRunning: Int = synchronized { totalRunning }

    def getSessionList: Seq[SessionInfo] = synchronized { sessionList.values.toSeq }

    def getSession(sessionId: String): Option[SessionInfo] = synchronized {
      sessionList.get(sessionId)
    }

    def getExecutionList: Seq[ExecutionInfo] = synchronized { executionList.values.toSeq }

    override def onJobStart(jobStart: SparkListenerJobStart): Unit = synchronized {
      for {
        props <- Option(jobStart.properties)
        groupId <- Option(props.getProperty(SparkContext.SPARK_JOB_GROUP_ID))
        (_, info) <- executionList if info.groupId == groupId
      } {
        info.jobId += jobStart.jobId.toString
        info.groupId = groupId
      }
    }

    def onSessionCreated(ip: String, sessionId: String, userName: String = "UNKNOWN"): Unit = {
      synchronized {
        val info = new SessionInfo(sessionId, System.currentTimeMillis, ip, userName)
        sessionList.put(sessionId, info)
        onlineSessionNum += 1
        trimSessionIfNecessary()
      }
    }

    def onSessionClosed(sessionId: String): Unit = synchronized {
      sessionList(sessionId).finishTimestamp = System.currentTimeMillis
      onlineSessionNum -= 1
      trimSessionIfNecessary()
    }

    def onStatementStart(
        id: String,
        sessionId: String,
        statement: String,
        groupId: String,
        userName: String = "UNKNOWN"): Unit = synchronized {
      val info = new ExecutionInfo(statement, sessionId, System.currentTimeMillis, userName)
      info.state = ExecutionState.STARTED
      executionList.put(id, info)
      trimExecutionIfNecessary()
      sessionList(sessionId).totalExecution += 1
      executionList(id).groupId = groupId
      totalRunning += 1
    }

    def onStatementParsed(id: String, executionPlan: String): Unit = synchronized {
      executionList(id).executePlan = executionPlan
      executionList(id).state = ExecutionState.COMPILED
    }

    def onStatementError(id: String, errorMessage: String, errorTrace: String): Unit = {
      synchronized {
        executionList(id).finishTimestamp = System.currentTimeMillis
        executionList(id).detail = errorMessage
        executionList(id).state = ExecutionState.FAILED
        totalRunning -= 1
        trimExecutionIfNecessary()
      }
    }

    def onStatementFinish(id: String): Unit = synchronized {
      executionList(id).finishTimestamp = System.currentTimeMillis
      executionList(id).state = ExecutionState.FINISHED
      totalRunning -= 1
      trimExecutionIfNecessary()
    }

    private def trimExecutionIfNecessary() = {
      if (executionList.size > retainedStatements) {
        val toRemove = math.max(retainedStatements / 10, 1)
        executionList.filter(_._2.finishTimestamp != 0).take(toRemove).foreach { s =>
          executionList.remove(s._1)
        }
      }
    }

    private def trimSessionIfNecessary() = {
      if (sessionList.size > retainedSessions) {
        val toRemove = math.max(retainedSessions / 10, 1)
        sessionList.filter(_._2.finishTimestamp != 0).take(toRemove).foreach { s =>
          sessionList.remove(s._1)
        }
      }

    }
  }
}

private[hive] class HiveThriftServer2(sqlContext: SQLContext)
  extends HiveServer2
  with ReflectedCompositeService {
  // state is tracked internally so that the server only attempts to shut down if it successfully
  // started, and then once only.
  private val started = new AtomicBoolean(false)

  override def init(hiveConf: HiveConf) {
    val sparkSqlCliService = new SparkSQLCLIService(this, sqlContext)
    setSuperField(this, "cliService", sparkSqlCliService)
    addService(sparkSqlCliService)

    val thriftCliService = if (isHTTPTransportMode(hiveConf)) {
      new ThriftHttpCLIService(sparkSqlCliService)
    } else {
      new ThriftBinaryCLIService(sparkSqlCliService)
    }

    setSuperField(this, "thriftCLIService", thriftCliService)
    addService(thriftCliService)
    initCompositeService(hiveConf)
  }

  private def isHTTPTransportMode(hiveConf: HiveConf): Boolean = {
    val transportMode = hiveConf.getVar(ConfVars.HIVE_SERVER2_TRANSPORT_MODE)
    transportMode.toLowerCase(Locale.ROOT).equals("http")
  }


  override def start(): Unit = {
    super.start()
    started.set(true)
  }

  override def stop(): Unit = {
    if (started.getAndSet(false)) {
       super.stop()
    }
  }
}
