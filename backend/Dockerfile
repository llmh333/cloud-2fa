
# Multi-stage Dockerfile optimized for a Spring Boot (Java 17) Maven project
# - Builder stage downloads dependencies first to maximize cache reuse
# - Runtime stage uses a small, secure Java runtime

ARG MAVEN_IMAGE=maven:3.9.4-eclipse-temurin-17
ARG RUNTIME_IMAGE=eclipse-temurin:17-jre-jammy
ARG APP_PORT=8080

FROM ${MAVEN_IMAGE} AS builder
WORKDIR /workspace

# copy only Maven files first to leverage Docker cache for dependencies
COPY pom.xml mvnw* ./
COPY .mvn .mvn
RUN mvn -B -DskipTests dependency:go-offline

# copy source and build
COPY src ./src
RUN mvn -B -DskipTests package --no-transfer-progress

FROM ${RUNTIME_IMAGE} AS runtime
WORKDIR /app

# create non-root user for better security (image supports adduser)
RUN addgroup --system spring && adduser --system --ingroup spring spring || true

# copy the built jar from builder stage
COPY --from=builder /workspace/target/*.jar /app/app.jar
RUN chown -R spring:spring /app || true

USER spring
EXPOSE ${APP_PORT}

# Java runtime flags tuned for container environments
ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=75.0","-jar","/app/app.jar"]

