FROM maven:3.9.6-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy
COPY --from=build /target/quizzapp-0.0.1-SNAPSHOT.jar quizzapp.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","quizzapp.jar"]