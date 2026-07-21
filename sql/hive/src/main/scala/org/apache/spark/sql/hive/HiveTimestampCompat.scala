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

package org.apache.spark.sql.hive

import org.apache.hadoop.hive.serde2.objectinspector.primitive.TimestampObjectInspector

import org.apache.spark.sql.catalyst.util.DateTimeUtils

/**
 * ODP-7072: external Hive tables that use the legacy Hive
 * `org.apache.hadoop.hive.serde2.JsonSerDe` deserialize TIMESTAMP columns into
 * `org.apache.hadoop.hive.common.type.Timestamp` (the legacy Hive native timestamp type),
 * whereas Spark 2.4's spark-hive is compiled against the bundled-fork Hive whose
 * `(Java)TimestampObjectInspector.getPrimitiveJavaObject` casts to `java.sql.Timestamp`.
 * That mismatch throws
 *   ClassCastException: common.type.Timestamp cannot be cast to java.sql.Timestamp
 * inside `oi.getPrimitiveJavaObject(...)` on the read path (TableReader / HiveInspectors).
 *
 * This shim bridges the legacy Hive type to Spark's micros without adding a compile-time
 * dependency on that class (it is not on Spark's compile classpath): the
 * `common.type.Timestamp` branch is resolved reflectively. Normal tables
 * (java.sql.Timestamp or writable OIs from ORC/parquet/text) take the unchanged fast path,
 * so there is no behavior change for existing reads.
 *
 * Note: vanilla Spark 3 handles timestamps identically (TableReader/HiveInspectors expect
 * java.sql.Timestamp), so this bridge would apply unchanged there too.
 */
private[hive] object HiveTimestampCompat {

  private lazy val hiveTsClass: Option[Class[_]] =
    try {
      // scalastyle:off classforname
      Some(Class.forName("org.apache.hadoop.hive.common.type.Timestamp"))
      // scalastyle:on classforname
    } catch {
      case _: Throwable => None
    }

  /**
   * Convert a Hive timestamp field value to Spark's internal micros representation,
   * tolerating both `java.sql.Timestamp` (normal path) and the legacy Hive
   * `org.apache.hadoop.hive.common.type.Timestamp` (legacy Hive JsonSerDe path).
   */
  def toMicros(value: Any, oi: TimestampObjectInspector): Long = value match {
    case t: java.sql.Timestamp =>
      DateTimeUtils.fromJavaTimestamp(t)
    case v if hiveTsClass.exists(_.isInstance(v)) =>
      // common.type.Timestamp is ZONE-AGNOSTIC wall-clock; its toString() is the canonical
      // "yyyy-MM-dd HH:mm:ss[.fff]". Parse via Timestamp.valueOf (interpreted as LOCAL), which is
      // exactly how Spark reads normal Hive timestamps and how HS2 displays them -> values match.
      //
      // Do NOT use toEpochMilli(): that interprets the wall-clock as UTC and the value then renders
      // shifted by the session-TZ offset (e.g. +5:30 in IST), diverging from HS2/executeQuery.
      DateTimeUtils.fromJavaTimestamp(java.sql.Timestamp.valueOf(v.toString.replace('T', ' ')))
    case _ =>
      // Writable OIs (ORC/parquet/text) and anything else: unchanged original path.
      DateTimeUtils.fromJavaTimestamp(oi.getPrimitiveJavaObject(value))
  }
}
