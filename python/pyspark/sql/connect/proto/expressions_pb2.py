#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# NO CHECKED-IN PROTOBUF GENCODE
# source: spark/connect/expressions.proto
# Protobuf Python Version: 5.28.3
"""Generated protocol buffer code."""
from google.protobuf import descriptor as _descriptor
from google.protobuf import descriptor_pool as _descriptor_pool
from google.protobuf import runtime_version as _runtime_version
from google.protobuf import symbol_database as _symbol_database
from google.protobuf.internal import builder as _builder

_runtime_version.ValidateProtobufRuntimeVersion(
    _runtime_version.Domain.PUBLIC, 5, 28, 3, "", "spark/connect/expressions.proto"
)
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from google.protobuf import any_pb2 as google_dot_protobuf_dot_any__pb2
from pyspark.sql.connect.proto import types_pb2 as spark_dot_connect_dot_types__pb2
from pyspark.sql.connect.proto import common_pb2 as spark_dot_connect_dot_common__pb2


DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(
    b'\n\x1fspark/connect/expressions.proto\x12\rspark.connect\x1a\x19google/protobuf/any.proto\x1a\x19spark/connect/types.proto\x1a\x1aspark/connect/common.proto"\xf3\x34\n\nExpression\x12\x37\n\x06\x63ommon\x18\x12 \x01(\x0b\x32\x1f.spark.connect.ExpressionCommonR\x06\x63ommon\x12=\n\x07literal\x18\x01 \x01(\x0b\x32!.spark.connect.Expression.LiteralH\x00R\x07literal\x12\x62\n\x14unresolved_attribute\x18\x02 \x01(\x0b\x32-.spark.connect.Expression.UnresolvedAttributeH\x00R\x13unresolvedAttribute\x12_\n\x13unresolved_function\x18\x03 \x01(\x0b\x32,.spark.connect.Expression.UnresolvedFunctionH\x00R\x12unresolvedFunction\x12Y\n\x11\x65xpression_string\x18\x04 \x01(\x0b\x32*.spark.connect.Expression.ExpressionStringH\x00R\x10\x65xpressionString\x12S\n\x0funresolved_star\x18\x05 \x01(\x0b\x32(.spark.connect.Expression.UnresolvedStarH\x00R\x0eunresolvedStar\x12\x37\n\x05\x61lias\x18\x06 \x01(\x0b\x32\x1f.spark.connect.Expression.AliasH\x00R\x05\x61lias\x12\x34\n\x04\x63\x61st\x18\x07 \x01(\x0b\x32\x1e.spark.connect.Expression.CastH\x00R\x04\x63\x61st\x12V\n\x10unresolved_regex\x18\x08 \x01(\x0b\x32).spark.connect.Expression.UnresolvedRegexH\x00R\x0funresolvedRegex\x12\x44\n\nsort_order\x18\t \x01(\x0b\x32#.spark.connect.Expression.SortOrderH\x00R\tsortOrder\x12S\n\x0flambda_function\x18\n \x01(\x0b\x32(.spark.connect.Expression.LambdaFunctionH\x00R\x0elambdaFunction\x12:\n\x06window\x18\x0b \x01(\x0b\x32 .spark.connect.Expression.WindowH\x00R\x06window\x12l\n\x18unresolved_extract_value\x18\x0c \x01(\x0b\x32\x30.spark.connect.Expression.UnresolvedExtractValueH\x00R\x16unresolvedExtractValue\x12M\n\rupdate_fields\x18\r \x01(\x0b\x32&.spark.connect.Expression.UpdateFieldsH\x00R\x0cupdateFields\x12\x82\x01\n unresolved_named_lambda_variable\x18\x0e \x01(\x0b\x32\x37.spark.connect.Expression.UnresolvedNamedLambdaVariableH\x00R\x1dunresolvedNamedLambdaVariable\x12~\n#common_inline_user_defined_function\x18\x0f \x01(\x0b\x32..spark.connect.CommonInlineUserDefinedFunctionH\x00R\x1f\x63ommonInlineUserDefinedFunction\x12\x42\n\rcall_function\x18\x10 \x01(\x0b\x32\x1b.spark.connect.CallFunctionH\x00R\x0c\x63\x61llFunction\x12\x64\n\x19named_argument_expression\x18\x11 \x01(\x0b\x32&.spark.connect.NamedArgumentExpressionH\x00R\x17namedArgumentExpression\x12?\n\x0cmerge_action\x18\x13 \x01(\x0b\x32\x1a.spark.connect.MergeActionH\x00R\x0bmergeAction\x12g\n\x1atyped_aggregate_expression\x18\x14 \x01(\x0b\x32\'.spark.connect.TypedAggregateExpressionH\x00R\x18typedAggregateExpression\x12T\n\x13subquery_expression\x18\x15 \x01(\x0b\x32!.spark.connect.SubqueryExpressionH\x00R\x12subqueryExpression\x12\x35\n\textension\x18\xe7\x07 \x01(\x0b\x32\x14.google.protobuf.AnyH\x00R\textension\x1a\x8f\x06\n\x06Window\x12\x42\n\x0fwindow_function\x18\x01 \x01(\x0b\x32\x19.spark.connect.ExpressionR\x0ewindowFunction\x12@\n\x0epartition_spec\x18\x02 \x03(\x0b\x32\x19.spark.connect.ExpressionR\rpartitionSpec\x12\x42\n\norder_spec\x18\x03 \x03(\x0b\x32#.spark.connect.Expression.SortOrderR\torderSpec\x12K\n\nframe_spec\x18\x04 \x01(\x0b\x32,.spark.connect.Expression.Window.WindowFrameR\tframeSpec\x1a\xed\x03\n\x0bWindowFrame\x12U\n\nframe_type\x18\x01 \x01(\x0e\x32\x36.spark.connect.Expression.Window.WindowFrame.FrameTypeR\tframeType\x12P\n\x05lower\x18\x02 \x01(\x0b\x32:.spark.connect.Expression.Window.WindowFrame.FrameBoundaryR\x05lower\x12P\n\x05upper\x18\x03 \x01(\x0b\x32:.spark.connect.Expression.Window.WindowFrame.FrameBoundaryR\x05upper\x1a\x91\x01\n\rFrameBoundary\x12!\n\x0b\x63urrent_row\x18\x01 \x01(\x08H\x00R\ncurrentRow\x12\x1e\n\tunbounded\x18\x02 \x01(\x08H\x00R\tunbounded\x12\x31\n\x05value\x18\x03 \x01(\x0b\x32\x19.spark.connect.ExpressionH\x00R\x05valueB\n\n\x08\x62oundary"O\n\tFrameType\x12\x18\n\x14\x46RAME_TYPE_UNDEFINED\x10\x00\x12\x12\n\x0e\x46RAME_TYPE_ROW\x10\x01\x12\x14\n\x10\x46RAME_TYPE_RANGE\x10\x02\x1a\xa9\x03\n\tSortOrder\x12/\n\x05\x63hild\x18\x01 \x01(\x0b\x32\x19.spark.connect.ExpressionR\x05\x63hild\x12O\n\tdirection\x18\x02 \x01(\x0e\x32\x31.spark.connect.Expression.SortOrder.SortDirectionR\tdirection\x12U\n\rnull_ordering\x18\x03 \x01(\x0e\x32\x30.spark.connect.Expression.SortOrder.NullOrderingR\x0cnullOrdering"l\n\rSortDirection\x12\x1e\n\x1aSORT_DIRECTION_UNSPECIFIED\x10\x00\x12\x1c\n\x18SORT_DIRECTION_ASCENDING\x10\x01\x12\x1d\n\x19SORT_DIRECTION_DESCENDING\x10\x02"U\n\x0cNullOrdering\x12\x1a\n\x16SORT_NULLS_UNSPECIFIED\x10\x00\x12\x14\n\x10SORT_NULLS_FIRST\x10\x01\x12\x13\n\x0fSORT_NULLS_LAST\x10\x02\x1a\xbb\x02\n\x04\x43\x61st\x12-\n\x04\x65xpr\x18\x01 \x01(\x0b\x32\x19.spark.connect.ExpressionR\x04\x65xpr\x12-\n\x04type\x18\x02 \x01(\x0b\x32\x17.spark.connect.DataTypeH\x00R\x04type\x12\x1b\n\x08type_str\x18\x03 \x01(\tH\x00R\x07typeStr\x12\x44\n\teval_mode\x18\x04 \x01(\x0e\x32\'.spark.connect.Expression.Cast.EvalModeR\x08\x65valMode"b\n\x08\x45valMode\x12\x19\n\x15\x45VAL_MODE_UNSPECIFIED\x10\x00\x12\x14\n\x10\x45VAL_MODE_LEGACY\x10\x01\x12\x12\n\x0e\x45VAL_MODE_ANSI\x10\x02\x12\x11\n\rEVAL_MODE_TRY\x10\x03\x42\x0e\n\x0c\x63\x61st_to_type\x1a\xc1\x0f\n\x07Literal\x12-\n\x04null\x18\x01 \x01(\x0b\x32\x17.spark.connect.DataTypeH\x00R\x04null\x12\x18\n\x06\x62inary\x18\x02 \x01(\x0cH\x00R\x06\x62inary\x12\x1a\n\x07\x62oolean\x18\x03 \x01(\x08H\x00R\x07\x62oolean\x12\x14\n\x04\x62yte\x18\x04 \x01(\x05H\x00R\x04\x62yte\x12\x16\n\x05short\x18\x05 \x01(\x05H\x00R\x05short\x12\x1a\n\x07integer\x18\x06 \x01(\x05H\x00R\x07integer\x12\x14\n\x04long\x18\x07 \x01(\x03H\x00R\x04long\x12\x16\n\x05\x66loat\x18\n \x01(\x02H\x00R\x05\x66loat\x12\x18\n\x06\x64ouble\x18\x0b \x01(\x01H\x00R\x06\x64ouble\x12\x45\n\x07\x64\x65\x63imal\x18\x0c \x01(\x0b\x32).spark.connect.Expression.Literal.DecimalH\x00R\x07\x64\x65\x63imal\x12\x18\n\x06string\x18\r \x01(\tH\x00R\x06string\x12\x14\n\x04\x64\x61te\x18\x10 \x01(\x05H\x00R\x04\x64\x61te\x12\x1e\n\ttimestamp\x18\x11 \x01(\x03H\x00R\ttimestamp\x12%\n\rtimestamp_ntz\x18\x12 \x01(\x03H\x00R\x0ctimestampNtz\x12\x61\n\x11\x63\x61lendar_interval\x18\x13 \x01(\x0b\x32\x32.spark.connect.Expression.Literal.CalendarIntervalH\x00R\x10\x63\x61lendarInterval\x12\x30\n\x13year_month_interval\x18\x14 \x01(\x05H\x00R\x11yearMonthInterval\x12,\n\x11\x64\x61y_time_interval\x18\x15 \x01(\x03H\x00R\x0f\x64\x61yTimeInterval\x12?\n\x05\x61rray\x18\x16 \x01(\x0b\x32\'.spark.connect.Expression.Literal.ArrayH\x00R\x05\x61rray\x12\x39\n\x03map\x18\x17 \x01(\x0b\x32%.spark.connect.Expression.Literal.MapH\x00R\x03map\x12\x42\n\x06struct\x18\x18 \x01(\x0b\x32(.spark.connect.Expression.Literal.StructH\x00R\x06struct\x12\x61\n\x11specialized_array\x18\x19 \x01(\x0b\x32\x32.spark.connect.Expression.Literal.SpecializedArrayH\x00R\x10specializedArray\x1au\n\x07\x44\x65\x63imal\x12\x14\n\x05value\x18\x01 \x01(\tR\x05value\x12!\n\tprecision\x18\x02 \x01(\x05H\x00R\tprecision\x88\x01\x01\x12\x19\n\x05scale\x18\x03 \x01(\x05H\x01R\x05scale\x88\x01\x01\x42\x0c\n\n_precisionB\x08\n\x06_scale\x1a\x62\n\x10\x43\x61lendarInterval\x12\x16\n\x06months\x18\x01 \x01(\x05R\x06months\x12\x12\n\x04\x64\x61ys\x18\x02 \x01(\x05R\x04\x64\x61ys\x12"\n\x0cmicroseconds\x18\x03 \x01(\x03R\x0cmicroseconds\x1a\x82\x01\n\x05\x41rray\x12:\n\x0c\x65lement_type\x18\x01 \x01(\x0b\x32\x17.spark.connect.DataTypeR\x0b\x65lementType\x12=\n\x08\x65lements\x18\x02 \x03(\x0b\x32!.spark.connect.Expression.LiteralR\x08\x65lements\x1a\xe3\x01\n\x03Map\x12\x32\n\x08key_type\x18\x01 \x01(\x0b\x32\x17.spark.connect.DataTypeR\x07keyType\x12\x36\n\nvalue_type\x18\x02 \x01(\x0b\x32\x17.spark.connect.DataTypeR\tvalueType\x12\x35\n\x04keys\x18\x03 \x03(\x0b\x32!.spark.connect.Expression.LiteralR\x04keys\x12\x39\n\x06values\x18\x04 \x03(\x0b\x32!.spark.connect.Expression.LiteralR\x06values\x1a\x81\x01\n\x06Struct\x12\x38\n\x0bstruct_type\x18\x01 \x01(\x0b\x32\x17.spark.connect.DataTypeR\nstructType\x12=\n\x08\x65lements\x18\x02 \x03(\x0b\x32!.spark.connect.Expression.LiteralR\x08\x65lements\x1a\xc0\x02\n\x10SpecializedArray\x12,\n\x05\x62ools\x18\x01 \x01(\x0b\x32\x14.spark.connect.BoolsH\x00R\x05\x62ools\x12)\n\x04ints\x18\x02 \x01(\x0b\x32\x13.spark.connect.IntsH\x00R\x04ints\x12,\n\x05longs\x18\x03 \x01(\x0b\x32\x14.spark.connect.LongsH\x00R\x05longs\x12/\n\x06\x66loats\x18\x04 \x01(\x0b\x32\x15.spark.connect.FloatsH\x00R\x06\x66loats\x12\x32\n\x07\x64oubles\x18\x05 \x01(\x0b\x32\x16.spark.connect.DoublesH\x00R\x07\x64oubles\x12\x32\n\x07strings\x18\x06 \x01(\x0b\x32\x16.spark.connect.StringsH\x00R\x07stringsB\x0c\n\nvalue_typeB\x0e\n\x0cliteral_type\x1a\xba\x01\n\x13UnresolvedAttribute\x12/\n\x13unparsed_identifier\x18\x01 \x01(\tR\x12unparsedIdentifier\x12\x1c\n\x07plan_id\x18\x02 \x01(\x03H\x00R\x06planId\x88\x01\x01\x12\x31\n\x12is_metadata_column\x18\x03 \x01(\x08H\x01R\x10isMetadataColumn\x88\x01\x01\x42\n\n\x08_plan_idB\x15\n\x13_is_metadata_column\x1a\x82\x02\n\x12UnresolvedFunction\x12#\n\rfunction_name\x18\x01 \x01(\tR\x0c\x66unctionName\x12\x37\n\targuments\x18\x02 \x03(\x0b\x32\x19.spark.connect.ExpressionR\targuments\x12\x1f\n\x0bis_distinct\x18\x03 \x01(\x08R\nisDistinct\x12\x37\n\x18is_user_defined_function\x18\x04 \x01(\x08R\x15isUserDefinedFunction\x12$\n\x0bis_internal\x18\x05 \x01(\x08H\x00R\nisInternal\x88\x01\x01\x42\x0e\n\x0c_is_internal\x1a\x32\n\x10\x45xpressionString\x12\x1e\n\nexpression\x18\x01 \x01(\tR\nexpression\x1a|\n\x0eUnresolvedStar\x12,\n\x0funparsed_target\x18\x01 \x01(\tH\x00R\x0eunparsedTarget\x88\x01\x01\x12\x1c\n\x07plan_id\x18\x02 \x01(\x03H\x01R\x06planId\x88\x01\x01\x42\x12\n\x10_unparsed_targetB\n\n\x08_plan_id\x1aV\n\x0fUnresolvedRegex\x12\x19\n\x08\x63ol_name\x18\x01 \x01(\tR\x07\x63olName\x12\x1c\n\x07plan_id\x18\x02 \x01(\x03H\x00R\x06planId\x88\x01\x01\x42\n\n\x08_plan_id\x1a\x84\x01\n\x16UnresolvedExtractValue\x12/\n\x05\x63hild\x18\x01 \x01(\x0b\x32\x19.spark.connect.ExpressionR\x05\x63hild\x12\x39\n\nextraction\x18\x02 \x01(\x0b\x32\x19.spark.connect.ExpressionR\nextraction\x1a\xbb\x01\n\x0cUpdateFields\x12\x46\n\x11struct_expression\x18\x01 \x01(\x0b\x32\x19.spark.connect.ExpressionR\x10structExpression\x12\x1d\n\nfield_name\x18\x02 \x01(\tR\tfieldName\x12\x44\n\x10value_expression\x18\x03 \x01(\x0b\x32\x19.spark.connect.ExpressionR\x0fvalueExpression\x1ax\n\x05\x41lias\x12-\n\x04\x65xpr\x18\x01 \x01(\x0b\x32\x19.spark.connect.ExpressionR\x04\x65xpr\x12\x12\n\x04name\x18\x02 \x03(\tR\x04name\x12\x1f\n\x08metadata\x18\x03 \x01(\tH\x00R\x08metadata\x88\x01\x01\x42\x0b\n\t_metadata\x1a\x9e\x01\n\x0eLambdaFunction\x12\x35\n\x08\x66unction\x18\x01 \x01(\x0b\x32\x19.spark.connect.ExpressionR\x08\x66unction\x12U\n\targuments\x18\x02 \x03(\x0b\x32\x37.spark.connect.Expression.UnresolvedNamedLambdaVariableR\targuments\x1a>\n\x1dUnresolvedNamedLambdaVariable\x12\x1d\n\nname_parts\x18\x01 \x03(\tR\tnamePartsB\x0b\n\texpr_type"A\n\x10\x45xpressionCommon\x12-\n\x06origin\x18\x01 \x01(\x0b\x32\x15.spark.connect.OriginR\x06origin"\x8d\x03\n\x1f\x43ommonInlineUserDefinedFunction\x12#\n\rfunction_name\x18\x01 \x01(\tR\x0c\x66unctionName\x12$\n\rdeterministic\x18\x02 \x01(\x08R\rdeterministic\x12\x37\n\targuments\x18\x03 \x03(\x0b\x32\x19.spark.connect.ExpressionR\targuments\x12\x39\n\npython_udf\x18\x04 \x01(\x0b\x32\x18.spark.connect.PythonUDFH\x00R\tpythonUdf\x12I\n\x10scalar_scala_udf\x18\x05 \x01(\x0b\x32\x1d.spark.connect.ScalarScalaUDFH\x00R\x0escalarScalaUdf\x12\x33\n\x08java_udf\x18\x06 \x01(\x0b\x32\x16.spark.connect.JavaUDFH\x00R\x07javaUdf\x12\x1f\n\x0bis_distinct\x18\x07 \x01(\x08R\nisDistinctB\n\n\x08\x66unction"\xcc\x01\n\tPythonUDF\x12\x38\n\x0boutput_type\x18\x01 \x01(\x0b\x32\x17.spark.connect.DataTypeR\noutputType\x12\x1b\n\teval_type\x18\x02 \x01(\x05R\x08\x65valType\x12\x18\n\x07\x63ommand\x18\x03 \x01(\x0cR\x07\x63ommand\x12\x1d\n\npython_ver\x18\x04 \x01(\tR\tpythonVer\x12/\n\x13\x61\x64\x64itional_includes\x18\x05 \x03(\tR\x12\x61\x64\x64itionalIncludes"\xd6\x01\n\x0eScalarScalaUDF\x12\x18\n\x07payload\x18\x01 \x01(\x0cR\x07payload\x12\x37\n\ninputTypes\x18\x02 \x03(\x0b\x32\x17.spark.connect.DataTypeR\ninputTypes\x12\x37\n\noutputType\x18\x03 \x01(\x0b\x32\x17.spark.connect.DataTypeR\noutputType\x12\x1a\n\x08nullable\x18\x04 \x01(\x08R\x08nullable\x12\x1c\n\taggregate\x18\x05 \x01(\x08R\taggregate"\x95\x01\n\x07JavaUDF\x12\x1d\n\nclass_name\x18\x01 \x01(\tR\tclassName\x12=\n\x0boutput_type\x18\x02 \x01(\x0b\x32\x17.spark.connect.DataTypeH\x00R\noutputType\x88\x01\x01\x12\x1c\n\taggregate\x18\x03 \x01(\x08R\taggregateB\x0e\n\x0c_output_type"c\n\x18TypedAggregateExpression\x12G\n\x10scalar_scala_udf\x18\x01 \x01(\x0b\x32\x1d.spark.connect.ScalarScalaUDFR\x0escalarScalaUdf"l\n\x0c\x43\x61llFunction\x12#\n\rfunction_name\x18\x01 \x01(\tR\x0c\x66unctionName\x12\x37\n\targuments\x18\x02 \x03(\x0b\x32\x19.spark.connect.ExpressionR\targuments"\\\n\x17NamedArgumentExpression\x12\x10\n\x03key\x18\x01 \x01(\tR\x03key\x12/\n\x05value\x18\x02 \x01(\x0b\x32\x19.spark.connect.ExpressionR\x05value"\x80\x04\n\x0bMergeAction\x12\x46\n\x0b\x61\x63tion_type\x18\x01 \x01(\x0e\x32%.spark.connect.MergeAction.ActionTypeR\nactionType\x12<\n\tcondition\x18\x02 \x01(\x0b\x32\x19.spark.connect.ExpressionH\x00R\tcondition\x88\x01\x01\x12G\n\x0b\x61ssignments\x18\x03 \x03(\x0b\x32%.spark.connect.MergeAction.AssignmentR\x0b\x61ssignments\x1aj\n\nAssignment\x12+\n\x03key\x18\x01 \x01(\x0b\x32\x19.spark.connect.ExpressionR\x03key\x12/\n\x05value\x18\x02 \x01(\x0b\x32\x19.spark.connect.ExpressionR\x05value"\xa7\x01\n\nActionType\x12\x17\n\x13\x41\x43TION_TYPE_INVALID\x10\x00\x12\x16\n\x12\x41\x43TION_TYPE_DELETE\x10\x01\x12\x16\n\x12\x41\x43TION_TYPE_INSERT\x10\x02\x12\x1b\n\x17\x41\x43TION_TYPE_INSERT_STAR\x10\x03\x12\x16\n\x12\x41\x43TION_TYPE_UPDATE\x10\x04\x12\x1b\n\x17\x41\x43TION_TYPE_UPDATE_STAR\x10\x05\x42\x0c\n\n_condition"\xe5\x04\n\x12SubqueryExpression\x12\x17\n\x07plan_id\x18\x01 \x01(\x03R\x06planId\x12S\n\rsubquery_type\x18\x02 \x01(\x0e\x32..spark.connect.SubqueryExpression.SubqueryTypeR\x0csubqueryType\x12\x62\n\x11table_arg_options\x18\x03 \x01(\x0b\x32\x31.spark.connect.SubqueryExpression.TableArgOptionsH\x00R\x0ftableArgOptions\x88\x01\x01\x1a\xea\x01\n\x0fTableArgOptions\x12@\n\x0epartition_spec\x18\x01 \x03(\x0b\x32\x19.spark.connect.ExpressionR\rpartitionSpec\x12\x42\n\norder_spec\x18\x02 \x03(\x0b\x32#.spark.connect.Expression.SortOrderR\torderSpec\x12\x37\n\x15with_single_partition\x18\x03 \x01(\x08H\x00R\x13withSinglePartition\x88\x01\x01\x42\x18\n\x16_with_single_partition"z\n\x0cSubqueryType\x12\x19\n\x15SUBQUERY_TYPE_UNKNOWN\x10\x00\x12\x18\n\x14SUBQUERY_TYPE_SCALAR\x10\x01\x12\x18\n\x14SUBQUERY_TYPE_EXISTS\x10\x02\x12\x1b\n\x17SUBQUERY_TYPE_TABLE_ARG\x10\x03\x42\x14\n\x12_table_arg_optionsB6\n\x1eorg.apache.spark.connect.protoP\x01Z\x12internal/generatedb\x06proto3'
)

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(
    DESCRIPTOR, "pyspark.sql.connect.proto.expressions_pb2", _globals
)
if not _descriptor._USE_C_DESCRIPTORS:
    _globals["DESCRIPTOR"]._loaded_options = None
    _globals[
        "DESCRIPTOR"
    ]._serialized_options = b"\n\036org.apache.spark.connect.protoP\001Z\022internal/generated"
    _globals["_EXPRESSION"]._serialized_start = 133
    _globals["_EXPRESSION"]._serialized_end = 6904
    _globals["_EXPRESSION_WINDOW"]._serialized_start = 1986
    _globals["_EXPRESSION_WINDOW"]._serialized_end = 2769
    _globals["_EXPRESSION_WINDOW_WINDOWFRAME"]._serialized_start = 2276
    _globals["_EXPRESSION_WINDOW_WINDOWFRAME"]._serialized_end = 2769
    _globals["_EXPRESSION_WINDOW_WINDOWFRAME_FRAMEBOUNDARY"]._serialized_start = 2543
    _globals["_EXPRESSION_WINDOW_WINDOWFRAME_FRAMEBOUNDARY"]._serialized_end = 2688
    _globals["_EXPRESSION_WINDOW_WINDOWFRAME_FRAMETYPE"]._serialized_start = 2690
    _globals["_EXPRESSION_WINDOW_WINDOWFRAME_FRAMETYPE"]._serialized_end = 2769
    _globals["_EXPRESSION_SORTORDER"]._serialized_start = 2772
    _globals["_EXPRESSION_SORTORDER"]._serialized_end = 3197
    _globals["_EXPRESSION_SORTORDER_SORTDIRECTION"]._serialized_start = 3002
    _globals["_EXPRESSION_SORTORDER_SORTDIRECTION"]._serialized_end = 3110
    _globals["_EXPRESSION_SORTORDER_NULLORDERING"]._serialized_start = 3112
    _globals["_EXPRESSION_SORTORDER_NULLORDERING"]._serialized_end = 3197
    _globals["_EXPRESSION_CAST"]._serialized_start = 3200
    _globals["_EXPRESSION_CAST"]._serialized_end = 3515
    _globals["_EXPRESSION_CAST_EVALMODE"]._serialized_start = 3401
    _globals["_EXPRESSION_CAST_EVALMODE"]._serialized_end = 3499
    _globals["_EXPRESSION_LITERAL"]._serialized_start = 3518
    _globals["_EXPRESSION_LITERAL"]._serialized_end = 5503
    _globals["_EXPRESSION_LITERAL_DECIMAL"]._serialized_start = 4452
    _globals["_EXPRESSION_LITERAL_DECIMAL"]._serialized_end = 4569
    _globals["_EXPRESSION_LITERAL_CALENDARINTERVAL"]._serialized_start = 4571
    _globals["_EXPRESSION_LITERAL_CALENDARINTERVAL"]._serialized_end = 4669
    _globals["_EXPRESSION_LITERAL_ARRAY"]._serialized_start = 4672
    _globals["_EXPRESSION_LITERAL_ARRAY"]._serialized_end = 4802
    _globals["_EXPRESSION_LITERAL_MAP"]._serialized_start = 4805
    _globals["_EXPRESSION_LITERAL_MAP"]._serialized_end = 5032
    _globals["_EXPRESSION_LITERAL_STRUCT"]._serialized_start = 5035
    _globals["_EXPRESSION_LITERAL_STRUCT"]._serialized_end = 5164
    _globals["_EXPRESSION_LITERAL_SPECIALIZEDARRAY"]._serialized_start = 5167
    _globals["_EXPRESSION_LITERAL_SPECIALIZEDARRAY"]._serialized_end = 5487
    _globals["_EXPRESSION_UNRESOLVEDATTRIBUTE"]._serialized_start = 5506
    _globals["_EXPRESSION_UNRESOLVEDATTRIBUTE"]._serialized_end = 5692
    _globals["_EXPRESSION_UNRESOLVEDFUNCTION"]._serialized_start = 5695
    _globals["_EXPRESSION_UNRESOLVEDFUNCTION"]._serialized_end = 5953
    _globals["_EXPRESSION_EXPRESSIONSTRING"]._serialized_start = 5955
    _globals["_EXPRESSION_EXPRESSIONSTRING"]._serialized_end = 6005
    _globals["_EXPRESSION_UNRESOLVEDSTAR"]._serialized_start = 6007
    _globals["_EXPRESSION_UNRESOLVEDSTAR"]._serialized_end = 6131
    _globals["_EXPRESSION_UNRESOLVEDREGEX"]._serialized_start = 6133
    _globals["_EXPRESSION_UNRESOLVEDREGEX"]._serialized_end = 6219
    _globals["_EXPRESSION_UNRESOLVEDEXTRACTVALUE"]._serialized_start = 6222
    _globals["_EXPRESSION_UNRESOLVEDEXTRACTVALUE"]._serialized_end = 6354
    _globals["_EXPRESSION_UPDATEFIELDS"]._serialized_start = 6357
    _globals["_EXPRESSION_UPDATEFIELDS"]._serialized_end = 6544
    _globals["_EXPRESSION_ALIAS"]._serialized_start = 6546
    _globals["_EXPRESSION_ALIAS"]._serialized_end = 6666
    _globals["_EXPRESSION_LAMBDAFUNCTION"]._serialized_start = 6669
    _globals["_EXPRESSION_LAMBDAFUNCTION"]._serialized_end = 6827
    _globals["_EXPRESSION_UNRESOLVEDNAMEDLAMBDAVARIABLE"]._serialized_start = 6829
    _globals["_EXPRESSION_UNRESOLVEDNAMEDLAMBDAVARIABLE"]._serialized_end = 6891
    _globals["_EXPRESSIONCOMMON"]._serialized_start = 6906
    _globals["_EXPRESSIONCOMMON"]._serialized_end = 6971
    _globals["_COMMONINLINEUSERDEFINEDFUNCTION"]._serialized_start = 6974
    _globals["_COMMONINLINEUSERDEFINEDFUNCTION"]._serialized_end = 7371
    _globals["_PYTHONUDF"]._serialized_start = 7374
    _globals["_PYTHONUDF"]._serialized_end = 7578
    _globals["_SCALARSCALAUDF"]._serialized_start = 7581
    _globals["_SCALARSCALAUDF"]._serialized_end = 7795
    _globals["_JAVAUDF"]._serialized_start = 7798
    _globals["_JAVAUDF"]._serialized_end = 7947
    _globals["_TYPEDAGGREGATEEXPRESSION"]._serialized_start = 7949
    _globals["_TYPEDAGGREGATEEXPRESSION"]._serialized_end = 8048
    _globals["_CALLFUNCTION"]._serialized_start = 8050
    _globals["_CALLFUNCTION"]._serialized_end = 8158
    _globals["_NAMEDARGUMENTEXPRESSION"]._serialized_start = 8160
    _globals["_NAMEDARGUMENTEXPRESSION"]._serialized_end = 8252
    _globals["_MERGEACTION"]._serialized_start = 8255
    _globals["_MERGEACTION"]._serialized_end = 8767
    _globals["_MERGEACTION_ASSIGNMENT"]._serialized_start = 8477
    _globals["_MERGEACTION_ASSIGNMENT"]._serialized_end = 8583
    _globals["_MERGEACTION_ACTIONTYPE"]._serialized_start = 8586
    _globals["_MERGEACTION_ACTIONTYPE"]._serialized_end = 8753
    _globals["_SUBQUERYEXPRESSION"]._serialized_start = 8770
    _globals["_SUBQUERYEXPRESSION"]._serialized_end = 9383
    _globals["_SUBQUERYEXPRESSION_TABLEARGOPTIONS"]._serialized_start = 9003
    _globals["_SUBQUERYEXPRESSION_TABLEARGOPTIONS"]._serialized_end = 9237
    _globals["_SUBQUERYEXPRESSION_SUBQUERYTYPE"]._serialized_start = 9239
    _globals["_SUBQUERYEXPRESSION_SUBQUERYTYPE"]._serialized_end = 9361
# @@protoc_insertion_point(module_scope)
