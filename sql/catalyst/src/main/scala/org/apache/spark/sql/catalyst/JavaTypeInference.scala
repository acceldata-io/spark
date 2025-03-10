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

package org.apache.spark.sql.catalyst

import java.beans.{Introspector, PropertyDescriptor}
import java.lang.{Iterable => JIterable}
import java.lang.reflect.Type
import java.util.{Iterator => JIterator, List => JList, Map => JMap}

import scala.language.existentials

import com.google.common.reflect.TypeToken

import org.apache.spark.sql.catalyst.analysis.{GetColumnByOrdinal, UnresolvedAttribute, UnresolvedExtractValue}
import org.apache.spark.sql.catalyst.expressions._
import org.apache.spark.sql.catalyst.expressions.objects._
import org.apache.spark.sql.catalyst.util.{ArrayBasedMapData, DateTimeUtils, GenericArrayData}
import org.apache.spark.sql.types._
import org.apache.spark.unsafe.types.UTF8String

/**
 * Type-inference utilities for POJOs and Java collections.
 */
object JavaTypeInference {

  private val iterableType = TypeToken.of(classOf[JIterable[_]])
  private val mapType = TypeToken.of(classOf[JMap[_, _]])
  private val listType = TypeToken.of(classOf[JList[_]])
  private val iteratorReturnType = classOf[JIterable[_]].getMethod("iterator").getGenericReturnType
  private val nextReturnType = classOf[JIterator[_]].getMethod("next").getGenericReturnType
  private val keySetReturnType = classOf[JMap[_, _]].getMethod("keySet").getGenericReturnType
  private val valuesReturnType = classOf[JMap[_, _]].getMethod("values").getGenericReturnType

  // Guava changed the name of this method; this tries to stay compatible with both
  // TODO replace with isSupertypeOf when Guava 14 support no longer needed for Hadoop
  private val ttIsAssignableFrom: (TypeToken[_], TypeToken[_]) => Boolean = {
    val ttMethods = classOf[TypeToken[_]].getMethods.
      filter(_.getParameterCount == 1).
      filter(_.getParameterTypes.head == classOf[TypeToken[_]])
    val isAssignableFromMethod = ttMethods.find(_.getName == "isSupertypeOf").getOrElse(
      ttMethods.find(_.getName == "isAssignableFrom").get)
    (a: TypeToken[_], b: TypeToken[_]) => isAssignableFromMethod.invoke(a, b).asInstanceOf[Boolean]
  }

  /**
   * Infers the corresponding SQL data type of a JavaBean class.
   * @param beanClass Java type
   * @return (SQL data type, nullable)
   */
  def inferDataType(beanClass: Class[_]): (DataType, Boolean) = {
    inferDataType(TypeToken.of(beanClass))
  }

  /**
   * Infers the corresponding SQL data type of a Java type.
   * @param beanType Java type
   * @return (SQL data type, nullable)
   */
  private[sql] def inferDataType(beanType: Type): (DataType, Boolean) = {
    inferDataType(TypeToken.of(beanType))
  }

