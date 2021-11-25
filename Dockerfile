FROM openjdk:8-jre-alpine
COPY  --from=MAVEN_ENV /target/ehr_Api-*.jar ehr_Api.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
