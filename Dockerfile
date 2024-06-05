FROM maven:latest AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:latest

WORKDIR /app

COPY --from=build /app/target/desafio-celcoin-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "desafio-celcoin-0.0.1-SNAPSHOT.jar"]

