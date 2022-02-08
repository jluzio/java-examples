#!/usr/bin/env bash

args="$@"
cmd_base_opts="--root-dir src/test/resources --global-response-templating"
cmd_arg_opts=$(echo $args | sed -E 's/-jar|-jbang//g')
cmd_opts="$cmd_base_opts $cmd_arg_opts"

log_call() {
  echo "Calling $1 with $cmd_opts"
}
call_jbang() {
  log_call "jbang"
  jbang com.github.tomakehurst:wiremock-jre8-standalone:2.32.0 $cmd_opts
}
call_jar() {
  log_call "jar"
  jar_file="target/dependency/wiremock-jre8-standalone.jar"
  if [ ! -f $jar_file ]; then
    mvn dependency:copy -Dartifact="com.github.tomakehurst:wiremock-jre8-standalone:2.32.0" -DoutputDirectory=target/dependency -Dmdep.stripVersion=true
    #mvn dependency:copy@wiremock-standalone
  fi
  java -jar $jar_file $cmd_opts
}

if [[ $args == *"-jbang"* ]]; then
  call_jbang
elif [[ $args == *"-jar"* ]]; then
  call_jar
else
  call_jbang
fi
