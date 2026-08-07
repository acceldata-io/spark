#!/usr/bin/env bash

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

#
# Script to create a binary distribution for easy deploys of Spark.
# The distribution directory defaults to dist/ but can be overridden below.
# The distribution contains fat (assembly) jars that include the Scala library,
# so it is completely self contained.
# It does not contain source or *.class files.

set -o pipefail
set -e
set -x

# Figure out where the Spark framework is installed
SPARK_HOME="$(cd "`dirname "$0"`/.."; pwd)"
DISTDIR="$SPARK_HOME/dist"

MAKE_TGZ=false
MAKE_PIP=false
MAKE_R=false
NAME=none
MVN="$SPARK_HOME/build/mvn"

function exit_with_usage {
  set +x
  echo "make-distribution.sh - tool for making binary distributions of Spark"
  echo ""
  echo "usage:"
  cl_options="[--name] [--tgz] [--pip] [--r] [--mvn <mvn-command>]"
  echo "make-distribution.sh $cl_options <maven build options>"
  echo "See Spark's \"Building Spark\" doc for correct Maven options."
  echo ""
  exit 1
}

# Parse arguments
while (( "$#" )); do
  case $1 in
    --tgz)
      MAKE_TGZ=true
      ;;
    --pip)
      MAKE_PIP=true
      ;;
    --r)
      MAKE_R=true
      ;;
    --mvn)
      MVN="$2"
      shift
      ;;
    --name)
      NAME="$2"
      shift
      ;;
    --help)
      exit_with_usage
      ;;
    --*)
      echo "Error: $1 is not supported"
      exit_with_usage
      ;;
    -*)
      break
      ;;
    *)
      echo "Error: $1 is not supported"
      exit_with_usage
      ;;
  esac
  shift
done

if [ -z "$JAVA_HOME" ]; then
  # Fall back on JAVA_HOME from rpm, if found
  if [ $(command -v  rpm) ]; then
    RPM_JAVA_HOME="$(rpm -E %java_home 2>/dev/null)"
    if [ "$RPM_JAVA_HOME" != "%java_home" ]; then
      JAVA_HOME="$RPM_JAVA_HOME"
      echo "No JAVA_HOME set, proceeding with '$JAVA_HOME' learned from rpm"
    fi
  fi

  if [ -z "$JAVA_HOME" ]; then
    if [ `command -v java` ]; then
      # If java is in /usr/bin/java, we want /usr
      JAVA_HOME="$(dirname $(dirname $(which java)))"
    fi
  fi
fi

if [ -z "$JAVA_HOME" ]; then
  echo "Error: JAVA_HOME is not set, cannot proceed."
  exit -1
fi

if [ $(command -v git) ]; then
    GITREV=$(git rev-parse --short HEAD 2>/dev/null || :)
    if [ ! -z "$GITREV" ]; then
        GITREVSTRING=" (git revision $GITREV)"
    fi
    unset GITREV
fi


if [ ! "$(command -v "$MVN")" ] ; then
    echo -e "Could not locate Maven command: '$MVN'."
    echo -e "Specify the Maven command with the --mvn flag"
    exit -1;
fi

VERSION=$("$MVN" help:evaluate -Dexpression=project.version $@ 2>/dev/null\
    | grep -v "INFO"\
    | grep -v "WARNING"\
    | tail -n 1)
SCALA_VERSION=$("$MVN" help:evaluate -Dexpression=scala.binary.version $@ 2>/dev/null\
    | grep -v "INFO"\
    | grep -v "WARNING"\
    | tail -n 1)
SPARK_HADOOP_VERSION=$("$MVN" help:evaluate -Dexpression=hadoop.version $@ 2>/dev/null\
    | grep -v "INFO"\
    | grep -v "WARNING"\
    | tail -n 1)
SPARK_HIVE=$("$MVN" help:evaluate -Dexpression=project.activeProfiles -pl sql/hive $@ 2>/dev/null\
    | grep -v "INFO"\
    | grep -v "WARNING"\
    | fgrep --count "<id>hive</id>";\
    # Reset exit status to 0, otherwise the script stops here if the last grep finds nothing\
    # because we use "set -o pipefail"
    echo -n)

