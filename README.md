# Spring Boot REST API Demo

A RESTful API built with Spring Boot demonstrating user authentication and CRUD operations.

## Features

- User signup and login with JWT authentication
- Bearer token-based secure endpoints
- Full CRUD operations (Create, Read, Update, Delete)
- MySQL database integration
- Exception handling for authentication flows

## Tech Stack

- Java 25
- Spring Boot 3.x
- Spring Security
- JWT (JSON Web Tokens)
- MySQL
- Maven

## Current Status

Implemented: User signup, login, JWT token generation, CRUD endpoints  
In Progress: Role-based access control, comprehensive input validation, unit tests

## API Endpoints

### Authentication
- `POST /api/v1/auth/register` - Register new user
- `POST /api/v1/auth/login` - Login and receive JWT token

### CRUD Operations (requires authentication)
- `POST /api/v1/user/addUser` - Create
- `GET /api/v1/user/getUserById/{id}` - Read
- `PUT /api/v1/user/getUserById//{id}` - Update
- `DELETE /api/v1/user/deleteUser` - Delete

## Setup

1. Clone the repository
2. Configure MySQL database in `application.properties`
3. Run `mvn spring-boot:run`
4. API will be available at `http://localhost:8080`

## Testing

Use Postman or cURL to test endpoints. Include JWT token in Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

## Future Enhancements

- Deploy to AWS EC2
- Add comprehensive unit tests (JUnit + Mockito)
- Implement role-based access control (RBAC)
- Add input validation across all endpoints
- Implement refresh token mechanism

## Author

Dilangi Thilakarathna  
[LinkedIn](https://www.linkedin.com/in/dilangi-thilakarathne)` 
