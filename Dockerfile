# Step 1: Build using Maven and Eclipse Temurin Java 17
FROM maven:3.8.5-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run using a supported lightweight JRE (Runtime)
FROM eclipse-temurin:17-jre
COPY --from=build /target/*.jar app.jar
EXPOSE 8080

# Optimization for Koyeb's Free Tier (256MB RAM)
ENTRYPOINT ["java","-Xmx128m","-jar","/app.jar"]