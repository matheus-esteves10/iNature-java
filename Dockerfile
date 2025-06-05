FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine-3.21
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
WORKDIR /myapp

ENV DB_FIAP_URL=""
ENV DB_FIAP_USERNAME=""
ENV DB_FIAP_PASSWORD=""
ENV SUPABASE_BUCKET_APIKEY=""
ENV JWT_SECRET=""

COPY --from=build /app/target/*.jar app.jar
RUN chown -R appuser:appgroup /myapp
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

#docker build -t inature-server:1.0 .
#docker run --env-file .env -p 8080:8080 inature-server:1.0
#docker login
#docker tag inature-server:1.0 matheusesteves10/inature-server:1.0
#docker push matheusesteves10/inature-server:1.0