  /**
   * Infers the corresponding SQL data type of a Java type.
   * @param typeToken Java type
   * @return (SQL data type, nullable)
   */
  private def inferDataType(typeToken: TypeToken[_], seenTypeSet: Set[Class[_]] = Set.empty)
    : (DataType, Boolean) = {
    typeToken.getRawType match {
      case c: Class[_] if c.isAnnotationPresent(classOf[SQLUserDefinedType]) =>
        (c.getAnnotation(classOf[SQLUserDefinedType]).udt().newInstance(), true)

      case c: Class[_] if UDTRegistration.exists(c.getName) =>
        val udt = UDTRegistration.getUDTFor(c.getName).get.newInstance()
          .asInstanceOf[UserDefinedType[_ >: Null]]
        (udt, true)

      case c: Class[_] if c == classOf[java.lang.String] => (StringType, true)
      case c: Class[_] if c == classOf[Array[Byte]] => (BinaryType, true)

      case c: Class[_] if c == java.lang.Short.TYPE => (ShortType, false)
      case c: Class[_] if c == java.lang.Integer.TYPE => (IntegerType, false)
      case c: Class[_] if c == java.lang.Long.TYPE => (LongType, false)
      case c: Class[_] if c == java.lang.Double.TYPE => (DoubleType, false)
      case c: Class[_] if c == java.lang.Byte.TYPE => (ByteType, false)
      case c: Class[_] if c == java.lang.Float.TYPE => (FloatType, false)
      case c: Class[_] if c == java.lang.Boolean.TYPE => (BooleanType, false)

      case c: Class[_] if c == classOf[java.lang.Short] => (ShortType, true)
      case c: Class[_] if c == classOf[java.lang.Integer] => (IntegerType, true)
      case c: Class[_] if c == classOf[java.lang.Long] => (LongType, true)
      case c: Class[_] if c == classOf[java.lang.Double] => (DoubleType, true)
      case c: Class[_] if c == classOf[java.lang.Byte] => (ByteType, true)
      case c: Class[_] if c == classOf[java.lang.Float] => (FloatType, true)
      case c: Class[_] if c == classOf[java.lang.Boolean] => (BooleanType, true)

      case c: Class[_] if c == classOf[java.math.BigDecimal] => (DecimalType.SYSTEM_DEFAULT, true)
      case c: Class[_] if c == classOf[java.math.BigInteger] => (DecimalType.BigIntDecimal, true)
      case c: Class[_] if c == classOf[java.sql.Date] => (DateType, true)
      case c: Class[_] if c == classOf[java.sql.Timestamp] => (TimestampType, true)

      case _ if typeToken.isArray =>
        val (dataType, nullable) = inferDataType(typeToken.getComponentType, seenTypeSet)
        (ArrayType(dataType, nullable), true)

      case _ if ttIsAssignableFrom(iterableType, typeToken) =>
        val (dataType, nullable) = inferDataType(elementType(typeToken), seenTypeSet)
        (ArrayType(dataType, nullable), true)

      case _ if ttIsAssignableFrom(mapType, typeToken) =>
        val (keyType, valueType) = mapKeyValueType(typeToken)
        val (keyDataType, _) = inferDataType(keyType, seenTypeSet)
        val (valueDataType, nullable) = inferDataType(valueType, seenTypeSet)
        (MapType(keyDataType, valueDataType, nullable), true)

      case other if other.isEnum =>
        (StringType, true)

      case other =>
        if (seenTypeSet.contains(other)) {
          throw new UnsupportedOperationException(
            "Cannot have circular references in bean class, but got the circular reference " +
              s"of class $other")
        }

        // TODO: we should only collect properties that have getter and setter. However, some tests
        // pass in scala case class as java bean class which doesn't have getter and setter.
        val properties = getJavaBeanReadableProperties(other)
        val fields = properties.map { property =>
          val returnType = typeToken.method(property.getReadMethod).getReturnType
          val (dataType, nullable) = inferDataType(returnType, seenTypeSet + other)
          new StructField(property.getName, dataType, nullable)
        }
        (new StructType(fields), true)
    }
  }

  def getJavaBeanReadableProperties(beanClass: Class[_]): Array[PropertyDescriptor] = {
    val beanInfo = Introspector.getBeanInfo(beanClass)
    beanInfo.getPropertyDescriptors.filterNot(_.getName == "class")
      .filterNot(_.getName == "declaringClass")
      .filter(_.getReadMethod != null)
  }

  private def getJavaBeanReadableAndWritableProperties(
      beanClass: Class[_]): Array[PropertyDescriptor] = {
    getJavaBeanReadableProperties(beanClass).filter(_.getWriteMethod != null)
  }

