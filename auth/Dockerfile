FROM eclipse-temurin:21-jdk-jammy AS build
LABEL author="kishore"

WORKDIR /auth

COPY mvnw pom.xml ./
COPY .mvn/ .mvn
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
WORKDIR /auth

COPY --from=build /auth/target/*.jar auth.jar

EXPOSE 8082

ENTRYPOINT ["java","-XX:+UseContainerSupport","-jar","auth.jar"]