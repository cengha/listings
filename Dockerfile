FROM openjdk:11-jre-slim

EXPOSE 8080

RUN mkdir -p config/docker /heycar/bin
WORKDIR /heycar

RUN pwd
RUN ls

COPY ./bin/docker-entrypoint.sh /heycar/bin/
COPY ./build/libs/application.jar /heycar/

RUN pwd
RUN ls

CMD ["./bin/docker-entrypoint.sh"]

HEALTHCHECK --interval=5m --timeout=3s \
  CMD curl -f http://localhost:8080/internal/health || exit 1
