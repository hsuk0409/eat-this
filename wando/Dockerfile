FROM adoptopenjdk:latest
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

EXPOSE 8080
ENTRYPOINT {"java", "-jar", "/app.jar"}