if [ "$NAME" == "none" ]; then
  NAME=$SPARK_HADOOP_VERSION
fi

echo "Spark version is $VERSION"

if [ "$MAKE_TGZ" == "true" ]; then
  echo "Making spark-$VERSION-bin-$NAME.tgz"
else
  echo "Making distribution for Spark $VERSION in '$DISTDIR'..."
fi

# Build uber fat JAR
cd "$SPARK_HOME"

export MAVEN_OPTS="${MAVEN_OPTS:--Xss128m -Xms512m -Xmx6g -XX:ReservedCodeCacheSize=2g}"

# Store the command as an array because $MVN variable might have spaces in it.
# Normal quoting tricks don't work.
# See: http://mywiki.wooledge.org/BashFAQ/050
BUILD_COMMAND=("$MVN" clean install -DskipTests $@)

# Actually build the jar
echo -e "\nBuilding with..."
echo -e "\$ ${BUILD_COMMAND[@]}\n"

"${BUILD_COMMAND[@]}"

# Make directories
rm -rf "$DISTDIR"
mkdir -p "$DISTDIR/jars"
echo "Spark $VERSION$GITREVSTRING built for Hadoop $SPARK_HADOOP_VERSION" > "$DISTDIR/RELEASE"
echo "Build flags: $@" >> "$DISTDIR/RELEASE"

