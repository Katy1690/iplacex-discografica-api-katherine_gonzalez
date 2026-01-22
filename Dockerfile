# Etapa 1: Build (Construcción del archivo .jar)
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

# Etapa 2: Run (Ejecución)
FROM openjdk:17-jdk-slim
EXPOSE 8080

COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]