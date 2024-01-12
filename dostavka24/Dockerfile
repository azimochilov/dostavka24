# Build Stage
FROM gradle:7.5.1-jdk17 AS builder
WORKDIR /app
COPY . /app/.
RUN gradle build -x test

#Final Stage
FROM openjdk:17-oracle
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar /app/*.jar
COPY --from=builder /app/.env /app/.env
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/*.jar"]
