# Spring Boot Microservices

RESTful APIs built with Spring Boot demonstrating user authentication and CRUD operations.

## Features

- User signup and login with JWT authentication
- Bearer token-based secure endpoints
- Full CRUD operations (Create, Read, Update, Delete)
- MySQL database integration
- Exception handling and input validation
- Unit tests with Mockito

## Tech Stack

- Java 25
- Spring Boot
- Spring Security (JWT Authentication)
- Spring Data JPA (Hibernate)
- MySQL (local), AWS RDS (planned)
- Maven
- JUnit 5 & Mockito

## Prerequisites
- JDK 17+
- MySQL 8.0+
- Maven 3.6+

## Setup Instructions

1. Clone the repository
```bash
git clone https://github.com/Dilangi/Spring-Boot-Demo.git
cd spring-boot-demo
```

2. Configure database
```bash
# Create database
mysql -u root -p
```

3. Update application.properties
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/springboot_demo
spring.datasource.username=your_username
spring.datasource.password=your_password
```

4. Run the application
```bash
mvn spring-boot:run
```
## Common Issues

- MySQL connection error → ensure MySQL is running
- JWT Unauthorized → check Authorization header format

## API Endpoints available at `http://localhost:4999`

### Authentication
- `POST /api/v1/auth/register` - Register new user
- `POST /api/v1/auth/login` - Login and receive JWT token

### CRUD Operation Samples (requires authentication)
- `POST /api/v1/user/addUser` - Create
- `GET /api/v1/user/getUsers` - Read
- `PUT /api/v1/user/updateUser/{id}` - Update
- `DELETE /api/v1/user/deleteUser/{id}` - Delete

## Example Usage

### Signup
```bash
curl -X POST http://localhost:4999/api/signup \
  -H "Content-Type: application/json" \
  -d '{"username":"user","password":"password123"}'
```

### Login
```bash
curl -X POST http://localhost:4999/api/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user","password":"password123"}'
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Access Protected Endpoint
```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

Use Postman or cURL to test endpoints. Include JWT token in Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

## Testing
```bash
Run all tests:
mvn test

Run a single test:
mvn -Dtest=AccountServiceTest test
```

## Current Status

Implemented: User signup, login, JWT token generation, CRUD endpoints, unit tests (for service)
In Progress: Role-based access control, comprehensive input validation, improve unit test coverage

## Future Enhancements

- Deploy to AWS EC2
- Implement role-based access control (RBAC)
- Add input validation across all endpoints
- Implement refresh token mechanism

## Author

Dilangi Thilakarathna  
[LinkedIn](https://www.linkedin.com/in/dilangi-thilakarathne)