# Stage 1: Build
FROM maven:3-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
COPY ./opentelemetry-javaagent.jar .
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY --from=build /app/opentelemetry-javaagent.jar .
EXPOSE 8081
ENTRYPOINT ["java", "-javaagent:./opentelemetry-javaagent.jar", "-Dotel.exporter.otlp.endpoint=http://otel-collector:4318", "-Dotel.metrics.exporter=otlp", "-Dotel.traces.exporter=otlp", "-Dotel.logs.exporter=otlp", "-Dotel.metric.export.interval=15000", "-jar", "app.jar"]