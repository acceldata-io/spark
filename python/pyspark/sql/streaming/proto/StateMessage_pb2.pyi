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
"""
@generated by mypy-protobuf.  Do not edit manually!
isort:skip_file

Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
"""
import builtins
import google.protobuf.descriptor
import google.protobuf.internal.enum_type_wrapper
import google.protobuf.message
import sys
import typing

if sys.version_info >= (3, 10):
    import typing as typing_extensions
else:
    import typing_extensions

DESCRIPTOR: google.protobuf.descriptor.FileDescriptor

class _HandleState:
    ValueType = typing.NewType("ValueType", builtins.int)
    V: typing_extensions.TypeAlias = ValueType

class _HandleStateEnumTypeWrapper(
    google.protobuf.internal.enum_type_wrapper._EnumTypeWrapper[_HandleState.ValueType],
    builtins.type,
):  # noqa: F821
    DESCRIPTOR: google.protobuf.descriptor.EnumDescriptor
    CREATED: _HandleState.ValueType  # 0
    INITIALIZED: _HandleState.ValueType  # 1
    DATA_PROCESSED: _HandleState.ValueType  # 2
    TIMER_PROCESSED: _HandleState.ValueType  # 3
    CLOSED: _HandleState.ValueType  # 4

class HandleState(_HandleState, metaclass=_HandleStateEnumTypeWrapper): ...

CREATED: HandleState.ValueType  # 0
INITIALIZED: HandleState.ValueType  # 1
DATA_PROCESSED: HandleState.ValueType  # 2
TIMER_PROCESSED: HandleState.ValueType  # 3
CLOSED: HandleState.ValueType  # 4
global___HandleState = HandleState

