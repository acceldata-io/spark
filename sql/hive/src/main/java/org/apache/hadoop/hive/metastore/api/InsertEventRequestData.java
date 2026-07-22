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

package org.apache.hadoop.hive.metastore.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.thrift.TBase;
import org.apache.thrift.TBaseHelper;
import org.apache.thrift.TException;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.meta_data.FieldMetaData;
import org.apache.thrift.meta_data.FieldValueMetaData;
import org.apache.thrift.meta_data.ListMetaData;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TField;
import org.apache.thrift.protocol.TList;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.protocol.TProtocolUtil;
import org.apache.thrift.protocol.TStruct;
import org.apache.thrift.protocol.TType;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.transport.TIOStreamTransport;

@SuppressWarnings({"rawtypes", "unchecked", "serial"})
public class InsertEventRequestData
    implements TBase<InsertEventRequestData, InsertEventRequestData._Fields>,
    Serializable, Cloneable {

  private static final TStruct STRUCT_DESC = new TStruct("InsertEventRequestData");

  private static final TField COMPAT_REPLACE_FIELD_DESC =
      new TField("replace", TType.BOOL, (short) 1);
  private static final TField COMPAT_FILES_ADDED_FIELD_DESC =
      new TField("filesAdded", TType.LIST, (short) 2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes =
      new HashMap<Class<? extends IScheme>, SchemeFactory>();

  private List<String> filesAdded;

  public static final Map<_Fields, FieldMetaData> metaDataMap;

  public InsertEventRequestData() {
  }

  public InsertEventRequestData(List<String> filesAdded) {
    this();
    this.filesAdded = filesAdded;
  }

  public InsertEventRequestData(InsertEventRequestData other) {
    if (other.isSetFilesAdded()) {
      ArrayList<String> copy = new ArrayList<String>();
      for (String otherElement : other.filesAdded) {
        copy.add(otherElement);
      }
      this.filesAdded = copy;
    }
  }

  public InsertEventRequestData deepCopy() {
    return new InsertEventRequestData(this);
  }

  public void clear() {
    this.filesAdded = null;
  }

  public int getFilesAddedSize() {
    return this.filesAdded == null ? 0 : this.filesAdded.size();
  }

  public Iterator<String> getFilesAddedIterator() {
    return this.filesAdded == null ? null : this.filesAdded.iterator();
  }

  public void addToFilesAdded(String elem) {
    if (this.filesAdded == null) {
      this.filesAdded = new ArrayList<String>();
    }
    this.filesAdded.add(elem);
  }

  public List<String> getFilesAdded() {
    return this.filesAdded;
  }

  public void setFilesAdded(List<String> filesAdded) {
    this.filesAdded = filesAdded;
  }

  public void unsetFilesAdded() {
    this.filesAdded = null;
  }

  public boolean isSetFilesAdded() {
    return this.filesAdded != null;
  }

  public void setFilesAddedIsSet(boolean value) {
    if (!value) {
      this.filesAdded = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
      case FILES_ADDED:
        if (value == null) {
          unsetFilesAdded();
        } else {
          setFilesAdded((List) value);
        }
        break;
      default:
        throw new IllegalArgumentException("Unknown field: " + field);
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
      case FILES_ADDED:
        return getFilesAdded();
      default:
        throw new IllegalStateException();
    }
  }

  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }
    switch (field) {
      case FILES_ADDED:
        return isSetFilesAdded();
      default:
        throw new IllegalStateException();
    }
  }

  @Override
  public boolean equals(Object that) {
    if (that == null) {
      return false;
    }
    if (that instanceof InsertEventRequestData) {
      return this.equals((InsertEventRequestData) that);
    }
    return false;
  }

  public boolean equals(InsertEventRequestData that) {
    if (that == null) {
      return false;
    }
    boolean thisPresent = this.isSetFilesAdded();
    boolean thatPresent = that.isSetFilesAdded();
    if (thisPresent || thatPresent) {
      if (!(thisPresent && thatPresent)) {
        return false;
      }
      if (!this.filesAdded.equals(that.filesAdded)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    boolean present = isSetFilesAdded();
    builder.append(present);
    if (present) {
      builder.append(this.filesAdded);
    }
    return builder.toHashCode();
  }

  public int compareTo(InsertEventRequestData other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }
    int lastComparison = Boolean.valueOf(isSetFilesAdded()).compareTo(other.isSetFilesAdded());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFilesAdded()) {
      lastComparison = TBaseHelper.compareTo(this.filesAdded, other.filesAdded);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(TProtocol iprot) throws TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(TProtocol oprot) throws TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("InsertEventRequestData(");
    sb.append("filesAdded:");
    if (this.filesAdded == null) {
      sb.append("null");
    } else {
      sb.append(this.filesAdded);
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    if (!isSetFilesAdded()) {
      throw new TProtocolException(
          "Required field 'filesAdded' is unset! Struct:" + toString());
    }
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    try {
      write(new TCompactProtocol(new TIOStreamTransport((OutputStream) out)));
    } catch (TException te) {
      throw new IOException(te);
    }
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    try {
      read(new TCompactProtocol(new TIOStreamTransport((InputStream) in)));
    } catch (TException te) {
      throw new IOException(te);
    }
  }

  static {
    schemes.put(StandardScheme.class, new InsertEventRequestDataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new InsertEventRequestDataTupleSchemeFactory());
    EnumMap<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FILES_ADDED, new FieldMetaData("filesAdded", (byte) 1,
        new ListMetaData(TType.LIST, new FieldValueMetaData(TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(InsertEventRequestData.class, metaDataMap);
  }

  private static class InsertEventRequestDataTupleScheme
      extends TupleScheme<InsertEventRequestData> {
    public void write(TProtocol prot, InsertEventRequestData struct) throws TException {
      new InsertEventRequestDataStandardScheme().write(prot, struct);
    }

    public void read(TProtocol prot, InsertEventRequestData struct) throws TException {
      new InsertEventRequestDataStandardScheme().read(prot, struct);
    }
  }

  private static class InsertEventRequestDataTupleSchemeFactory implements SchemeFactory {
    public InsertEventRequestDataTupleScheme getScheme() {
      return new InsertEventRequestDataTupleScheme();
    }
  }

  private static class InsertEventRequestDataStandardScheme
      extends StandardScheme<InsertEventRequestData> {

    public void read(TProtocol iprot, InsertEventRequestData struct) throws TException {
      iprot.readStructBegin();
      while (true) {
        TField schemeField = iprot.readFieldBegin();
        if (schemeField.type == TType.STOP) {
          break;
        }
        switch (schemeField.id) {
          case 1:
            if (schemeField.type == TType.LIST) {
              TList list = iprot.readListBegin();
              struct.filesAdded = new ArrayList<String>(list.size);
              for (int i = 0; i < list.size; i++) {
                struct.filesAdded.add(iprot.readString());
              }
              iprot.readListEnd();
              struct.setFilesAddedIsSet(true);
            } else if (schemeField.type == TType.BOOL) {
              iprot.readBool();
            } else {
              TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2:
            if (schemeField.type == TType.LIST) {
              TList list = iprot.readListBegin();
              struct.filesAdded = new ArrayList<String>(list.size);
              for (int i = 0; i < list.size; i++) {
                struct.filesAdded.add(iprot.readString());
              }
              iprot.readListEnd();
              struct.setFilesAddedIsSet(true);
            } else {
              TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            TProtocolUtil.skip(iprot, schemeField.type);
            break;
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();
      struct.validate();
    }

    public void write(TProtocol oprot, InsertEventRequestData struct) throws TException {
      struct.validate();
      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(COMPAT_REPLACE_FIELD_DESC);
      oprot.writeBool(false);
      oprot.writeFieldEnd();

      if (struct.filesAdded != null) {
        oprot.writeFieldBegin(COMPAT_FILES_ADDED_FIELD_DESC);
        oprot.writeListBegin(new TList(TType.STRING, struct.filesAdded.size()));
        for (String file : struct.filesAdded) {
          oprot.writeString(file);
        }
        oprot.writeListEnd();
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }
  }

  private static class InsertEventRequestDataStandardSchemeFactory implements SchemeFactory {
    public InsertEventRequestDataStandardScheme getScheme() {
      return new InsertEventRequestDataStandardScheme();
    }
  }

  public enum _Fields implements TFieldIdEnum {
    FILES_ADDED((short) 1, "filesAdded");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    private final short thriftId;
    private final String fieldName;

    _Fields(short thriftId, String fieldName) {
      this.thriftId = thriftId;
      this.fieldName = fieldName;
    }

    public static _Fields findByThriftId(int fieldId) {
      switch (fieldId) {
        case 1:
          return FILES_ADDED;
        default:
          return null;
      }
    }

    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) {
        throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      }
      return fields;
    }

    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    public short getThriftFieldId() {
      return thriftId;
    }

    public String getFieldName() {
      return fieldName;
    }
  }
}
