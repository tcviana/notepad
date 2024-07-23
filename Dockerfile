# Java version
FROM openjdk:17-slim

# volume
VOLUME /tmp

# package application jar
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# application execute
ENTRYPOINT ["java", "-jar", "/app.jar"]
