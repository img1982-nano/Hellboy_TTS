#!/bin/sh
export JAVA_OPTS="-Xmx1536m"
DIR="$( cd "$( dirname "$0" )" && pwd )"
$DIR/gradle/wrapper/gradle-wrapper.jar "$@"
