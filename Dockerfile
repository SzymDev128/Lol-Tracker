FROM maven:3.9-eclipse-temurin-21

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests

CMD ["java", "-jar", "target/loltracker-0.0.1-SNAPSHOT.jar"]
