FROM openjdk:11

EXPOSE 8080

ARG JAR_FILE=build/libs/*

ADD ${JAR_FILE} app/

RUN find app/ -type f -name '*-plain*' -delete

RUN mv app/* app.jar

ENTRYPOINT exec java -jar /app.jar