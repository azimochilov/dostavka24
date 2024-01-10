# Build Stage
FROM openjdk:17 AS builder
WORKDIR /app
COPY . /app/.
RUN gradle clean build -x test

#Final Stage
FROM openjdk:17
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
