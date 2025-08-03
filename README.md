# Student Service with Spring Boot and Docker

A RESTful microservice for managing student records using Spring Boot and PostgreSQL, containerized with Docker.

## Technology Stack

- Java 17
- Spring Boot
- PostgreSQL
- Docker
- Maven
- JPA/Hibernate

## Prerequisites

To run this application, you need:

- Docker & Docker Compose
- JDK 17 (for local development)
- Maven (for local development)

## Project Structure

```
students-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/majeed/students/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       └── StudentsServiceApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql
├── docker-compose.yml
├── Dockerfile
└── pom.xml
```

## Building and Running

### Local Development

1. Build the application:

   ```bash
   mvn clean package
   ```

2. Run locally:
   ```bash
   mvn spring-boot:run
   ```

### Running with Docker

1. Build and start the containers:

   ```bash
   docker-compose up --build
   ```

2. Stop the containers:
   ```bash
   docker-compose down
   ```

## Docker Configuration

### Dockerfile

The application is containerized using a multi-stage build:

```dockerfile
FROM openjdk:17-slim
ADD student-service.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "student-service.jar"]
```

### Docker Compose

The `docker-compose.yml` file sets up two services:

1. **app**: The Spring Boot application

   - Builds from local Dockerfile
   - Exposes port 8080
   - Connected to custom network

2. **postgres**: PostgreSQL database
   - Uses latest PostgreSQL image
   - Configured with custom credentials
   - Exposes port 5432
   - Connected to custom network

## Database Configuration

PostgreSQL database is configured with:

- Database Name: `student_db`
- Username: `root`
- Password: `root123`
- Port: `5432`

Spring Boot is configured to:

- Create tables automatically
- Initialize data using `data.sql`
- Show SQL queries in logs

## Application Properties

Key application properties:

```properties
server.port=8080
spring.datasource.url=jdbc:postgresql://postgres:5432/student_db
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Note: The database host in the JDBC URL is `postgres`, which is the service name in docker-compose.

## Network Configuration

A custom bridge network `s-network` is created to enable communication between the application and database containers.

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## License

This project is licensed under the MIT License.