# Copy jars
cp "$SPARK_HOME"/assembly/target/scala*/jars/* "$DISTDIR/jars/"

# ODP-7074: the full hive-exec-1.2.1 jar bundles pre-split copies of the classes that now live in
# hive-storage-api (org.apache.hadoop.hive.{ql.exec.vector,serde2.io,common.type}.*). Those stale
# copies shadow hive-storage-api-2.8.1, so orc-core-1.9.5's OrcMapredRecordWriter throws
# NoSuchFieldError: type on native + HWC ORC writes. Compilation uses the full jar (keeps HiveConf
# etc., so no core-classifier cascade); here we trim ONLY the hive-storage-api-owned classes from
# the shipped hive-exec so they resolve to hive-storage-api-2.8.1 - same net effect as upstream's
# hive-exec-core without dropping HiveConf.
HIVE_EXEC_DIST=$(ls "$DISTDIR"/jars/hive-exec-*.jar 2>/dev/null | grep -v -- '-core' | head -1)
HIVE_SAPI_DIST=$(ls "$DISTDIR"/jars/hive-storage-api-*.jar 2>/dev/null | head -1)
if [ -n "$HIVE_EXEC_DIST" ] && [ -n "$HIVE_SAPI_DIST" ]; then
  echo "ODP-7074: trimming hive-storage-api-owned classes from $(basename "$HIVE_EXEC_DIST")"
  python - "$HIVE_EXEC_DIST" "$HIVE_SAPI_DIST" <<'PYEOF'
import sys, zipfile, shutil
exec_jar, sapi_jar = sys.argv[1], sys.argv[2]
owned = set(n for n in zipfile.ZipFile(sapi_jar).namelist() if n.endswith('.class'))
tmp = exec_jar + '.trimmed'
zin = zipfile.ZipFile(exec_jar)
zout = zipfile.ZipFile(tmp, 'w', zipfile.ZIP_DEFLATED)
removed = 0
for item in zin.infolist():
    if item.filename in owned:
        removed += 1
        continue
    zout.writestr(item, zin.read(item.filename))
zin.close(); zout.close()
shutil.move(tmp, exec_jar)
sys.stderr.write("ODP-7074: removed %d storage-api classes from %s\n" % (removed, exec_jar))
PYEOF
fi

# ODP-7072: the Hive-1.2-fork hive-exec's serde2.JsonSerDe (and the HiveTimestampCompat/HiveDateCompat
# proleptic<->hybrid calendar bridge) reference org.apache.hadoop.hive.common.type.{Date,Timestamp},
# but those classes exist in neither the Hive-1.2 hive-exec nor hive-storage-api-2.8.1. Native
# spark.sql reads through the Hive serde path (e.g. JsonSerDe over Hive-4 tables) therefore fail with
# NoClassDefFoundError: org/apache/hadoop/hive/common/type/Date. Bundle ONLY the date/timestamp
# classes from Hive 4.0.1's hive-common - deliberately NOT HiveDecimal/HiveChar/HiveInterval*, which
# would re-shadow hive-storage-api-2.8.1 and re-break HWC/ORC decimal reads. hive-common is resolved
# from the same ODP repo the build already uses.
HIVE4_VERSION="${HIVE4_VERSION:-4.0.1.3.3.6.5-SNAPSHOT}"
CT_DIR=$(mktemp -d)
if "$MVN" -q org.apache.maven.plugins:maven-dependency-plugin:3.1.1:copy \
     -Dartifact=org.apache.hive:hive-common:${HIVE4_VERSION} \
     -DoutputDirectory="$CT_DIR" -Dmdep.stripVersion=true "$@" >/dev/null 2>&1 \
   && [ -f "$CT_DIR/hive-common.jar" ]; then
  echo "ODP-7072: bundling Hive-4 common.type date/timestamp classes -> odp-hive4-common-type.jar"
  python - "$CT_DIR/hive-common.jar" "$DISTDIR/jars/odp-hive4-common-type.jar" <<'PYEOF'
import sys, zipfile
src, dst = sys.argv[1], sys.argv[2]
pfx = "org/apache/hadoop/hive/common/type/"
keep = ("Date", "Timestamp")  # NOT HiveDecimal/HiveChar/etc - those clash with hive-storage-api-2.8.1
zin = zipfile.ZipFile(src)
zout = zipfile.ZipFile(dst, 'w', zipfile.ZIP_DEFLATED)
n = 0
for item in zin.infolist():
    fn = item.filename
    if fn.startswith(pfx) and fn.endswith('.class') and fn[len(pfx):].startswith(keep):
        zout.writestr(item, zin.read(fn)); n += 1
zin.close(); zout.close()
sys.stderr.write("ODP-7072: bundled %d common.type date/timestamp classes into %s\n" % (n, dst))
PYEOF
else
  echo "ODP-7072 WARNING: could not resolve org.apache.hive:hive-common:${HIVE4_VERSION}; native Hive-serde date/timestamp reads will fail with NoClassDefFoundError: common/type/Date" >&2
fi
rm -rf "$CT_DIR"

# Only create the standalone metastore directory if metastore artifact were copied.
if [ -f "$SPARK_HOME"/standalone-metastore/target/standalone-metastore-*.jar ]; then
  mkdir "$DISTDIR/standalone-metastore"
  cp "$SPARK_HOME"/standalone-metastore/target/standalone-metastore-*.jar "$DISTDIR/standalone-metastore"
fi

# Only create the yarn directory if the yarn artifacts were built.
if [ -f "$SPARK_HOME"/common/network-yarn/target/scala*/spark-*-yarn-shuffle.jar ]; then
  mkdir "$DISTDIR/yarn"
  cp "$SPARK_HOME"/common/network-yarn/target/scala*/spark-*-yarn-shuffle.jar "$DISTDIR/yarn"
fi

# Only create and copy the dockerfiles directory if the kubernetes artifacts were built.
if [ -d "$SPARK_HOME"/resource-managers/kubernetes/core/target/ ]; then
  mkdir -p "$DISTDIR/kubernetes/"
  cp -a "$SPARK_HOME"/resource-managers/kubernetes/docker/src/main/dockerfiles "$DISTDIR/kubernetes/"
  cp -a "$SPARK_HOME"/resource-managers/kubernetes/integration-tests/tests "$DISTDIR/kubernetes/"
fi

