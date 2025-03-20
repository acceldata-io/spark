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
package org.apache.spark.internal

import java.util.Locale

/**
 * All structured logging `keys` used in `MDC` must be extends `LogKey`
 * <p>
 *
 * `LogKey`s serve as identifiers for mapped diagnostic contexts (MDC) within logs.
 * Follow these guidelines when adding a new LogKey:
 * <ul>
 *   <li>
 *     Define all structured logging keys in `LogKey.scala`, and sort them alphabetically for
 *     ease of search.
 *   </li>
 *   <li>
 *     Use `UPPER_SNAKE_CASE` for key names.
 *   </li>
 *   <li>
 *     Key names should be both simple and broad, yet include specific identifiers like `STAGE_ID`,
 *     `TASK_ID`, and `JOB_ID` when needed for clarity. For instance, use `MAX_ATTEMPTS` as a
 *     general key instead of creating separate keys for each scenario such as
 *     `EXECUTOR_STATE_SYNC_MAX_ATTEMPTS` and `MAX_TASK_FAILURES`.
 *     This balances simplicity with the detail needed for effective logging.
 *   </li>
 *   <li>
 *     Use abbreviations in names if they are widely understood,
 *     such as `APP_ID` for APPLICATION_ID, and `K8S` for KUBERNETES.
 *   </li>
 *   <li>
 *     For time-related keys, use milliseconds as the unit of time.
 *   </li>
 * </ul>
 */
trait LogKey {
  private lazy val _name: String = getClass.getSimpleName.stripSuffix("$").toLowerCase(Locale.ROOT)
  def name: String = _name
}

/**
 * Various keys used for mapped diagnostic contexts(MDC) in logging. All structured logging keys
 * should be defined here for standardization.
 */
