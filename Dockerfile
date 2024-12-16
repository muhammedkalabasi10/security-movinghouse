FROM maven:3.9.6-amazoncorretto-21-al2023 AS builder
# Set the working directory in the container
WORKDIR /app
# Copy the pom.xml and the project files to the container
COPY pom.xml .
COPY src ./src
# Build the application using Maven
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
COPY --from=builder /app/target/*.jar /app/securitymovinghouse.jar
ENTRYPOINT ["java", "-jar", "/app/securitymovinghouse.jar"]