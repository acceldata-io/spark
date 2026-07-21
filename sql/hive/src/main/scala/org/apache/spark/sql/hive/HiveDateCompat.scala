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

import org.apache.hadoop.hive.serde2.objectinspector.primitive.DateObjectInspector

import org.apache.spark.sql.catalyst.util.DateTimeUtils

/**
 * ODP-7072: sibling of [[HiveTimestampCompat]] for the DATE read path. External Hive tables read
 * through the Hive serde path deserialize DATE columns into the legacy Hive native date type
 * `org.apache.hadoop.hive.common.type.Date` (Hive 3/4), whereas Spark 2.4's spark-hive is compiled
 * against the bundled-fork Hive whose `DateObjectInspector.getPrimitiveJavaObject` casts to
 * `java.sql.Date`. That mismatch throws
 *   ClassCastException: common.type.Date cannot be cast to java.sql.Date
 * on the read path (TableReader / HiveInspectors).
 *
 * `common.type.Date` also uses the PROLEPTIC Gregorian calendar while Spark 2.4 renders dates with
 * the HYBRID Julian-Gregorian calendar. Both concerns are handled the same way as the timestamp
 * bridge: parse the canonical `yyyy-MM-dd` string via `java.sql.Date.valueOf` (a hybrid/local
 * parse), which preserves the calendar LABEL - so pre-1582 dates render as HS2 displays them and
 * modern dates are unchanged. Normal tables (java.sql.Date or writable OIs from ORC/parquet/text)
 * take the unchanged fast path, so there is no behavior change for existing reads.
 */
private[hive] object HiveDateCompat {

  private lazy val hiveDateClass: Option[Class[_]] =
    try {
      // scalastyle:off classforname
      Some(Class.forName("org.apache.hadoop.hive.common.type.Date"))
      // scalastyle:on classforname
    } catch {
      case _: Throwable => None
    }

  /**
   * Convert a Hive date field value to Spark's internal days-since-epoch, tolerating both
   * `java.sql.Date` (normal path) and the legacy Hive `org.apache.hadoop.hive.common.type.Date`
   * (legacy Hive serde path).
   */
  def toDays(value: Any, oi: DateObjectInspector): Int = value match {
    case d: java.sql.Date =>
      DateTimeUtils.fromJavaDate(d)
    case v if hiveDateClass.exists(_.isInstance(v)) =>
      // common.type.Date.toString is the canonical "yyyy-MM-dd"; Date.valueOf parses it in the
      // hybrid/local calendar, matching how Spark reads normal Hive dates and HS2 display.
      DateTimeUtils.fromJavaDate(java.sql.Date.valueOf(v.toString))
    case _ =>
      // Writable OIs (ORC/parquet/text) and anything else: unchanged original path.
      DateTimeUtils.fromJavaDate(oi.getPrimitiveJavaObject(value))
  }
}
