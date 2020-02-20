#!/bin/sh

./wait-for-it.sh docker-mysql:3306 -t 15

java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5006 -Djava.security.egd=file:/dev/./urandom  -jar   app.jar



