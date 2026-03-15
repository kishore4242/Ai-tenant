FROM eclipse-temurin:21-jdk-jammy AS build
LABEL author="kishore"

WORKDIR /web-service

COPY mvnw pom.xml ./
COPY .mvn/ .mvn
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
WORKDIR /web-service

COPY --from=build /web-service/target/*.jar web-service.jar

EXPOSE 8083

ENTRYPOINT ["java","-Xms1g","-Xmx3g","-XX:+UseG1GC","-XX:+UseContainerSupport","-jar","web-service.jar"]