FROM openjdk:17-slim
ADD student-service.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "student-service.jar"]