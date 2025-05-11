# STEP 1
FROM gradle:8.5-jdk17 AS builder

WORKDIR /app

COPY build.gradle settings.gradle ./

RUN gradle dependencies || true

COPY . .

RUN gradle bootJar


# STEP 2
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8001