FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

#docker build -t inature-server:1.0 .
#docker run --env-file .env -p 8080:8080 inature-server:1.0
#docker login
#docker tag inature-server:1.0 matheusesteves10/inature-server:1.0
#docker push matheusesteves10/inature-server:1.0