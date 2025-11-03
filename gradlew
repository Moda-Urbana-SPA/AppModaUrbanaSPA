#!/usr/bin/env sh
##############################################################################
# Gradle start up script for UN*X
##############################################################################
APP_HOME=$(cd "$(dirname "$0")"; pwd)
DEFAULT_JVM_OPTS=""
CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar
exec "$JAVA_HOME/bin/java" $DEFAULT_JVM_OPTS -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"