class StateRequest(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    VERSION_FIELD_NUMBER: builtins.int
    STATEFULPROCESSORCALL_FIELD_NUMBER: builtins.int
    STATEVARIABLEREQUEST_FIELD_NUMBER: builtins.int
    IMPLICITGROUPINGKEYREQUEST_FIELD_NUMBER: builtins.int
    TIMERREQUEST_FIELD_NUMBER: builtins.int
    version: builtins.int
    @property
    def statefulProcessorCall(self) -> global___StatefulProcessorCall: ...
    @property
    def stateVariableRequest(self) -> global___StateVariableRequest: ...
    @property
    def implicitGroupingKeyRequest(self) -> global___ImplicitGroupingKeyRequest: ...
    @property
    def timerRequest(self) -> global___TimerRequest: ...
    def __init__(
        self,
        *,
        version: builtins.int = ...,
        statefulProcessorCall: global___StatefulProcessorCall | None = ...,
        stateVariableRequest: global___StateVariableRequest | None = ...,
        implicitGroupingKeyRequest: global___ImplicitGroupingKeyRequest | None = ...,
        timerRequest: global___TimerRequest | None = ...,
    ) -> None: ...
    def HasField(
        self,
        field_name: typing_extensions.Literal[
            "implicitGroupingKeyRequest",
            b"implicitGroupingKeyRequest",
            "method",
            b"method",
            "stateVariableRequest",
            b"stateVariableRequest",
            "statefulProcessorCall",
            b"statefulProcessorCall",
            "timerRequest",
            b"timerRequest",
        ],
    ) -> builtins.bool: ...
    def ClearField(
        self,
        field_name: typing_extensions.Literal[
            "implicitGroupingKeyRequest",
            b"implicitGroupingKeyRequest",
            "method",
            b"method",
            "stateVariableRequest",
            b"stateVariableRequest",
            "statefulProcessorCall",
            b"statefulProcessorCall",
            "timerRequest",
            b"timerRequest",
            "version",
            b"version",
        ],
    ) -> None: ...
    def WhichOneof(
        self, oneof_group: typing_extensions.Literal["method", b"method"]
    ) -> (
        typing_extensions.Literal[
            "statefulProcessorCall",
            "stateVariableRequest",
            "implicitGroupingKeyRequest",
            "timerRequest",
        ]
        | None
    ): ...

global___StateRequest = StateRequest

class StateResponse(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    STATUSCODE_FIELD_NUMBER: builtins.int
    ERRORMESSAGE_FIELD_NUMBER: builtins.int
    VALUE_FIELD_NUMBER: builtins.int
    statusCode: builtins.int
    errorMessage: builtins.str
    value: builtins.bytes
    def __init__(
        self,
        *,
        statusCode: builtins.int = ...,
        errorMessage: builtins.str = ...,
        value: builtins.bytes = ...,
    ) -> None: ...
    def ClearField(
        self,
        field_name: typing_extensions.Literal[
            "errorMessage", b"errorMessage", "statusCode", b"statusCode", "value", b"value"
        ],
    ) -> None: ...

global___StateResponse = StateResponse

class StateResponseWithLongTypeVal(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    STATUSCODE_FIELD_NUMBER: builtins.int
    ERRORMESSAGE_FIELD_NUMBER: builtins.int
    VALUE_FIELD_NUMBER: builtins.int
    statusCode: builtins.int
    errorMessage: builtins.str
    value: builtins.int
    def __init__(
        self,
        *,
        statusCode: builtins.int = ...,
        errorMessage: builtins.str = ...,
        value: builtins.int = ...,
    ) -> None: ...
    def ClearField(
        self,
        field_name: typing_extensions.Literal[
            "errorMessage", b"errorMessage", "statusCode", b"statusCode", "value", b"value"
        ],
    ) -> None: ...

global___StateResponseWithLongTypeVal = StateResponseWithLongTypeVal

class StatefulProcessorCall(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    SETHANDLESTATE_FIELD_NUMBER: builtins.int
    GETVALUESTATE_FIELD_NUMBER: builtins.int
    GETLISTSTATE_FIELD_NUMBER: builtins.int
    GETMAPSTATE_FIELD_NUMBER: builtins.int
    TIMERSTATECALL_FIELD_NUMBER: builtins.int
    DELETEIFEXISTS_FIELD_NUMBER: builtins.int
    @property
    def setHandleState(self) -> global___SetHandleState: ...
    @property
    def getValueState(self) -> global___StateCallCommand: ...
    @property
    def getListState(self) -> global___StateCallCommand: ...
    @property
    def getMapState(self) -> global___StateCallCommand: ...
    @property
    def timerStateCall(self) -> global___TimerStateCallCommand: ...
    @property
    def deleteIfExists(self) -> global___StateCallCommand: ...
    def __init__(
        self,
        *,
        setHandleState: global___SetHandleState | None = ...,
        getValueState: global___StateCallCommand | None = ...,
        getListState: global___StateCallCommand | None = ...,
        getMapState: global___StateCallCommand | None = ...,
        timerStateCall: global___TimerStateCallCommand | None = ...,
        deleteIfExists: global___StateCallCommand | None = ...,
    ) -> None: ...
    def HasField(
        self,
        field_name: typing_extensions.Literal[
            "deleteIfExists",
            b"deleteIfExists",
            "getListState",
            b"getListState",
            "getMapState",
            b"getMapState",
            "getValueState",
            b"getValueState",
            "method",
            b"method",
            "setHandleState",
            b"setHandleState",
            "timerStateCall",
            b"timerStateCall",
        ],
    ) -> builtins.bool: ...
    def ClearField(
        self,
        field_name: typing_extensions.Literal[
            "deleteIfExists",
            b"deleteIfExists",
            "getListState",
            b"getListState",
            "getMapState",
            b"getMapState",
            "getValueState",
            b"getValueState",
            "method",
            b"method",
            "setHandleState",
            b"setHandleState",
            "timerStateCall",
            b"timerStateCall",
        ],
    ) -> None: ...
    def WhichOneof(
        self, oneof_group: typing_extensions.Literal["method", b"method"]
    ) -> (
        typing_extensions.Literal[
            "setHandleState",
            "getValueState",
            "getListState",
            "getMapState",
            "timerStateCall",
            "deleteIfExists",
        ]
        | None
    ): ...

global___StatefulProcessorCall = StatefulProcessorCall

class StateVariableRequest(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    VALUESTATECALL_FIELD_NUMBER: builtins.int
    LISTSTATECALL_FIELD_NUMBER: builtins.int
    MAPSTATECALL_FIELD_NUMBER: builtins.int
    @property
    def valueStateCall(self) -> global___ValueStateCall: ...
    @property
    def listStateCall(self) -> global___ListStateCall: ...
    @property
    def mapStateCall(self) -> global___MapStateCall: ...
    def __init__(
        self,
        *,
        valueStateCall: global___ValueStateCall | None = ...,
        listStateCall: global___ListStateCall | None = ...,
        mapStateCall: global___MapStateCall | None = ...,
    ) -> None: ...
    def HasField(
        self,
        field_name: typing_extensions.Literal[
            "listStateCall",
            b"listStateCall",
            "mapStateCall",
            b"mapStateCall",
            "method",
            b"method",
            "valueStateCall",
            b"valueStateCall",
        ],
    ) -> builtins.bool: ...
    def ClearField(
        self,
        field_name: typing_extensions.Literal[
            "listStateCall",
            b"listStateCall",
            "mapStateCall",
            b"mapStateCall",
            "method",
            b"method",
            "valueStateCall",
            b"valueStateCall",
        ],
    ) -> None: ...
    def WhichOneof(
        self, oneof_group: typing_extensions.Literal["method", b"method"]
    ) -> typing_extensions.Literal["valueStateCall", "listStateCall", "mapStateCall"] | None: ...

global___StateVariableRequest = StateVariableRequest

class ImplicitGroupingKeyRequest(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    SETIMPLICITKEY_FIELD_NUMBER: builtins.int
    REMOVEIMPLICITKEY_FIELD_NUMBER: builtins.int
    @property
    def setImplicitKey(self) -> global___SetImplicitKey: ...
    @property
    def removeImplicitKey(self) -> global___RemoveImplicitKey: ...
    def __init__(
        self,
        *,
        setImplicitKey: global___SetImplicitKey | None = ...,
        removeImplicitKey: global___RemoveImplicitKey | None = ...,
    ) -> None: ...
    def HasField(
        self,
        field_name: typing_extensions.Literal[
            "method",
            b"method",
            "removeImplicitKey",
            b"removeImplicitKey",
            "setImplicitKey",
            b"setImplicitKey",
        ],
    ) -> builtins.bool: ...
    def ClearField(
        self,
        field_name: typing_extensions.Literal[
            "method",
            b"method",
            "removeImplicitKey",
            b"removeImplicitKey",
            "setImplicitKey",
            b"setImplicitKey",
        ],
    ) -> None: ...
    def WhichOneof(
        self, oneof_group: typing_extensions.Literal["method", b"method"]
    ) -> typing_extensions.Literal["setImplicitKey", "removeImplicitKey"] | None: ...

global___ImplicitGroupingKeyRequest = ImplicitGroupingKeyRequest

class TimerRequest(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    TIMERVALUEREQUEST_FIELD_NUMBER: builtins.int
    EXPIRYTIMERREQUEST_FIELD_NUMBER: builtins.int
    @property
    def timerValueRequest(self) -> global___TimerValueRequest: ...
    @property
    def expiryTimerRequest(self) -> global___ExpiryTimerRequest: ...
    def __init__(
        self,
        *,
        timerValueRequest: global___TimerValueRequest | None = ...,
        expiryTimerRequest: global___ExpiryTimerRequest | None = ...,
    ) -> None: ...
    def HasField(
        self,
        field_name: typing_extensions.Literal[
            "expiryTimerRequest",
            b"expiryTimerRequest",
            "method",
            b"method",
            "timerValueRequest",
            b"timerValueRequest",
        ],
    ) -> builtins.bool: ...
    def ClearField(
        self,
        field_name: typing_extensions.Literal[
            "expiryTimerRequest",
            b"expiryTimerRequest",
            "method",
            b"method",
            "timerValueRequest",
            b"timerValueRequest",
        ],
    ) -> None: ...
    def WhichOneof(
        self, oneof_group: typing_extensions.Literal["method", b"method"]
    ) -> typing_extensions.Literal["timerValueRequest", "expiryTimerRequest"] | None: ...

global___TimerRequest = TimerRequest

class TimerValueRequest(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    GETPROCESSINGTIMER_FIELD_NUMBER: builtins.int
    GETWATERMARK_FIELD_NUMBER: builtins.int
    @property
    def getProcessingTimer(self) -> global___GetProcessingTime: ...
    @property
    def getWatermark(self) -> global___GetWatermark: ...
    def __init__(
        self,
        *,
        getProcessingTimer: global___GetProcessingTime | None = ...,
        getWatermark: global___GetWatermark | None = ...,
    ) -> None: ...
    def HasField(
        self,
        field_name: typing_extensions.Literal[
            "getProcessingTimer",
            b"getProcessingTimer",
            "getWatermark",
            b"getWatermark",
            "method",
            b"method",
        ],
    ) -> builtins.bool: ...
    def ClearField(
        self,
        field_name: typing_extensions.Literal[
            "getProcessingTimer",
            b"getProcessingTimer",
            "getWatermark",
            b"getWatermark",
            "method",
            b"method",
        ],
    ) -> None: ...
    def WhichOneof(
        self, oneof_group: typing_extensions.Literal["method", b"method"]
    ) -> typing_extensions.Literal["getProcessingTimer", "getWatermark"] | None: ...

global___TimerValueRequest = TimerValueRequest

class ExpiryTimerRequest(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    EXPIRYTIMESTAMPMS_FIELD_NUMBER: builtins.int
    expiryTimestampMs: builtins.int
    def __init__(
        self,
        *,
        expiryTimestampMs: builtins.int = ...,
    ) -> None: ...
    def ClearField(
        self, field_name: typing_extensions.Literal["expiryTimestampMs", b"expiryTimestampMs"]
    ) -> None: ...

global___ExpiryTimerRequest = ExpiryTimerRequest

class GetProcessingTime(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    def __init__(
        self,
    ) -> None: ...

global___GetProcessingTime = GetProcessingTime

class GetWatermark(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    def __init__(
        self,
    ) -> None: ...

global___GetWatermark = GetWatermark

class StateCallCommand(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    STATENAME_FIELD_NUMBER: builtins.int
    SCHEMA_FIELD_NUMBER: builtins.int
    MAPSTATEVALUESCHEMA_FIELD_NUMBER: builtins.int
    TTL_FIELD_NUMBER: builtins.int
    stateName: builtins.str
    schema: builtins.str
    mapStateValueSchema: builtins.str
    @property
    def ttl(self) -> global___TTLConfig: ...
    def __init__(
        self,
        *,
        stateName: builtins.str = ...,
        schema: builtins.str = ...,
        mapStateValueSchema: builtins.str = ...,
        ttl: global___TTLConfig | None = ...,
    ) -> None: ...
    def HasField(self, field_name: typing_extensions.Literal["ttl", b"ttl"]) -> builtins.bool: ...
    def ClearField(
        self,
        field_name: typing_extensions.Literal[
            "mapStateValueSchema",
            b"mapStateValueSchema",
            "schema",
            b"schema",
            "stateName",
            b"stateName",
            "ttl",
            b"ttl",
        ],
    ) -> None: ...

global___StateCallCommand = StateCallCommand

class TimerStateCallCommand(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    REGISTER_FIELD_NUMBER: builtins.int
    DELETE_FIELD_NUMBER: builtins.int
    LIST_FIELD_NUMBER: builtins.int
    @property
    def register(self) -> global___RegisterTimer: ...
    @property
    def delete(self) -> global___DeleteTimer: ...
    @property
    def list(self) -> global___ListTimers: ...
    def __init__(
        self,
        *,
        register: global___RegisterTimer | None = ...,
        delete: global___DeleteTimer | None = ...,
        list: global___ListTimers | None = ...,
    ) -> None: ...
    def HasField(
        self,
        field_name: typing_extensions.Literal[
            "delete", b"delete", "list", b"list", "method", b"method", "register", b"register"
        ],
    ) -> builtins.bool: ...
    def ClearField(
        self,
        field_name: typing_extensions.Literal[
            "delete", b"delete", "list", b"list", "method", b"method", "register", b"register"
        ],
    ) -> None: ...
    def WhichOneof(
        self, oneof_group: typing_extensions.Literal["method", b"method"]
    ) -> typing_extensions.Literal["register", "delete", "list"] | None: ...

global___TimerStateCallCommand = TimerStateCallCommand

class ValueStateCall(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    STATENAME_FIELD_NUMBER: builtins.int
    EXISTS_FIELD_NUMBER: builtins.int
    GET_FIELD_NUMBER: builtins.int
    VALUESTATEUPDATE_FIELD_NUMBER: builtins.int
    CLEAR_FIELD_NUMBER: builtins.int
    stateName: builtins.str
    @property
    def exists(self) -> global___Exists: ...
    @property
    def get(self) -> global___Get: ...
    @property
    def valueStateUpdate(self) -> global___ValueStateUpdate: ...
    @property
    def clear(self) -> global___Clear: ...
    def __init__(
        self,
        *,
        stateName: builtins.str = ...,
        exists: global___Exists | None = ...,
        get: global___Get | None = ...,
        valueStateUpdate: global___ValueStateUpdate | None = ...,
        clear: global___Clear | None = ...,
    ) -> None: ...
    def HasField(
        self,
        field_name: typing_extensions.Literal[
            "clear",
            b"clear",
            "exists",
            b"exists",
            "get",
            b"get",
            "method",
            b"method",
            "valueStateUpdate",
            b"valueStateUpdate",
        ],
    ) -> builtins.bool: ...
    def ClearField(
        self,
        field_name: typing_extensions.Literal[
            "clear",
            b"clear",
            "exists",
            b"exists",
            "get",
            b"get",
            "method",
            b"method",
            "stateName",
            b"stateName",
            "valueStateUpdate",
            b"valueStateUpdate",
        ],
    ) -> None: ...
    def WhichOneof(
        self, oneof_group: typing_extensions.Literal["method", b"method"]
    ) -> typing_extensions.Literal["exists", "get", "valueStateUpdate", "clear"] | None: ...

global___ValueStateCall = ValueStateCall

class ListStateCall(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    STATENAME_FIELD_NUMBER: builtins.int
    EXISTS_FIELD_NUMBER: builtins.int
    LISTSTATEGET_FIELD_NUMBER: builtins.int
    LISTSTATEPUT_FIELD_NUMBER: builtins.int
    APPENDVALUE_FIELD_NUMBER: builtins.int
    APPENDLIST_FIELD_NUMBER: builtins.int
    CLEAR_FIELD_NUMBER: builtins.int
    stateName: builtins.str
    @property
    def exists(self) -> global___Exists: ...
    @property
    def listStateGet(self) -> global___ListStateGet: ...
    @property
    def listStatePut(self) -> global___ListStatePut: ...
    @property
    def appendValue(self) -> global___AppendValue: ...
    @property
    def appendList(self) -> global___AppendList: ...
    @property
    def clear(self) -> global___Clear: ...
    def __init__(
        self,
        *,
        stateName: builtins.str = ...,
        exists: global___Exists | None = ...,
        listStateGet: global___ListStateGet | None = ...,
        listStatePut: global___ListStatePut | None = ...,
        appendValue: global___AppendValue | None = ...,
        appendList: global___AppendList | None = ...,
        clear: global___Clear | None = ...,
    ) -> None: ...
    def HasField(
        self,
        field_name: typing_extensions.Literal[
            "appendList",
            b"appendList",
            "appendValue",
            b"appendValue",
            "clear",
            b"clear",
            "exists",
            b"exists",
            "listStateGet",
            b"listStateGet",
            "listStatePut",
            b"listStatePut",
            "method",
            b"method",
        ],
    ) -> builtins.bool: ...
    def ClearField(
        self,
        field_name: typing_extensions.Literal[
            "appendList",
            b"appendList",
            "appendValue",
            b"appendValue",
            "clear",
            b"clear",
            "exists",
            b"exists",
            "listStateGet",
            b"listStateGet",
            "listStatePut",
            b"listStatePut",
            "method",
            b"method",
            "stateName",
            b"stateName",
        ],
    ) -> None: ...
    def WhichOneof(
        self, oneof_group: typing_extensions.Literal["method", b"method"]
    ) -> (
        typing_extensions.Literal[
            "exists", "listStateGet", "listStatePut", "appendValue", "appendList", "clear"
        ]
        | None
    ): ...

global___ListStateCall = ListStateCall

class MapStateCall(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    STATENAME_FIELD_NUMBER: builtins.int
    EXISTS_FIELD_NUMBER: builtins.int
    GETVALUE_FIELD_NUMBER: builtins.int
    CONTAINSKEY_FIELD_NUMBER: builtins.int
    UPDATEVALUE_FIELD_NUMBER: builtins.int
    ITERATOR_FIELD_NUMBER: builtins.int
    KEYS_FIELD_NUMBER: builtins.int
    VALUES_FIELD_NUMBER: builtins.int
    REMOVEKEY_FIELD_NUMBER: builtins.int
    CLEAR_FIELD_NUMBER: builtins.int
    stateName: builtins.str
    @property
    def exists(self) -> global___Exists: ...
    @property
    def getValue(self) -> global___GetValue: ...
    @property
    def containsKey(self) -> global___ContainsKey: ...
    @property
    def updateValue(self) -> global___UpdateValue: ...
    @property
    def iterator(self) -> global___Iterator: ...
    @property
    def keys(self) -> global___Keys: ...
    @property
    def values(self) -> global___Values: ...
    @property
    def removeKey(self) -> global___RemoveKey: ...
    @property
    def clear(self) -> global___Clear: ...
    def __init__(
        self,
        *,
        stateName: builtins.str = ...,
        exists: global___Exists | None = ...,
        getValue: global___GetValue | None = ...,
        containsKey: global___ContainsKey | None = ...,
        updateValue: global___UpdateValue | None = ...,
        iterator: global___Iterator | None = ...,
        keys: global___Keys | None = ...,
        values: global___Values | None = ...,
        removeKey: global___RemoveKey | None = ...,
        clear: global___Clear | None = ...,
    ) -> None: ...
    def HasField(
        self,
        field_name: typing_extensions.Literal[
            "clear",
            b"clear",
            "containsKey",
            b"containsKey",
            "exists",
            b"exists",
            "getValue",
            b"getValue",
            "iterator",
            b"iterator",
            "keys",
            b"keys",
            "method",
            b"method",
            "removeKey",
            b"removeKey",
            "updateValue",
            b"updateValue",
            "values",
            b"values",
        ],
    ) -> builtins.bool: ...
    def ClearField(
        self,
        field_name: typing_extensions.Literal[
            "clear",
            b"clear",
            "containsKey",
            b"containsKey",
            "exists",
            b"exists",
            "getValue",
            b"getValue",
            "iterator",
            b"iterator",
            "keys",
            b"keys",
            "method",
            b"method",
            "removeKey",
            b"removeKey",
            "stateName",
            b"stateName",
            "updateValue",
            b"updateValue",
            "values",
            b"values",
        ],
    ) -> None: ...
    def WhichOneof(
        self, oneof_group: typing_extensions.Literal["method", b"method"]
    ) -> (
        typing_extensions.Literal[
            "exists",
            "getValue",
            "containsKey",
            "updateValue",
            "iterator",
            "keys",
            "values",
            "removeKey",
            "clear",
        ]
        | None
    ): ...

global___MapStateCall = MapStateCall

class SetImplicitKey(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    KEY_FIELD_NUMBER: builtins.int
    key: builtins.bytes
    def __init__(
        self,
        *,
        key: builtins.bytes = ...,
    ) -> None: ...
    def ClearField(self, field_name: typing_extensions.Literal["key", b"key"]) -> None: ...

global___SetImplicitKey = SetImplicitKey

class RemoveImplicitKey(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    def __init__(
        self,
    ) -> None: ...

global___RemoveImplicitKey = RemoveImplicitKey

class Exists(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    def __init__(
        self,
    ) -> None: ...

global___Exists = Exists

class Get(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    def __init__(
        self,
    ) -> None: ...

global___Get = Get

class RegisterTimer(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    EXPIRYTIMESTAMPMS_FIELD_NUMBER: builtins.int
    expiryTimestampMs: builtins.int
    def __init__(
        self,
        *,
        expiryTimestampMs: builtins.int = ...,
    ) -> None: ...
    def ClearField(
        self, field_name: typing_extensions.Literal["expiryTimestampMs", b"expiryTimestampMs"]
    ) -> None: ...

global___RegisterTimer = RegisterTimer

class DeleteTimer(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    EXPIRYTIMESTAMPMS_FIELD_NUMBER: builtins.int
    expiryTimestampMs: builtins.int
    def __init__(
        self,
        *,
        expiryTimestampMs: builtins.int = ...,
    ) -> None: ...
    def ClearField(
        self, field_name: typing_extensions.Literal["expiryTimestampMs", b"expiryTimestampMs"]
    ) -> None: ...

global___DeleteTimer = DeleteTimer

class ListTimers(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    ITERATORID_FIELD_NUMBER: builtins.int
    iteratorId: builtins.str
    def __init__(
        self,
        *,
        iteratorId: builtins.str = ...,
    ) -> None: ...
    def ClearField(
        self, field_name: typing_extensions.Literal["iteratorId", b"iteratorId"]
    ) -> None: ...

global___ListTimers = ListTimers

class ValueStateUpdate(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    VALUE_FIELD_NUMBER: builtins.int
    value: builtins.bytes
    def __init__(
        self,
        *,
        value: builtins.bytes = ...,
    ) -> None: ...
    def ClearField(self, field_name: typing_extensions.Literal["value", b"value"]) -> None: ...

global___ValueStateUpdate = ValueStateUpdate

class Clear(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    def __init__(
        self,
    ) -> None: ...

global___Clear = Clear

class ListStateGet(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    ITERATORID_FIELD_NUMBER: builtins.int
    iteratorId: builtins.str
    def __init__(
        self,
        *,
        iteratorId: builtins.str = ...,
    ) -> None: ...
    def ClearField(
        self, field_name: typing_extensions.Literal["iteratorId", b"iteratorId"]
    ) -> None: ...

global___ListStateGet = ListStateGet

class ListStatePut(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    def __init__(
        self,
    ) -> None: ...

global___ListStatePut = ListStatePut

class AppendValue(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    VALUE_FIELD_NUMBER: builtins.int
    value: builtins.bytes
    def __init__(
        self,
        *,
        value: builtins.bytes = ...,
    ) -> None: ...
    def ClearField(self, field_name: typing_extensions.Literal["value", b"value"]) -> None: ...

global___AppendValue = AppendValue

class AppendList(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    def __init__(
        self,
    ) -> None: ...

global___AppendList = AppendList

class GetValue(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    USERKEY_FIELD_NUMBER: builtins.int
    userKey: builtins.bytes
    def __init__(
        self,
        *,
        userKey: builtins.bytes = ...,
    ) -> None: ...
    def ClearField(self, field_name: typing_extensions.Literal["userKey", b"userKey"]) -> None: ...

global___GetValue = GetValue

class ContainsKey(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    USERKEY_FIELD_NUMBER: builtins.int
    userKey: builtins.bytes
    def __init__(
        self,
        *,
        userKey: builtins.bytes = ...,
    ) -> None: ...
    def ClearField(self, field_name: typing_extensions.Literal["userKey", b"userKey"]) -> None: ...

global___ContainsKey = ContainsKey

class UpdateValue(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    USERKEY_FIELD_NUMBER: builtins.int
    VALUE_FIELD_NUMBER: builtins.int
    userKey: builtins.bytes
    value: builtins.bytes
    def __init__(
        self,
        *,
        userKey: builtins.bytes = ...,
        value: builtins.bytes = ...,
    ) -> None: ...
    def ClearField(
        self, field_name: typing_extensions.Literal["userKey", b"userKey", "value", b"value"]
    ) -> None: ...

global___UpdateValue = UpdateValue

class Iterator(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    ITERATORID_FIELD_NUMBER: builtins.int
    iteratorId: builtins.str
    def __init__(
        self,
        *,
        iteratorId: builtins.str = ...,
    ) -> None: ...
    def ClearField(
        self, field_name: typing_extensions.Literal["iteratorId", b"iteratorId"]
    ) -> None: ...

global___Iterator = Iterator

class Keys(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    ITERATORID_FIELD_NUMBER: builtins.int
    iteratorId: builtins.str
    def __init__(
        self,
        *,
        iteratorId: builtins.str = ...,
    ) -> None: ...
    def ClearField(
        self, field_name: typing_extensions.Literal["iteratorId", b"iteratorId"]
    ) -> None: ...

global___Keys = Keys

class Values(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    ITERATORID_FIELD_NUMBER: builtins.int
    iteratorId: builtins.str
    def __init__(
        self,
        *,
        iteratorId: builtins.str = ...,
    ) -> None: ...
    def ClearField(
        self, field_name: typing_extensions.Literal["iteratorId", b"iteratorId"]
    ) -> None: ...

global___Values = Values

class RemoveKey(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    USERKEY_FIELD_NUMBER: builtins.int
    userKey: builtins.bytes
    def __init__(
        self,
        *,
        userKey: builtins.bytes = ...,
    ) -> None: ...
    def ClearField(self, field_name: typing_extensions.Literal["userKey", b"userKey"]) -> None: ...

global___RemoveKey = RemoveKey

class SetHandleState(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    STATE_FIELD_NUMBER: builtins.int
    state: global___HandleState.ValueType
    def __init__(
        self,
        *,
        state: global___HandleState.ValueType = ...,
    ) -> None: ...
    def ClearField(self, field_name: typing_extensions.Literal["state", b"state"]) -> None: ...

global___SetHandleState = SetHandleState

class TTLConfig(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor

    DURATIONMS_FIELD_NUMBER: builtins.int
    durationMs: builtins.int
    def __init__(
        self,
        *,
        durationMs: builtins.int = ...,
    ) -> None: ...
    def ClearField(
        self, field_name: typing_extensions.Literal["durationMs", b"durationMs"]
    ) -> None: ...

global___TTLConfig = TTLConfig
