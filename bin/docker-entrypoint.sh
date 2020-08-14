#!/usr/bin/env bash
set -o xtrace
set -o errexit

java $JAVA_OPTS $APP_OPTS \
  -Djava.security.egd=file:/dev/./urandom \
  -jar application.jar

# One can pass extra commands that can be passed for troubleshooting in case the application terminates immediately
# after startup
exec "$@"
