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

package org.apache.spark.graphx.util

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import scala.collection.mutable.HashSet
import scala.language.existentials

import org.apache.xbean.asm6.{ClassReader, ClassVisitor, MethodVisitor}
import org.apache.xbean.asm6.Opcodes._
import org.apache.xbean.asm9.{ClassReader => ClassReader9}
import org.apache.xbean.asm9.{ClassVisitor => ClassVisitor9}

import org.apache.spark.util.Utils

/**
 * Includes an utility function to test whether a function accesses a specific attribute
 * of an object.
 */
private[graphx] object BytecodeUtils {

  /**
   * Test whether the given closure invokes the specified method in the specified class.
   */
  def invokedMethod(closure: AnyRef, targetClass: Class[_], targetMethod: String): Boolean = {
    if (_invokedMethod(closure.getClass, "apply", targetClass, targetMethod)) {
      true
    } else {
      // look at closures enclosed in this closure
      for (f <- closure.getClass.getDeclaredFields
           if f.getType.getName.startsWith("scala.Function")) {
        f.setAccessible(true)
        if (invokedMethod(f.get(closure), targetClass, targetMethod)) {
          return true
        }
      }
      return false
    }
  }

  private def _invokedMethod(cls: Class[_], method: String,
                             targetClass: Class[_], targetMethod: String): Boolean = {

    val seen = new HashSet[(Class[_], String)]
    var stack = List[(Class[_], String)]((cls, method))

    while (stack.nonEmpty) {
      val (c, m) = stack.head
      stack = stack.tail
      seen.add((c, m))
      val finder = new MethodInvocationFinder(c.getName, m)
      getClassReader(c).accept(finder, 0)
      for (classMethod <- finder.methodsInvoked) {
        if (classMethod._1 == targetClass && classMethod._2 == targetMethod) {
          return true
        } else if (!seen.contains(classMethod)) {
          stack = classMethod :: stack
        }
      }
    }
    return false
  }

  /**
   * Get an ASM class reader for a given class from the JAR that loaded it.
   * Supports both JDK8 (using ASM6) and JDK11+ (using ASM9 to downgrade to JDK8 bytecode).
   */
  private def getClassReader(cls: Class[_]): ClassReader = {
    // Copy data over, before delegating to ClassReader - else we can run out of open file handles.
    val className = cls.getName.replaceFirst("^.*\\.", "") + ".class"
    val resourceStream = cls.getResourceAsStream(className)
    // todo: Fixme - continuing with earlier behavior ...
    if (resourceStream == null) return new ClassReader(resourceStream)

    val baos = new ByteArrayOutputStream(128)
    Utils.copyStream(resourceStream, baos, true)
    val classBytes = baos.toByteArray

    // Check class file major version stored at bytes 6-7.
    // JDK8=52, JDK9=53, JDK10=54, JDK11=55, JDK17=61
    val majorVersion = ((classBytes(6) & 0xFF) << 8) | (classBytes(7) & 0xFF)

    if (majorVersion >= 55) {
      // JDK11+ class — use ASM9 to read and downgrade version to JDK8 (52)
      // so existing ASM6-based visitors can process it safely
      val reader9 = new ClassReader9(new ByteArrayInputStream(classBytes))
      val writer9 = new org.apache.xbean.asm9.ClassWriter(0)
      reader9.accept(new ClassVisitor9(org.apache.xbean.asm9.Opcodes.ASM9, writer9) {
        override def visit(
                            version: Int,
                            access: Int,
                            name: String,
                            signature: String,
                            superName: String,
                            interfaces: Array[String]): Unit = {
          // Downgrade class version to Java 8 (52)
          super.visit(52, access, name, signature, superName, interfaces)
        }
      }, 0)
      new ClassReader(new ByteArrayInputStream(writer9.toByteArray))
    } else {
      // JDK8/10 class — existing behavior unchanged
      new ClassReader(new ByteArrayInputStream(classBytes))
    }
  }

  /**
   * Given the class name, return whether we should look into the class or not. This is used to
   * skip examining a large quantity of Java or Scala classes that we know for sure wouldn't access
   * the closures. Note that the class name is expected in ASM style (i.e. use "/" instead of ".").
   */
  private def skipClass(className: String): Boolean = {
    val c = className
    c.startsWith("java/") || c.startsWith("scala/") || c.startsWith("javax/")
  }

  /**
   * Find the set of methods invoked by the specified method in the specified class.
   * For example, after running the visitor,
   *   MethodInvocationFinder("spark/graph/Foo", "test")
   * its methodsInvoked variable will contain the set of methods invoked directly by
   * Foo.test(). Interface invocations are not returned as part of the result set because we cannot
   * determine the actual method invoked by inspecting the bytecode.
   */
  private class MethodInvocationFinder(className: String, methodName: String)
    extends ClassVisitor(ASM6) {

    val methodsInvoked = new HashSet[(Class[_], String)]

    override def visitMethod(access: Int, name: String, desc: String,
                             sig: String, exceptions: Array[String]): MethodVisitor = {
      if (name == methodName) {
        new MethodVisitor(ASM6) {
          override def visitMethodInsn(
                                        op: Int, owner: String, name: String, desc: String, itf: Boolean) {
            if (op == INVOKEVIRTUAL || op == INVOKESPECIAL || op == INVOKESTATIC) {
              if (!skipClass(owner)) {
                methodsInvoked.add((Utils.classForName(owner.replace("/", ".")), name))
              }
            }
          }
        }
      } else {
        null
      }
    }
  }
}