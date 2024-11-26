FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN mvn clean install -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/back-end-0.0.1-SNAPSHOT.jar"]