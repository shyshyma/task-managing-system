FROM openjdk:17
RUN mkdir /usr/src/audit
WORKDIR /usr/src/audit
COPY build/libs/audit-*.jar audit.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "audit.jar"]
