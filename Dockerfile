FROM maven:3.9.5-eclipse-temurin-21-alpine as builder
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
WORKDIR /usr/src/app
RUN if [ -f src/main/resources/config/application.yml ]; then rm src/main/resources/config/application.yml; fi
RUN mvn clean package -Dmaven.test.skip=true

FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY --from=builder /usr/src/app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