  private def elementType(typeToken: TypeToken[_]): TypeToken[_] = {
    val typeToken2 = typeToken.asInstanceOf[TypeToken[_ <: JIterable[_]]]
    val iterableSuperType = typeToken2.getSupertype(classOf[JIterable[_]])
    val iteratorType = iterableSuperType.resolveType(iteratorReturnType)
    iteratorType.resolveType(nextReturnType)
  }

  private def mapKeyValueType(typeToken: TypeToken[_]): (TypeToken[_], TypeToken[_]) = {
    val typeToken2 = typeToken.asInstanceOf[TypeToken[_ <: JMap[_, _]]]
    val mapSuperType = typeToken2.getSupertype(classOf[JMap[_, _]])
    val keyType = elementType(mapSuperType.resolveType(keySetReturnType))
    val valueType = elementType(mapSuperType.resolveType(valuesReturnType))
    keyType -> valueType
  }

  /**
   * Returns the Spark SQL DataType for a given java class.  Where this is not an exact mapping
   * to a native type, an ObjectType is returned.
   *
   * Unlike `inferDataType`, this function doesn't do any massaging of types into the Spark SQL type
   * system.  As a result, ObjectType will be returned for things like boxed Integers.
   */
  private def inferExternalType(cls: Class[_]): DataType = cls match {
    case c if c == java.lang.Boolean.TYPE => BooleanType
    case c if c == java.lang.Byte.TYPE => ByteType
    case c if c == java.lang.Short.TYPE => ShortType
    case c if c == java.lang.Integer.TYPE => IntegerType
    case c if c == java.lang.Long.TYPE => LongType
    case c if c == java.lang.Float.TYPE => FloatType
    case c if c == java.lang.Double.TYPE => DoubleType
    case c if c == classOf[Array[Byte]] => BinaryType
    case _ => ObjectType(cls)
  }

  /**
   * Returns an expression that can be used to deserialize an internal row to an object of java bean
   * `T` with a compatible schema.  Fields of the row will be extracted using UnresolvedAttributes
   * of the same name as the constructor arguments.  Nested classes will have their fields accessed
   * using UnresolvedExtractValue.
   */
  def deserializerFor(beanClass: Class[_]): Expression = {
    deserializerFor(TypeToken.of(beanClass), None)
  }

