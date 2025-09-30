#!/usr/bin/env bash

# Default values
USE_JAR=false
USE_JBANG=false
VERSION=""

# Collect arguments for the cmd
CMD_ARGS=()

while [[ $# -gt 0 ]]; do
  case "$1" in
    -jar)
      USE_JAR=true
      shift
      ;;
    -jbang)
      USE_JBANG=true
      shift
      ;;
    -version)
      VERSION="$2"
      shift 2
      ;;
    *)
      CMD_ARGS+=("$1")
      shift
      ;;
  esac
done

cmd_base_opts="--root-dir src/test/resources --global-response-templating"
cmd_opts="$cmd_base_opts ${CMD_ARGS[@]}"
version="${VERSION:-LATEST}"

log_call() {
  echo "Calling $1 with $cmd_opts"
}
call_jbang() {
  log_call "jbang"
  jbang org.wiremock:wiremock-standalone:$version $cmd_opts
}
call_jar() {
  log_call "jar"
  jar_file="target/dependency/wiremock-standalone.jar"
  rm $jar_file
  if [ ! -f $jar_file ]; then
    mvn dependency:copy -Dartifact="org.wiremock:wiremock-standalone:$version" -DoutputDirectory=target/dependency -Dmdep.stripVersion=true
    #mvn dependency:copy@wiremock-standalone
  fi
  java -jar $jar_file $cmd_opts
}

if $USE_JBANG; then
  call_jbang
elif $USE_JAR; then
  call_jar
else
  call_jbang
fi