private[spark] object LogKeys {
  case object ACCUMULATOR_ID extends LogKey
  case object ACL_ENABLED extends LogKey
  case object ACTUAL_NUM_FILES extends LogKey
  case object ACTUAL_PARTITION_COLUMN extends LogKey
  case object ADDED_JARS extends LogKey
  case object ADMIN_ACLS extends LogKey
  case object ADMIN_ACL_GROUPS extends LogKey
  case object ADVISORY_TARGET_SIZE extends LogKey
  case object AGGREGATE_FUNCTIONS extends LogKey
  case object ALIGNED_FROM_TIME extends LogKey
  case object ALIGNED_TO_TIME extends LogKey
  case object ALPHA extends LogKey
  case object ANALYSIS_ERROR extends LogKey
  case object APP_ATTEMPT_ID extends LogKey
  case object APP_ATTEMPT_SHUFFLE_MERGE_ID extends LogKey
  case object APP_DESC extends LogKey
  case object APP_EXECUTOR_ID extends LogKey
  case object APP_ID extends LogKey
  case object APP_NAME extends LogKey
  case object APP_STATE extends LogKey
  case object ARCHIVE_NAME extends LogKey
  case object ARGS extends LogKey
  case object ARTIFACTS extends LogKey
  case object ARTIFACT_ID extends LogKey
  case object ATTRIBUTE_MAP extends LogKey
  case object AUTH_ENABLED extends LogKey
  case object AVG_BATCH_PROC_TIME extends LogKey
  case object BACKUP_FILE extends LogKey
  case object BARRIER_EPOCH extends LogKey
  case object BARRIER_ID extends LogKey
  case object BATCH_ID extends LogKey
  case object BATCH_NAME extends LogKey
  case object BATCH_TIMES extends LogKey
  case object BATCH_TIMESTAMP extends LogKey
  case object BATCH_WRITE extends LogKey
  case object BIND_ADDRESS extends LogKey
  case object BLOCK_GENERATOR_STATUS extends LogKey
  case object BLOCK_ID extends LogKey
  case object BLOCK_IDS extends LogKey
  case object BLOCK_MANAGER_ID extends LogKey
  case object BLOCK_MANAGER_IDS extends LogKey
  case object BLOCK_TYPE extends LogKey
  case object BOOT extends LogKey
  case object BOOTSTRAP_TIME extends LogKey
  case object BOOT_TIME extends LogKey
  case object BROADCAST extends LogKey
  case object BROADCAST_ID extends LogKey
  case object BROADCAST_OUTPUT_STATUS_SIZE extends LogKey
  case object BUCKET extends LogKey
  case object BYTECODE_SIZE extends LogKey
  case object BYTE_BUFFER extends LogKey
  case object BYTE_SIZE extends LogKey
  case object CACHED_TABLE_PARTITION_METADATA_SIZE extends LogKey
  case object CACHE_AUTO_REMOVED_SIZE extends LogKey
  case object CACHE_SIZE extends LogKey
  case object CACHE_UNTIL_HIGHEST_CONSUMED_SIZE extends LogKey
  case object CACHE_UNTIL_LAST_PRODUCED_SIZE extends LogKey
  case object CALL_SITE_LONG_FORM extends LogKey
  case object CALL_SITE_SHORT_FORM extends LogKey
  case object CANCEL_FUTURE_JOBS extends LogKey
  case object CATALOG_NAME extends LogKey
  case object CATEGORICAL_FEATURES extends LogKey
  case object CHECKPOINT_FILE extends LogKey
  case object CHECKPOINT_INTERVAL extends LogKey
  case object CHECKPOINT_LOCATION extends LogKey
  case object CHECKPOINT_PATH extends LogKey
  case object CHECKPOINT_ROOT extends LogKey
  case object CHECKPOINT_TIME extends LogKey
  case object CHOSEN_WATERMARK extends LogKey
  case object CLASSIFIER extends LogKey
  case object CLASS_LOADER extends LogKey
  case object CLASS_NAME extends LogKey
  case object CLASS_PATH extends LogKey
  case object CLASS_PATHS extends LogKey
  case object CLAUSES extends LogKey
  case object CLEANUP_LOCAL_DIRS extends LogKey
  case object CLUSTER_CENTROIDS extends LogKey
  case object CLUSTER_ID extends LogKey
  case object CLUSTER_LABEL extends LogKey
  case object CLUSTER_LEVEL extends LogKey
  case object CLUSTER_WEIGHT extends LogKey
  case object CODE extends LogKey
  case object CODEC_LEVEL extends LogKey
  case object CODEC_NAME extends LogKey
  case object CODEGEN_STAGE_ID extends LogKey
  case object COLUMN_DATA_TYPE_SOURCE extends LogKey
  case object COLUMN_DATA_TYPE_TARGET extends LogKey
  case object COLUMN_DEFAULT_VALUE extends LogKey
  case object COLUMN_NAME extends LogKey
  case object COMMAND extends LogKey
  case object COMMAND_OUTPUT extends LogKey
  case object COMMITTED_VERSION extends LogKey
  case object COMPACT_INTERVAL extends LogKey
  case object COMPONENT extends LogKey
  case object COMPUTE extends LogKey
  case object CONFIG extends LogKey
  case object CONFIG2 extends LogKey
  case object CONFIG3 extends LogKey
  case object CONFIG4 extends LogKey
  case object CONFIG5 extends LogKey
  case object CONFIG_DEPRECATION_MESSAGE extends LogKey
  case object CONFIG_KEY_UPDATED extends LogKey
  case object CONFIG_VERSION extends LogKey
  case object CONSUMER extends LogKey
  case object CONTAINER extends LogKey
  case object CONTAINER_ID extends LogKey
  case object CONTAINER_STATE extends LogKey
  case object CONTEXT extends LogKey
  case object COST extends LogKey
  case object COUNT extends LogKey
  case object CREATED_POOL_NAME extends LogKey
  case object CREATION_SITE extends LogKey
  case object CREDENTIALS_RENEWAL_INTERVAL_RATIO extends LogKey
  case object CROSS_VALIDATION_METRIC extends LogKey
  case object CROSS_VALIDATION_METRICS extends LogKey
  case object CSV_HEADER_COLUMN_NAME extends LogKey
  case object CSV_HEADER_COLUMN_NAMES extends LogKey
  case object CSV_HEADER_LENGTH extends LogKey
  case object CSV_SCHEMA_FIELD_NAME extends LogKey
  case object CSV_SCHEMA_FIELD_NAMES extends LogKey
  case object CSV_SOURCE extends LogKey
  case object CURRENT_BATCH_ID extends LogKey
  case object CURRENT_DISK_SIZE extends LogKey
  case object CURRENT_FILE extends LogKey
  case object CURRENT_MEMORY_SIZE extends LogKey
  case object CURRENT_PATH extends LogKey
  case object CURRENT_TIME extends LogKey
  case object DATA extends LogKey
  case object DATABASE_NAME extends LogKey
  case object DATAFRAME_CACHE_ENTRY extends LogKey
  case object DATAFRAME_ID extends LogKey
  case object DATA_FILE extends LogKey
  case object DATA_SOURCE extends LogKey
  case object DATA_SOURCES extends LogKey
  case object DEFAULT_COMPACT_INTERVAL extends LogKey
  case object DEFAULT_ISOLATION_LEVEL extends LogKey
  case object DEFAULT_NAME extends LogKey
  case object DEFAULT_VALUE extends LogKey
  case object DELAY extends LogKey
  case object DELEGATE extends LogKey
  case object DELTA extends LogKey
  case object DEPRECATED_KEY extends LogKey
  case object DERIVATIVE extends LogKey
  case object DESCRIPTION extends LogKey
  case object DESIRED_NUM_PARTITIONS extends LogKey
  case object DESIRED_TREE_DEPTH extends LogKey
  case object DESTINATION_PATH extends LogKey
  case object DFS_FILE extends LogKey
  case object DIFF_DELTA extends LogKey
  case object DIVISIBLE_CLUSTER_INDICES_SIZE extends LogKey
  case object DRIVER_ID extends LogKey
  case object DRIVER_MEMORY_SIZE extends LogKey
  case object DRIVER_STATE extends LogKey
  case object DROPPED_PARTITIONS extends LogKey
  case object DSTREAM extends LogKey
  case object DURATION extends LogKey
  case object EARLIEST_LOADED_VERSION extends LogKey
  case object EFFECTIVE_STORAGE_LEVEL extends LogKey
  case object ELAPSED_TIME extends LogKey
  case object ENCODING extends LogKey
  case object ENDPOINT_NAME extends LogKey
  case object END_INDEX extends LogKey
  case object END_POINT extends LogKey
  case object END_VERSION extends LogKey
  case object ENGINE extends LogKey
  case object EPOCH extends LogKey
  case object ERROR extends LogKey
  case object ESTIMATOR_PARAM_MAP extends LogKey
  case object EVALUATED_FILTERS extends LogKey
  case object EVENT extends LogKey
  case object EVENT_LOG_DESTINATION extends LogKey
  case object EVENT_LOOP extends LogKey
  case object EVENT_NAME extends LogKey
  case object EVENT_QUEUE extends LogKey
  case object EXCEPTION extends LogKey
  case object EXECUTE_INFO extends LogKey
  case object EXECUTE_KEY extends LogKey
  case object EXECUTION_MEMORY_SIZE extends LogKey
  case object EXECUTION_PLAN_LEAVES extends LogKey
  case object EXECUTOR_BACKEND extends LogKey
  case object EXECUTOR_ENVS extends LogKey
  case object EXECUTOR_ENV_REGEX extends LogKey
  case object EXECUTOR_ID extends LogKey
  case object EXECUTOR_IDS extends LogKey
  case object EXECUTOR_LAUNCH_COMMANDS extends LogKey
  case object EXECUTOR_MEMORY_OFFHEAP extends LogKey
  case object EXECUTOR_MEMORY_OVERHEAD_SIZE extends LogKey
  case object EXECUTOR_MEMORY_SIZE extends LogKey
  case object EXECUTOR_RESOURCES extends LogKey
  case object EXECUTOR_SHUFFLE_INFO extends LogKey
  case object EXECUTOR_STATE extends LogKey
  case object EXECUTOR_TIMEOUT extends LogKey
  case object EXECUTOR_USER_CLASS_PATH_FIRST extends LogKey
  case object EXEC_AMOUNT extends LogKey
  case object EXISTING_FILE extends LogKey
  case object EXISTING_PATH extends LogKey
  case object EXIT_CODE extends LogKey
  case object EXPECTED_NUM_FILES extends LogKey
  case object EXPECTED_PARTITION_COLUMN extends LogKey
  case object EXPIRY_TIMESTAMP extends LogKey
  case object EXPR extends LogKey
  case object EXPR_TERMS extends LogKey
  case object EXTENDED_EXPLAIN_GENERATOR extends LogKey
  case object FAILED_STAGE extends LogKey
  case object FAILED_STAGE_NAME extends LogKey
  case object FAILURES extends LogKey
  case object FALLBACK_VERSION extends LogKey
  case object FEATURE_COLUMN extends LogKey
  case object FEATURE_DIMENSION extends LogKey
  case object FEATURE_NAME extends LogKey
  case object FETCH_SIZE extends LogKey
  case object FIELD_NAME extends LogKey
  case object FIELD_TYPE extends LogKey
  case object FILES extends LogKey
  case object FILE_ABSOLUTE_PATH extends LogKey
  case object FILE_END_OFFSET extends LogKey
  case object FILE_FORMAT extends LogKey
  case object FILE_FORMAT2 extends LogKey
  case object FILE_LENGTH_XATTR extends LogKey
  case object FILE_MODIFICATION_TIME extends LogKey
  case object FILE_NAME extends LogKey
  case object FILE_NAME2 extends LogKey
  case object FILE_NAME3 extends LogKey
  case object FILE_NAMES extends LogKey
  case object FILE_START_OFFSET extends LogKey
  case object FILE_SYSTEM extends LogKey
  case object FILE_VERSION extends LogKey
  case object FILTER extends LogKey
  case object FINAL_CONTEXT extends LogKey
  case object FINAL_OUTPUT_PATH extends LogKey
  case object FINAL_PATH extends LogKey
  case object FINISH_TIME extends LogKey
  case object FINISH_TRIGGER_DURATION extends LogKey
  case object FREE_MEMORY_SIZE extends LogKey
  case object FROM_OFFSET extends LogKey
  case object FROM_TIME extends LogKey
  case object FS_DATA_OUTPUT_STREAM extends LogKey
  case object FUNCTION_NAME extends LogKey
  case object FUNCTION_PARAM extends LogKey
  case object GLOBAL_INIT_FILE extends LogKey
  case object GLOBAL_WATERMARK extends LogKey
  case object GROUP_BY_EXPRS extends LogKey
  case object GROUP_ID extends LogKey
  case object HADOOP_VERSION extends LogKey
  case object HASH_JOIN_KEYS extends LogKey
  case object HASH_MAP_SIZE extends LogKey
  case object HEARTBEAT extends LogKey
  case object HEARTBEAT_INTERVAL extends LogKey
  case object HISTORY_DIR extends LogKey
  case object HIVE_CLIENT_VERSION extends LogKey
  case object HIVE_METASTORE_VERSION extends LogKey
  case object HIVE_OPERATION_STATE extends LogKey
  case object HIVE_OPERATION_TYPE extends LogKey
  case object HOST extends LogKey
  case object HOSTS extends LogKey
  case object HOST_LOCAL_BLOCKS_SIZE extends LogKey
  case object HOST_PORT extends LogKey
  case object HOST_PORT2 extends LogKey
  case object HUGE_METHOD_LIMIT extends LogKey
  case object HYBRID_STORE_DISK_BACKEND extends LogKey
  case object IDENTIFIER extends LogKey
  case object INCOMPATIBLE_TYPES extends LogKey
  case object INDEX extends LogKey
  case object INDEX_FILE extends LogKey
  case object INDEX_NAME extends LogKey
  case object INFERENCE_MODE extends LogKey
  case object INIT extends LogKey
  case object INITIAL_CAPACITY extends LogKey
  case object INITIAL_HEARTBEAT_INTERVAL extends LogKey
  case object INIT_MODE extends LogKey
  case object INIT_TIME extends LogKey
  case object INPUT extends LogKey
  case object INPUT_SPLIT extends LogKey
  case object INTEGRAL extends LogKey
  case object INTERRUPT_THREAD extends LogKey
  case object INTERVAL extends LogKey
  case object INVALID_PARAMS extends LogKey
  case object ISOLATION_LEVEL extends LogKey
  case object ISSUE_DATE extends LogKey
  case object IS_NETWORK_REQUEST_DONE extends LogKey
  case object JAR_ENTRY extends LogKey
  case object JAR_MESSAGE extends LogKey
  case object JAR_URL extends LogKey
  case object JAVA_VERSION extends LogKey
  case object JAVA_VM_NAME extends LogKey
  case object JOB_ID extends LogKey
  case object JOIN_CONDITION extends LogKey
  case object JOIN_CONDITION_SUB_EXPR extends LogKey
  case object JOIN_TYPE extends LogKey
  case object K8S_CONTEXT extends LogKey
  case object KEY extends LogKey
  case object KEY2 extends LogKey
  case object KEYTAB extends LogKey
  case object KEYTAB_FILE extends LogKey
  case object KILL_EXECUTORS extends LogKey
  case object KINESIS_REASON extends LogKey
  case object LABEL_COLUMN extends LogKey
  case object LARGEST_CLUSTER_INDEX extends LogKey
  case object LAST_ACCESS_TIME extends LogKey
  case object LAST_COMMITTED_CHECKPOINT_ID extends LogKey
  case object LAST_COMMIT_BASED_CHECKPOINT_ID extends LogKey
  case object LAST_VALID_TIME extends LogKey
  case object LATEST_BATCH_ID extends LogKey
  case object LATEST_COMMITTED_BATCH_ID extends LogKey
  case object LATEST_SHUFFLE_MERGE_ID extends LogKey
  case object LEARNING_RATE extends LogKey
  case object LEFT_EXPR extends LogKey
  case object LEFT_LOGICAL_PLAN_STATS_SIZE_IN_BYTES extends LogKey
  case object LINE extends LogKey
  case object LINEAGE extends LogKey
  case object LINE_NUM extends LogKey
  case object LISTENER extends LogKey
  case object LOADED_CHECKPOINT_ID extends LogKey
  case object LOADED_VERSION extends LogKey
  case object LOAD_FACTOR extends LogKey
  case object LOAD_TIME extends LogKey
  case object LOCALE extends LogKey
  case object LOCAL_BLOCKS_SIZE extends LogKey
  case object LOCAL_SCRATCH_DIR extends LogKey
  case object LOCATION extends LogKey
  case object LOGICAL_PLAN extends LogKey
  case object LOGICAL_PLAN_COLUMNS extends LogKey
  case object LOGICAL_PLAN_LEAVES extends LogKey
  case object LOG_ID extends LogKey
  case object LOG_LEVEL extends LogKey
  case object LOG_OFFSET extends LogKey
  case object LOG_TYPE extends LogKey
  case object LOSSES extends LogKey
  case object LOWER_BOUND extends LogKey
  case object MALFORMATTED_STRING extends LogKey
  case object MAP_ID extends LogKey
  case object MASTER_URL extends LogKey
  case object MAX_ATTEMPTS extends LogKey
  case object MAX_CACHE_UNTIL_HIGHEST_CONSUMED_SIZE extends LogKey
  case object MAX_CACHE_UNTIL_LAST_PRODUCED_SIZE extends LogKey
  case object MAX_CAPACITY extends LogKey
  case object MAX_CATEGORIES extends LogKey
  case object MAX_EXECUTOR_FAILURES extends LogKey
  case object MAX_FILE_VERSION extends LogKey
  case object MAX_JVM_METHOD_PARAMS_LENGTH extends LogKey
  case object MAX_MEMORY_SIZE extends LogKey
  case object MAX_METHOD_CODE_SIZE extends LogKey
  case object MAX_NUM_BINS extends LogKey
  case object MAX_NUM_CHUNKS extends LogKey
  case object MAX_NUM_FILES extends LogKey
  case object MAX_NUM_LOG_POLICY extends LogKey
  case object MAX_NUM_PARTITIONS extends LogKey
  case object MAX_NUM_POSSIBLE_BINS extends LogKey
  case object MAX_NUM_ROWS_IN_MEMORY_BUFFER extends LogKey
  case object MAX_SEEN_VERSION extends LogKey
  case object MAX_SERVICE_NAME_LENGTH extends LogKey
  case object MAX_SIZE extends LogKey
  case object MAX_SLOTS extends LogKey
  case object MAX_SPLIT_BYTES extends LogKey
  case object MAX_TABLE_PARTITION_METADATA_SIZE extends LogKey
  case object MEMORY_CONSUMER extends LogKey
  case object MEMORY_POOL_NAME extends LogKey
  case object MEMORY_SIZE extends LogKey
  case object MEMORY_THRESHOLD_SIZE extends LogKey
  case object MERGE_DIR_NAME extends LogKey
  case object MESSAGE extends LogKey
  case object METADATA extends LogKey
  case object METADATA_DIRECTORY extends LogKey
  case object METADATA_JSON extends LogKey
  case object META_FILE extends LogKey
  case object METHOD_NAME extends LogKey
  case object METHOD_PARAM_TYPES extends LogKey
  case object METRICS_JSON extends LogKey
  case object METRIC_NAME extends LogKey
  case object MINI_BATCH_FRACTION extends LogKey
  case object MIN_COMPACTION_BATCH_ID extends LogKey
  case object MIN_NUM_FREQUENT_PATTERN extends LogKey
  case object MIN_POINT_PER_CLUSTER extends LogKey
  case object MIN_RATE extends LogKey
  case object MIN_SEEN_VERSION extends LogKey
  case object MIN_SHARE extends LogKey
  case object MIN_SIZE extends LogKey
  case object MIN_TIME extends LogKey
  case object MIN_VERSIONS_TO_DELETE extends LogKey
  case object MIN_VERSION_NUM extends LogKey
  case object MISSING_PARENT_STAGES extends LogKey
  case object MODEL_WEIGHTS extends LogKey
  case object MODIFY_ACLS extends LogKey
  case object MODIFY_ACLS_GROUPS extends LogKey
  case object MODULE_NAME extends LogKey
  case object NAME extends LogKey
  case object NAMESPACE extends LogKey
  case object NETWORK_IF extends LogKey
  case object NEW_FEATURE_COLUMN_NAME extends LogKey
  case object NEW_LABEL_COLUMN_NAME extends LogKey
  case object NEW_PATH extends LogKey
  case object NEW_RDD_ID extends LogKey
  case object NEW_STATE extends LogKey
  case object NEW_VALUE extends LogKey
  case object NEXT_RENEWAL_TIME extends LogKey
  case object NODES extends LogKey
  case object NODE_LOCATION extends LogKey
  case object NON_BUILT_IN_CONNECTORS extends LogKey
  case object NORM extends LogKey
  case object NUM_ADDED_PARTITIONS extends LogKey
  case object NUM_APPS extends LogKey
  case object NUM_ATTEMPT extends LogKey
  case object NUM_BATCHES extends LogKey
  case object NUM_BIN extends LogKey
  case object NUM_BLOCKS extends LogKey
  case object NUM_BLOCK_IDS extends LogKey
  case object NUM_BROADCAST_BLOCK extends LogKey
  case object NUM_BYTES extends LogKey
  case object NUM_BYTES_CURRENT extends LogKey
  case object NUM_BYTES_EVICTED extends LogKey
  case object NUM_BYTES_MAX extends LogKey
  case object NUM_BYTES_TO_FREE extends LogKey
  case object NUM_BYTES_TO_WARN extends LogKey
  case object NUM_BYTES_USED extends LogKey
  case object NUM_CATEGORIES extends LogKey
  case object NUM_CHECKSUM_FILE extends LogKey
  case object NUM_CHUNKS extends LogKey
  case object NUM_CLASSES extends LogKey
  case object NUM_COEFFICIENTS extends LogKey
  case object NUM_COLUMNS extends LogKey
  case object NUM_CONCURRENT_WRITER extends LogKey
  case object NUM_CORES extends LogKey
  case object NUM_DATA_FILE extends LogKey
  case object NUM_DATA_FILES extends LogKey
  case object NUM_DECOMMISSIONED extends LogKey
  case object NUM_DRIVERS extends LogKey
  case object NUM_DROPPED_PARTITIONS extends LogKey
  case object NUM_EFFECTIVE_RULE_OF_RUNS extends LogKey
  case object NUM_ELEMENTS_SPILL_THRESHOLD extends LogKey
  case object NUM_EVENTS extends LogKey
  case object NUM_EXAMPLES extends LogKey
  case object NUM_EXECUTORS extends LogKey
  case object NUM_EXECUTORS_EXITED extends LogKey
  case object NUM_EXECUTORS_KILLED extends LogKey
  case object NUM_EXECUTOR_CORES extends LogKey
  case object NUM_EXECUTOR_CORES_REMAINING extends LogKey
  case object NUM_EXECUTOR_CORES_TOTAL extends LogKey
  case object NUM_EXECUTOR_DESIRED extends LogKey
  case object NUM_EXECUTOR_LAUNCH extends LogKey
  case object NUM_EXECUTOR_TARGET extends LogKey
  case object NUM_FAILURES extends LogKey
  case object NUM_FEATURES extends LogKey
  case object NUM_FILES extends LogKey
  case object NUM_FILES_COPIED extends LogKey
  case object NUM_FILES_FAILED_TO_DELETE extends LogKey
  case object NUM_FILES_REUSED extends LogKey
  case object NUM_FREQUENT_ITEMS extends LogKey
  case object NUM_HOST_LOCAL_BLOCKS extends LogKey
  case object NUM_INDEX_FILE extends LogKey
  case object NUM_INDEX_FILES extends LogKey
  case object NUM_ITERATIONS extends LogKey
  case object NUM_KAFKA_PULLS extends LogKey
  case object NUM_KAFKA_RECORDS_PULLED extends LogKey
  case object NUM_LEADING_SINGULAR_VALUES extends LogKey
  case object NUM_LEFT_PARTITION_VALUES extends LogKey
  case object NUM_LOADED_ENTRIES extends LogKey
  case object NUM_LOCAL_BLOCKS extends LogKey
  case object NUM_LOCAL_DIRS extends LogKey
  case object NUM_LOCAL_FREQUENT_PATTERN extends LogKey
  case object NUM_MERGERS extends LogKey
  case object NUM_MERGER_LOCATIONS extends LogKey
  case object NUM_META_FILES extends LogKey
  case object NUM_NODES extends LogKey
  case object NUM_PARTITIONS extends LogKey
  case object NUM_PARTITIONS2 extends LogKey
  case object NUM_PATHS extends LogKey
  case object NUM_PEERS extends LogKey
  case object NUM_PEERS_REPLICATED_TO extends LogKey
  case object NUM_PEERS_TO_REPLICATE_TO extends LogKey
  case object NUM_PENDING_LAUNCH_TASKS extends LogKey
  case object NUM_POD extends LogKey
  case object NUM_POD_SHARED_SLOT extends LogKey
  case object NUM_POD_TARGET extends LogKey
  case object NUM_POINT extends LogKey
  case object NUM_PREFIXES extends LogKey
  case object NUM_PRUNED extends LogKey
  case object NUM_PUSH_MERGED_LOCAL_BLOCKS extends LogKey
  case object NUM_RECEIVERS extends LogKey
  case object NUM_RECORDS_READ extends LogKey
  case object NUM_RELEASED_LOCKS extends LogKey
  case object NUM_REMAINED extends LogKey
  case object NUM_REMOTE_BLOCKS extends LogKey
  case object NUM_REMOVED_WORKERS extends LogKey
  case object NUM_REPLICAS extends LogKey
  case object NUM_REQUESTS extends LogKey
  case object NUM_REQUEST_SYNC_TASK extends LogKey
  case object NUM_RESOURCE_SLOTS extends LogKey
  case object NUM_RETRIES extends LogKey
  case object NUM_RETRY extends LogKey
  case object NUM_RIGHT_PARTITION_VALUES extends LogKey
  case object NUM_ROWS extends LogKey
  case object NUM_RULE_OF_RUNS extends LogKey
  case object NUM_SEQUENCES extends LogKey
  case object NUM_SKIPPED extends LogKey
  case object NUM_SLOTS extends LogKey
  case object NUM_SPILLS extends LogKey
  case object NUM_SPILL_WRITERS extends LogKey
  case object NUM_SUB_DIRS extends LogKey
  case object NUM_SUCCESSFUL_TASKS extends LogKey
  case object NUM_TASKS extends LogKey
  case object NUM_TASK_CPUS extends LogKey
  case object NUM_TRAIN_WORD extends LogKey
  case object NUM_UNFINISHED_DECOMMISSIONED extends LogKey
  case object NUM_VERSIONS_RETAIN extends LogKey
  case object NUM_WEIGHTED_EXAMPLES extends LogKey
  case object NUM_WORKERS extends LogKey
  case object OBJECT_AGG_SORT_BASED_FALLBACK_THRESHOLD extends LogKey
  case object OBJECT_ID extends LogKey
  case object OFFSET extends LogKey
  case object OFFSETS extends LogKey
  case object OFFSET_SEQUENCE_METADATA extends LogKey
  case object OLD_BLOCK_MANAGER_ID extends LogKey
  case object OLD_GENERATION_GC extends LogKey
  case object OLD_VALUE extends LogKey
  case object OPEN_COST_IN_BYTES extends LogKey
  case object OPERATION_HANDLE extends LogKey
  case object OPERATION_HANDLE_ID extends LogKey
  case object OPERATION_ID extends LogKey
  case object OPTIMIZED_PLAN_COLUMNS extends LogKey
  case object OPTIMIZER_CLASS_NAME extends LogKey
  case object OPTIONS extends LogKey
  case object OP_ID extends LogKey
  case object OP_TYPE extends LogKey
  case object ORIGINAL_DISK_SIZE extends LogKey
  case object ORIGINAL_MEMORY_SIZE extends LogKey
  case object OS_ARCH extends LogKey
  case object OS_NAME extends LogKey
  case object OS_VERSION extends LogKey
  case object OUTPUT extends LogKey
  case object OUTPUT_BUFFER extends LogKey
  case object OVERHEAD_MEMORY_SIZE extends LogKey
  case object PAGE_SIZE extends LogKey
  case object PARENT_STAGES extends LogKey
  case object PARSE_MODE extends LogKey
  case object PARTITIONED_FILE_READER extends LogKey
  case object PARTITIONER extends LogKey
  case object PARTITION_ID extends LogKey
  case object PARTITION_IDS extends LogKey
  case object PARTITION_SIZE extends LogKey
  case object PARTITION_SPECIFICATION extends LogKey
  case object PARTITION_SPECS extends LogKey
  case object PATH extends LogKey
  case object PATHS extends LogKey
  case object PEER extends LogKey
  case object PENDING_TIMES extends LogKey
  case object PERCENT extends LogKey
  case object PIPELINE_STAGE_UID extends LogKey
  case object PLUGIN_NAME extends LogKey
  case object POD_ID extends LogKey
  case object POD_NAME extends LogKey
  case object POD_NAMESPACE extends LogKey
  case object POD_PHASE extends LogKey
  case object POD_STATE extends LogKey
  case object POINT_OF_CENTER extends LogKey
  case object POLICY extends LogKey
  case object POOL_NAME extends LogKey
  case object PORT extends LogKey
  case object PORT2 extends LogKey
  case object POST_SCAN_FILTERS extends LogKey
  case object PREDICATE extends LogKey
  case object PREDICATES extends LogKey
  case object PREFERRED_SERVICE_NAME extends LogKey
  case object PREFIX extends LogKey
  case object PRETTY_ID_STRING extends LogKey
  case object PRINCIPAL extends LogKey
  case object PROCESS extends LogKey
  case object PROCESSING_TIME extends LogKey
  case object PRODUCER_ID extends LogKey
  case object PROPERTY_NAME extends LogKey
  case object PROPORTIONAL extends LogKey
  case object PROTOCOL_VERSION extends LogKey
  case object PROVIDER extends LogKey
  case object PUSHED_FILTERS extends LogKey
  case object PUSH_MERGED_LOCAL_BLOCKS_SIZE extends LogKey
  case object PVC_METADATA_NAME extends LogKey
  case object PYTHON_EXEC extends LogKey
  case object PYTHON_PACKAGES extends LogKey
  case object PYTHON_VERSION extends LogKey
  case object PYTHON_WORKER_CHANNEL_IS_BLOCKING_MODE extends LogKey
  case object PYTHON_WORKER_CHANNEL_IS_CONNECTED extends LogKey
  case object PYTHON_WORKER_HAS_INPUTS extends LogKey
  case object PYTHON_WORKER_IDLE_TIMEOUT extends LogKey
  case object PYTHON_WORKER_IS_ALIVE extends LogKey
  case object PYTHON_WORKER_MODULE extends LogKey
  case object PYTHON_WORKER_RESPONSE extends LogKey
  case object PYTHON_WORKER_SELECTION_KEY_INTERESTS extends LogKey
  case object PYTHON_WORKER_SELECTION_KEY_IS_VALID extends LogKey
  case object PYTHON_WORKER_SELECTOR_IS_OPEN extends LogKey
  case object QUANTILES extends LogKey
  case object QUERY_CACHE_VALUE extends LogKey
  case object QUERY_HINT extends LogKey
  case object QUERY_ID extends LogKey
  case object QUERY_PLAN extends LogKey
  case object QUERY_PLAN_COMPARISON extends LogKey
  case object QUERY_PLAN_LENGTH_ACTUAL extends LogKey
  case object QUERY_PLAN_LENGTH_MAX extends LogKey
  case object QUERY_RUN_ID extends LogKey
  case object RANGE extends LogKey
  case object RATE_LIMIT extends LogKey
  case object RATIO extends LogKey
  case object RDD extends LogKey
  case object RDD_CHECKPOINT_DIR extends LogKey
  case object RDD_DEBUG_STRING extends LogKey
  case object RDD_DESCRIPTION extends LogKey
  case object RDD_ID extends LogKey
  case object READ_LIMIT extends LogKey
  case object REASON extends LogKey
  case object REATTACHABLE extends LogKey
  case object RECEIVED_BLOCK_INFO extends LogKey
  case object RECEIVED_BLOCK_TRACKER_LOG_EVENT extends LogKey
  case object RECEIVER_ID extends LogKey
  case object RECEIVER_IDS extends LogKey
  case object RECORDS extends LogKey
  case object RECOVERY_STATE extends LogKey
  case object RECURSIVE_DEPTH extends LogKey
  case object REDACTED_STATEMENT extends LogKey
  case object REDUCE_ID extends LogKey
  case object REGEX extends LogKey
  case object REGISTERED_EXECUTOR_FILE extends LogKey
  case object REGISTER_MERGE_RESULTS extends LogKey
  case object RELATION_NAME extends LogKey
  case object RELATION_OUTPUT extends LogKey
  case object RELATIVE_TOLERANCE extends LogKey
  case object RELEASED_LOCKS extends LogKey
  case object REMAINING_PARTITIONS extends LogKey
  case object REMOTE_ADDRESS extends LogKey
  case object REMOTE_BLOCKS_SIZE extends LogKey
  case object REMOVE_FROM_MASTER extends LogKey
  case object REPORT_DETAILS extends LogKey
  case object REQUESTER_SIZE extends LogKey
  case object REQUEST_EXECUTORS extends LogKey
  case object REQUEST_ID extends LogKey
  case object RESOURCE extends LogKey
  case object RESOURCE_NAME extends LogKey
  case object RESOURCE_PROFILE_ID extends LogKey
  case object RESOURCE_PROFILE_IDS extends LogKey
  case object RESOURCE_PROFILE_TO_TOTAL_EXECS extends LogKey
  case object RESPONSE_BODY_SIZE extends LogKey
  case object RESTART_TIME extends LogKey
  case object RESULT extends LogKey
  case object RESULT_SIZE_BYTES extends LogKey
  case object RESULT_SIZE_BYTES_MAX extends LogKey
  case object RETRY_INTERVAL extends LogKey
  case object RETRY_WAIT_TIME extends LogKey
  case object RIGHT_EXPR extends LogKey
  case object RIGHT_LOGICAL_PLAN_STATS_SIZE_IN_BYTES extends LogKey
  case object RMSE extends LogKey
  case object ROCKS_DB_LOG_LEVEL extends LogKey
  case object ROCKS_DB_LOG_MESSAGE extends LogKey
  case object RPC_ADDRESS extends LogKey
  case object RPC_ENDPOINT_REF extends LogKey
  case object RPC_MESSAGE_CAPACITY extends LogKey
  case object RPC_SSL_ENABLED extends LogKey
  case object RULE_EXECUTOR_NAME extends LogKey
  case object RULE_NAME extends LogKey
  case object RUN_ID extends LogKey
  case object RUN_ID_STRING extends LogKey
  case object SCALA_VERSION extends LogKey
  case object SCALING_DOWN_RATIO extends LogKey
  case object SCALING_UP_RATIO extends LogKey
  case object SCHEDULER_POOL_NAME extends LogKey
  case object SCHEDULING_MODE extends LogKey
  case object SCHEMA extends LogKey
  case object SCHEMA2 extends LogKey
  case object SERVER_NAME extends LogKey
  case object SERVICE_NAME extends LogKey
  case object SERVLET_CONTEXT_HANDLER_PATH extends LogKey
  case object SESSION_HANDLE extends LogKey
  case object SESSION_HOLD_INFO extends LogKey
  case object SESSION_ID extends LogKey
  case object SESSION_KEY extends LogKey
  case object SET_CLIENT_INFO_REQUEST extends LogKey
  case object SHARD_ID extends LogKey
  case object SHORTER_SERVICE_NAME extends LogKey
  case object SHORT_USER_NAME extends LogKey
  case object SHUFFLE_BLOCK_INFO extends LogKey
  case object SHUFFLE_DB_BACKEND_KEY extends LogKey
  case object SHUFFLE_DB_BACKEND_NAME extends LogKey
  case object SHUFFLE_ID extends LogKey
  case object SHUFFLE_IDS extends LogKey
  case object SHUFFLE_MERGE_ID extends LogKey
  case object SHUFFLE_MERGE_RECOVERY_FILE extends LogKey
  case object SHUFFLE_SERVICE_CONF_OVERLAY_URL extends LogKey
  case object SHUFFLE_SERVICE_METRICS_NAMESPACE extends LogKey
  case object SHUFFLE_SERVICE_NAME extends LogKey
  case object SIGMAS_LENGTH extends LogKey
  case object SIGNAL extends LogKey
  case object SINK extends LogKey
  case object SIZE extends LogKey
  case object SLEEP_TIME extends LogKey
  case object SLIDE_DURATION extends LogKey
  case object SMALLEST_CLUSTER_INDEX extends LogKey
  case object SNAPSHOT_VERSION extends LogKey
  case object SOCKET_ADDRESS extends LogKey
  case object SOURCE extends LogKey
  case object SOURCE_PATH extends LogKey
  case object SPARK_BRANCH extends LogKey
  case object SPARK_BUILD_DATE extends LogKey
  case object SPARK_BUILD_USER extends LogKey
  case object SPARK_DATA_STREAM extends LogKey
  case object SPARK_PLAN_ID extends LogKey
  case object SPARK_REPO_URL extends LogKey
  case object SPARK_REVISION extends LogKey
  case object SPARK_VERSION extends LogKey
  case object SPILL_TIMES extends LogKey
  case object SQL_TEXT extends LogKey
  case object SRC_PATH extends LogKey
  case object STAGE extends LogKey
  case object STAGES extends LogKey
  case object STAGE_ATTEMPT extends LogKey
  case object STAGE_ATTEMPT_ID extends LogKey
  case object STAGE_ID extends LogKey
  case object STAGE_NAME extends LogKey
  case object START_INDEX extends LogKey
  case object START_TIME extends LogKey
  case object STATEMENT_ID extends LogKey
  case object STATE_NAME extends LogKey
  case object STATE_STORE_COORDINATOR extends LogKey
  case object STATE_STORE_ID extends LogKey
  case object STATE_STORE_PROVIDER extends LogKey
  case object STATE_STORE_PROVIDER_ID extends LogKey
  case object STATE_STORE_PROVIDER_IDS extends LogKey
  case object STATE_STORE_VERSION extends LogKey
  case object STATS extends LogKey
  case object STATUS extends LogKey
  case object STDERR extends LogKey
  case object STOP_SITE_SHORT_FORM extends LogKey
  case object STORAGE_LEVEL extends LogKey
  case object STORAGE_LEVEL_DESERIALIZED extends LogKey
  case object STORAGE_LEVEL_REPLICATION extends LogKey
  case object STORAGE_MEMORY_SIZE extends LogKey
  case object STORE_ID extends LogKey
  case object STRATEGY extends LogKey
  case object STREAMING_CONTEXT extends LogKey
  case object STREAMING_DATA_SOURCE_DESCRIPTION extends LogKey
  case object STREAMING_DATA_SOURCE_NAME extends LogKey
  case object STREAMING_OFFSETS_END extends LogKey
  case object STREAMING_OFFSETS_START extends LogKey
  case object STREAMING_QUERY_PROGRESS extends LogKey
  case object STREAMING_SOURCE extends LogKey
  case object STREAMING_TABLE extends LogKey
  case object STREAMING_WRITE extends LogKey
  case object STREAM_CHUNK_ID extends LogKey
  case object STREAM_ID extends LogKey
  case object STREAM_NAME extends LogKey
  case object SUBMISSION_ID extends LogKey
  case object SUBSAMPLING_RATE extends LogKey
  case object SUB_QUERY extends LogKey
  case object TABLE_NAME extends LogKey
  case object TABLE_TYPE extends LogKey
  case object TABLE_TYPES extends LogKey
  case object TAG extends LogKey
  case object TARGET_NUM_EXECUTOR extends LogKey
  case object TARGET_NUM_EXECUTOR_DELTA extends LogKey
  case object TARGET_PATH extends LogKey
  case object TARGET_SIZE extends LogKey
  case object TASK_ATTEMPT_ID extends LogKey
  case object TASK_ID extends LogKey
  case object TASK_INDEX extends LogKey
  case object TASK_LOCALITY extends LogKey
  case object TASK_NAME extends LogKey
  case object TASK_REQUIREMENTS extends LogKey
  case object TASK_RESOURCES extends LogKey
  case object TASK_RESOURCE_ASSIGNMENTS extends LogKey
  case object TASK_SET_MANAGER extends LogKey
  case object TASK_SET_NAME extends LogKey
  case object TASK_STATE extends LogKey
  case object TEMP_FILE extends LogKey
  case object TEMP_OUTPUT_PATH extends LogKey
  case object TEMP_PATH extends LogKey
  case object TEST_SIZE extends LogKey
  case object THREAD extends LogKey
  case object THREAD_ID extends LogKey
  case object THREAD_NAME extends LogKey
  case object THREAD_POOL_KEEPALIVE_TIME extends LogKey
  case object THREAD_POOL_SIZE extends LogKey
  case object THREAD_POOL_WAIT_QUEUE_SIZE extends LogKey
  case object THRESHOLD extends LogKey
  case object THRESH_TIME extends LogKey
  case object TIME extends LogKey
  case object TIMEOUT extends LogKey
  case object TIMER extends LogKey
  case object TIMESTAMP extends LogKey
  case object TIME_UNITS extends LogKey
  case object TIP extends LogKey
  case object TOKEN extends LogKey
  case object TOKEN_KIND extends LogKey
  case object TOKEN_REGEX extends LogKey
  case object TOKEN_RENEWER extends LogKey
  case object TOPIC extends LogKey
  case object TOPIC_PARTITION extends LogKey
  case object TOPIC_PARTITIONS extends LogKey
  case object TOPIC_PARTITION_OFFSET extends LogKey
  case object TOPIC_PARTITION_OFFSET_RANGE extends LogKey
  case object TOTAL extends LogKey
  case object TOTAL_EFFECTIVE_TIME extends LogKey
  case object TOTAL_SIZE extends LogKey
  case object TOTAL_TIME extends LogKey
  case object TOTAL_TIME_READ extends LogKey
  case object TO_TIME extends LogKey
  case object TRAINING_SIZE extends LogKey
  case object TRAIN_VALIDATION_SPLIT_METRIC extends LogKey
  case object TRAIN_VALIDATION_SPLIT_METRICS extends LogKey
  case object TRANSFER_TYPE extends LogKey
  case object TREE_NODE extends LogKey
  case object TRIGGER_INTERVAL extends LogKey
  case object UI_ACLS extends LogKey
  case object UI_FILTER extends LogKey
  case object UI_FILTER_PARAMS extends LogKey
  case object UI_PROXY_BASE extends LogKey
  case object UNKNOWN_PARAM extends LogKey
  case object UNSUPPORTED_EXPR extends LogKey
  case object UNSUPPORTED_HINT_REASON extends LogKey
  case object UNTIL_OFFSET extends LogKey
  case object UPPER_BOUND extends LogKey
  case object URI extends LogKey
  case object URIS extends LogKey
  case object URL extends LogKey
  case object URL2 extends LogKey
  case object URLS extends LogKey
  case object USER_ID extends LogKey
  case object USER_NAME extends LogKey
  case object UUID extends LogKey
  case object VALUE extends LogKey
  case object VERSIONS_TO_DELETE extends LogKey
  case object VERSION_NUM extends LogKey
  case object VIEW_ACLS extends LogKey
  case object VIEW_ACLS_GROUPS extends LogKey
  case object VIRTUAL_CORES extends LogKey
  case object VOCAB_SIZE extends LogKey
  case object WAIT_RESULT_TIME extends LogKey
  case object WAIT_SEND_TIME extends LogKey
  case object WATERMARK_CONSTRAINT extends LogKey
  case object WEB_URL extends LogKey
  case object WEIGHT extends LogKey
  case object WORKER extends LogKey
  case object WORKER_HOST extends LogKey
  case object WORKER_ID extends LogKey
  case object WORKER_PORT extends LogKey
  case object WORKER_URL extends LogKey
  case object WRITE_AHEAD_LOG_INFO extends LogKey
  case object WRITE_AHEAD_LOG_RECORD_HANDLE extends LogKey
  case object WRITE_JOB_UUID extends LogKey
  case object XML_SCHEDULING_MODE extends LogKey
  case object XSD_PATH extends LogKey
  case object YARN_RESOURCE extends LogKey
  case object YOUNG_GENERATION_GC extends LogKey
  case object ZERO_TIME extends LogKey
}
