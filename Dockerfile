FROM openjdk:17
RUN mkdir /usr/src/taskmanager
WORKDIR /usr/src/taskmanager
COPY build/libs/taskmanager-*-RELEASE.jar taskmanager.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "taskmanager.jar"]