  private def deserializerFor(typeToken: TypeToken[_], path: Option[Expression]): Expression = {
    /** Returns the current path with a sub-field extracted. */
    def addToPath(part: String): Expression = path
      .map(p => UnresolvedExtractValue(p, expressions.Literal(part)))
      .getOrElse(UnresolvedAttribute(part))

    /** Returns the current path or `GetColumnByOrdinal`. */
    def getPath: Expression = path.getOrElse(GetColumnByOrdinal(0, inferDataType(typeToken)._1))

    typeToken.getRawType match {
      case c if !inferExternalType(c).isInstanceOf[ObjectType] => getPath

      case c if c == classOf[java.lang.Short] ||
                c == classOf[java.lang.Integer] ||
                c == classOf[java.lang.Long] ||
                c == classOf[java.lang.Double] ||
                c == classOf[java.lang.Float] ||
                c == classOf[java.lang.Byte] ||
                c == classOf[java.lang.Boolean] =>
        StaticInvoke(
          c,
          ObjectType(c),
          "valueOf",
          getPath :: Nil,
          returnNullable = false)

      case c if c == classOf[java.sql.Date] =>
        StaticInvoke(
          DateTimeUtils.getClass,
          ObjectType(c),
          "toJavaDate",
          getPath :: Nil,
          returnNullable = false)

      case c if c == classOf[java.sql.Timestamp] =>
        StaticInvoke(
          DateTimeUtils.getClass,
          ObjectType(c),
          "toJavaTimestamp",
          getPath :: Nil,
          returnNullable = false)

      case c if c == classOf[java.lang.String] =>
        Invoke(getPath, "toString", ObjectType(classOf[String]))

      case c if c == classOf[java.math.BigDecimal] =>
        Invoke(getPath, "toJavaBigDecimal", ObjectType(classOf[java.math.BigDecimal]))

      case c if c.isArray =>
        val elementType = c.getComponentType
        val primitiveMethod = elementType match {
          case c if c == java.lang.Boolean.TYPE => Some("toBooleanArray")
          case c if c == java.lang.Byte.TYPE => Some("toByteArray")
          case c if c == java.lang.Short.TYPE => Some("toShortArray")
          case c if c == java.lang.Integer.TYPE => Some("toIntArray")
          case c if c == java.lang.Long.TYPE => Some("toLongArray")
          case c if c == java.lang.Float.TYPE => Some("toFloatArray")
          case c if c == java.lang.Double.TYPE => Some("toDoubleArray")
          case _ => None
        }

        primitiveMethod.map { method =>
          Invoke(getPath, method, ObjectType(c))
        }.getOrElse {
          Invoke(
            MapObjects(
              p => deserializerFor(typeToken.getComponentType, Some(p)),
              getPath,
              inferDataType(elementType)._1),
            "array",
            ObjectType(c))
        }

      case c if ttIsAssignableFrom(listType, typeToken) =>
        val et = elementType(typeToken)
        UnresolvedMapObjects(
          p => deserializerFor(et, Some(p)),
          getPath,
          customCollectionCls = Some(c))

      case _ if ttIsAssignableFrom(mapType, typeToken) =>
        val (keyType, valueType) = mapKeyValueType(typeToken)
        val keyDataType = inferDataType(keyType)._1
        val valueDataType = inferDataType(valueType)._1

        val keyData =
          Invoke(
            MapObjects(
              p => deserializerFor(keyType, Some(p)),
              Invoke(getPath, "keyArray", ArrayType(keyDataType)),
              keyDataType),
            "array",
            ObjectType(classOf[Array[Any]]))

        val valueData =
          Invoke(
            MapObjects(
              p => deserializerFor(valueType, Some(p)),
              Invoke(getPath, "valueArray", ArrayType(valueDataType)),
              valueDataType),
            "array",
            ObjectType(classOf[Array[Any]]))

        StaticInvoke(
          ArrayBasedMapData.getClass,
          ObjectType(classOf[JMap[_, _]]),
          "toJavaMap",
          keyData :: valueData :: Nil,
          returnNullable = false)

      case other if other.isEnum =>
        StaticInvoke(
          other,
          ObjectType(other),
          "valueOf",
          Invoke(getPath, "toString", ObjectType(classOf[String]), returnNullable = false) :: Nil,
          returnNullable = false)

      case other =>
        val properties = getJavaBeanReadableAndWritableProperties(other)
        val setters = properties.map { p =>
          val fieldName = p.getName
          val fieldType = typeToken.method(p.getReadMethod).getReturnType
          val (_, nullable) = inferDataType(fieldType)
          val constructor = deserializerFor(fieldType, Some(addToPath(fieldName)))
          val setter = if (nullable) {
            constructor
          } else {
            AssertNotNull(constructor, Seq("currently no type path record in java"))
          }
          p.getWriteMethod.getName -> setter
        }.toMap

        val newInstance = NewInstance(other, Nil, ObjectType(other), propagateNull = false)
        val result = InitializeJavaBean(newInstance, setters)

        if (path.nonEmpty) {
          expressions.If(
            IsNull(getPath),
            expressions.Literal.create(null, ObjectType(other)),
            result
          )
        } else {
          result
        }
    }
  }

  /**
   * Returns an expression for serializing an object of the given type to an internal row.
   */
  def serializerFor(beanClass: Class[_]): CreateNamedStruct = {
    val inputObject = BoundReference(0, ObjectType(beanClass), nullable = true)
    val nullSafeInput = AssertNotNull(inputObject, Seq("top level input bean"))
    serializerFor(nullSafeInput, TypeToken.of(beanClass)) match {
      case expressions.If(_, _, s: CreateNamedStruct) => s
      case other => CreateNamedStruct(expressions.Literal("value") :: other :: Nil)
    }
  }

