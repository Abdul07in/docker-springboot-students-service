FROM openjdk:17-slim
ADD target/student-service.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "student-service.jar"]