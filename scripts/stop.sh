#!/usr/bin/env bash

PROJECT_ROOT="/home/ubuntu/scripts"
JAR_FILE="$PROJECT_ROOT/build/libs/blog -0.0.1-SNAPSHOT.jar"

DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)
CURRENT_PID=$(pgrep -f $JAR_FILE)


if [ -z $CURRENT_PID ]; then
	echo "$TIME_NOW : no process" >> $DEPLOY_LOG
else
	echo "$TIME_NOW : stop PID $CURRENT_PID" >> $DEPLOY_LOG
	kill -15 $CURRENT_PID
fi