FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

#RUN #./mvnw clean package -DskipTests

#FROM eclipse-temurin:17-jre
#WORKDIR /app
#COPY --from=build /app/target/*.jar app.jar

#ENV _JAVA_OPTIONS="-Xmx512m -Xms256m"
#ENV SPRING_PROFILES_ACTIVE=prod,api-docs

#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]
