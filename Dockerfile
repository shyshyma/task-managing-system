FROM openjdk:17
RUN mkdir /usr/src/taskmanager
WORKDIR /usr/src/taskmanager
COPY build/libs/taskmanager-1.0.war .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "taskmanager-1.0.war"]
