FROM openjdk:17
RUN mkdir /usr/src/taskmanager
WORKDIR /usr/src/taskmanager
COPY build/libs/task-manager-*.jar task-manager.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "task-manager.jar"]
