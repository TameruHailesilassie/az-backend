FROM openjdk:8-jre
ARG PROFILE
ENV PROFILE_VAR=$PROFILE
VOLUME /tmp
# Add the built jar for docker image building
ADD target/ehr_Apii.jar ehr_Api-action.jar
ENTRYPOINT ["/bin/bash", "-c", "java","-Dspring.profiles.active=$PROFILE_VAR","-jar","/ehr_Api-action.jar"]
# DO NOT USE(The variable would not be substituted): ENTRYPOINT ["java","-Dspring.profiles.active=$PROFILE_VAR","-jar","/hello-world-docker-action.jar"]
# CAN ALSO USE: ENTRYPOINT java -Dspring.profiles.active=$PROFILE_VAR -jar /hello-world-docker-action.jar
EXPOSE 80