  private def serializerFor(inputObject: Expression, typeToken: TypeToken[_]): Expression = {

    def toCatalystArray(input: Expression, elementType: TypeToken[_]): Expression = {
      val (dataType, nullable) = inferDataType(elementType)
      if (ScalaReflection.isNativeType(dataType)) {
        NewInstance(
          classOf[GenericArrayData],
          input :: Nil,
          dataType = ArrayType(dataType, nullable))
      } else {
        MapObjects(serializerFor(_, elementType), input, ObjectType(elementType.getRawType))
      }
    }

    if (!inputObject.dataType.isInstanceOf[ObjectType]) {
      inputObject
    } else {
      typeToken.getRawType match {
        case c if c == classOf[String] =>
          StaticInvoke(
            classOf[UTF8String],
            StringType,
            "fromString",
            inputObject :: Nil,
            returnNullable = false)

        case c if c == classOf[java.sql.Timestamp] =>
          StaticInvoke(
            DateTimeUtils.getClass,
            TimestampType,
            "fromJavaTimestamp",
            inputObject :: Nil,
            returnNullable = false)

        case c if c == classOf[java.sql.Date] =>
          StaticInvoke(
            DateTimeUtils.getClass,
            DateType,
            "fromJavaDate",
            inputObject :: Nil,
            returnNullable = false)

        case c if c == classOf[java.math.BigDecimal] =>
          StaticInvoke(
            Decimal.getClass,
            DecimalType.SYSTEM_DEFAULT,
            "apply",
            inputObject :: Nil,
            returnNullable = false)

        case c if c == classOf[java.lang.Boolean] =>
          Invoke(inputObject, "booleanValue", BooleanType)
        case c if c == classOf[java.lang.Byte] =>
          Invoke(inputObject, "byteValue", ByteType)
        case c if c == classOf[java.lang.Short] =>
          Invoke(inputObject, "shortValue", ShortType)
        case c if c == classOf[java.lang.Integer] =>
          Invoke(inputObject, "intValue", IntegerType)
        case c if c == classOf[java.lang.Long] =>
          Invoke(inputObject, "longValue", LongType)
        case c if c == classOf[java.lang.Float] =>
          Invoke(inputObject, "floatValue", FloatType)
        case c if c == classOf[java.lang.Double] =>
          Invoke(inputObject, "doubleValue", DoubleType)

        case _ if typeToken.isArray =>
          toCatalystArray(inputObject, typeToken.getComponentType)

        case _ if ttIsAssignableFrom(listType, typeToken) =>
          toCatalystArray(inputObject, elementType(typeToken))

        case _ if ttIsAssignableFrom(mapType, typeToken) =>
          val (keyType, valueType) = mapKeyValueType(typeToken)

          ExternalMapToCatalyst(
            inputObject,
            ObjectType(keyType.getRawType),
            serializerFor(_, keyType),
            keyNullable = true,
            ObjectType(valueType.getRawType),
            serializerFor(_, valueType),
            valueNullable = true
          )

        case other if other.isEnum =>
          StaticInvoke(
            classOf[UTF8String],
            StringType,
            "fromString",
            Invoke(inputObject, "name", ObjectType(classOf[String]), returnNullable = false) :: Nil,
            returnNullable = false)

        case other =>
          val properties = getJavaBeanReadableAndWritableProperties(other)
          val nonNullOutput = CreateNamedStruct(properties.flatMap { p =>
            val fieldName = p.getName
            val fieldType = typeToken.method(p.getReadMethod).getReturnType
            val fieldValue = Invoke(
              inputObject,
              p.getReadMethod.getName,
              inferExternalType(fieldType.getRawType))
            expressions.Literal(fieldName) :: serializerFor(fieldValue, fieldType) :: Nil
          })

          val nullOutput = expressions.Literal.create(null, nonNullOutput.dataType)
          expressions.If(IsNull(inputObject), nullOutput, nonNullOutput)
      }
    }
  }
}
