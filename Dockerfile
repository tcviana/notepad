# Java version
FROM openjdk:17-jre-slim

# volume
VOLUME /tmp

# package application jar
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# application execute
ENTRYPOINT ["java", "-jar", "/app.jar"]
