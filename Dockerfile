FROM openjdk:11
ADD target/pizzeriapi.jar pizzeriapi.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "pizzeriapi.jar"]