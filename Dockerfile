# Etapa 1: Build (Construcción)
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test --no-daemon

# Etapa 2: Run (Ejecución)
FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080

COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]