# Copy examples and dependencies
mkdir -p "$DISTDIR/examples/jars"
cp "$SPARK_HOME"/examples/target/scala*/jars/* "$DISTDIR/examples/jars"

# Deduplicate jars that have already been packaged as part of the main Spark dependencies.
for f in "$DISTDIR"/examples/jars/*; do
  name=$(basename "$f")
  if [ -f "$DISTDIR/jars/$name" ]; then
    rm "$DISTDIR/examples/jars/$name"
  fi
done

# Copy example sources (needed for python and SQL)
mkdir -p "$DISTDIR/examples/src/main"
cp -r "$SPARK_HOME/examples/src/main" "$DISTDIR/examples/src/"

# Copy license and ASF files
if [ -e "$SPARK_HOME/LICENSE-binary" ]; then
  cp "$SPARK_HOME/LICENSE-binary" "$DISTDIR/LICENSE"
  cp -r "$SPARK_HOME/licenses-binary" "$DISTDIR/licenses"
  cp "$SPARK_HOME/NOTICE-binary" "$DISTDIR/NOTICE"
else
  echo "Skipping copying LICENSE files"
fi

if [ -e "$SPARK_HOME/CHANGES.txt" ]; then
  cp "$SPARK_HOME/CHANGES.txt" "$DISTDIR"
fi

# Copy data files
cp -r "$SPARK_HOME/data" "$DISTDIR"

# Make pip package
if [ "$MAKE_PIP" == "true" ]; then
  echo "Building python distribution package"
  pushd "$SPARK_HOME/python" > /dev/null
  # Delete the egg info file if it exists, this can cache older setup files.
  rm -rf pyspark.egg-info || echo "No existing egg info file, skipping deletion"
  python setup.py sdist
  popd > /dev/null
else
  echo "Skipping building python distribution package"
fi

# Make R package - this is used for both CRAN release and packing R layout into distribution
if [ "$MAKE_R" == "true" ]; then
  echo "Building R source package"
  R_PACKAGE_VERSION=`grep Version "$SPARK_HOME/R/pkg/DESCRIPTION" | awk '{print $NF}'`
  pushd "$SPARK_HOME/R" > /dev/null
  # Build source package and run full checks
  # Do not source the check-cran.sh - it should be run from where it is for it to set SPARK_HOME
  NO_TESTS=1 "$SPARK_HOME/R/check-cran.sh"

  # Move R source package to match the Spark release version if the versions are not the same.
  # NOTE(shivaram): `mv` throws an error on Linux if source and destination are same file
  if [ "$R_PACKAGE_VERSION" != "$VERSION" ]; then
    mv "$SPARK_HOME/R/SparkR_$R_PACKAGE_VERSION.tar.gz" "$SPARK_HOME/R/SparkR_$VERSION.tar.gz"
  fi

  # Install source package to get it to generate vignettes rds files, etc.
  VERSION=$VERSION "$SPARK_HOME/R/install-source-package.sh"
  popd > /dev/null
else
  echo "Skipping building R source package"
fi

# Copy other things
mkdir "$DISTDIR/conf"
cp "$SPARK_HOME"/conf/*.template "$DISTDIR/conf"
cp "$SPARK_HOME/README.md" "$DISTDIR"
cp -r "$SPARK_HOME/bin" "$DISTDIR"
cp -r "$SPARK_HOME/python" "$DISTDIR"

# Remove the python distribution from dist/ if we built it
if [ "$MAKE_PIP" == "true" ]; then
  rm -f "$DISTDIR"/python/dist/pyspark-*.tar.gz
fi

cp -r "$SPARK_HOME/sbin" "$DISTDIR"
# Copy SparkR if it exists
if [ -d "$SPARK_HOME/R/lib/SparkR" ]; then
  mkdir -p "$DISTDIR/R/lib"
  cp -r "$SPARK_HOME/R/lib/SparkR" "$DISTDIR/R/lib"
  cp "$SPARK_HOME/R/lib/sparkr.zip" "$DISTDIR/R/lib"
fi

if [ "$MAKE_TGZ" == "true" ]; then
  TARDIR_NAME=spark-$VERSION-bin-$NAME
  TARDIR="$SPARK_HOME/$TARDIR_NAME"
  rm -rf "$TARDIR"
  cp -r "$DISTDIR" "$TARDIR"
  tar czf "spark-$VERSION-bin-$NAME.tgz" -C "$SPARK_HOME" "$TARDIR_NAME"
  rm -rf "$TARDIR"
fi
