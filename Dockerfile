# --- Stage 1: Build compilation container using Gradle ---
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# Copy only build configs first (Leverages caching for dependencies)
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon || true
COPY src ./src

# Compile the source and assemble the final executable jar file
RUN ./gradlew bootJar --no-daemon -x test

# --- Stage 2: Ultra-lightweight production execution container ---
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the compiled executable JAR file from Stage 1
COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]