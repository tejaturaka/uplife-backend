# Step 1: Use Maven to build the application
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Use a lightweight Java image to run it
FROM openjdk:17-jdk-slim
COPY --from=build /target/*.jar app.jar
EXPOSE 8080

# Optimization for Koyeb's Free Tier (256MB RAM)
ENTRYPOINT ["java","-Xmx128m","-jar","/app.jar"]