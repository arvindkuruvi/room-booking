# Use a Gradle image to build the application
FROM gradle:8.10.2-jdk17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle build files and source code
COPY build.gradle settings.gradle ./
COPY src ./src

# Build the project and create the JAR file
RUN gradle clean build -x test

# Use a smaller JDK image to run the JAR file
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/user-service.jar user-service.jar

# Expose the port your application will run on
EXPOSE 8080

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "user-service.jar"]