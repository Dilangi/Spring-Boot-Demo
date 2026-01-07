# Spring Boot Microservices

A secure RESTful API built with Spring Boot featuring JWT-based authentication, deployed to AWS cloud infrastructure.

## Features

- User signup with secure password storage (BCrypt)
- User login with JWT token generation
- Token-based authentication middleware
- AWS deployment: EC2 instance + RDS MySQL database
- Full CRUD operations (Create, Read, Update, Delete)
- Exception handling and input validation
- Unit tests with Mockito

## Architecture
- **Backend:** Spring Boot 3.x, Spring Security, Java 25
- **Database:** MySQL 8.0 on AWS RDS
- **Deployment:** AWS EC2 (t3.micro), RDS (db.t3.micro)
- **Security:** JWT tokens, BCrypt password hashing, Spring Security
- **Testing:** JUnit 5 & Mockito

## Setup Instructions: 

## Option 1. Run Locally (Localhost):

### Prerequisites
- JDK 17+
- MySQL 8.0+
- Maven 3.6+
- Git

1. Clone the repository
```bash
git clone https://github.com/Dilangi/Spring-Boot-Demo.git
cd spring-boot-demo
```

2. Configure local database
```bash
# Create database
mysql -u root -p
CREATE DATABASE springboot_demo;
```

3. Update application.properties
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/springboot_demo
spring.datasource.username=<YOUR_USERNAME>
spring.datasource.password=<YOUR_PASSWORD>

server.port=4999
```

4. Build the project
```bash
mvn clean package
```

5. Run the application
```bash
mvn spring-boot:run
```
### API Endpoints available at `http://localhost:4999`

### Common Issues

- MySQL connection error → ensure MySQL is running
- JWT Unauthorized → check Authorization header format


## Option 2. Run on AWS (Localhost)

### Prerequisites
- AWS account
- EC2 instance (Amazon Linux)
- RDS MySQL database
- EC2 and RDS in the same VPC
- Port 4999 open on EC2
- Port 3306 open from EC2 → RDS

1. Connect to EC2
```bash
cd <KEY_FILE_LOCATION>
ssh -i <YOUR-KEY.pem> ec2-user@<EC2_PUBLIC_IP>
```

2. Install Java & Maven
```bash
sudo dnf install -y java-17-amazon-corretto maven
```
Verify
```bash
java -version
mvn -version
```

3. Clone and build project
```bash
git clone https://github.com/Dilangi/Spring-Boot-Demo.git
cd Spring-Boot-Demo/demo
mvn clean package 
```

4. Update application.properties for AWS
```bash
spring.datasource.url=jdbc:mysql://<RDS-ENDPOINT>:3306/demo_db
spring.datasource.username=<RDS_USERNAME>
spring.datasource.password=<RDS_PASSWORD>

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

server.port=4999
server.address=0.0.0.0 
```

5. Run application on EC2
```bash
java -jar target/spring-boot-demo-0.0.1-SNAPSHOT.jar
```

### API Endpoints available at `http://<EC2_PUBLIC_IP>:4999`

### Common Issues

- API Not Reachable (ECONNREFUSED) → Ensure inbound rule in EC2 Security Group are correct
- Unknown Database Error → Ensure Database schema is created in RDS

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

- Fully implemented RESTful API with JWT-based authentication and authorization
- Secure user authentication using Spring Security and BCrypt
- Deployed and tested in a cloud environment using:
- AWS EC2 for application hosting
- AWS RDS (MySQL) for persistent data storage
- VPC & Security Groups for controlled network access
- Includes CRUD operations, input validation, and structured error handling
- Unit-tested using JUnit and Mockito to ensure reliability and maintainability

🚧 Deployment Availability
- The application has been successfully deployed and verified on AWS
- To minimize ongoing cloud costs, the live environment is currently stopped
- The application can be redeployed on demand within minutes

## Future Enhancements

- Role-based access control
- Implement role-based access control (RBAC)
- Add input validation across all endpoints
- Implement refresh token mechanism

## Author

Dilangi Thilakarathna  
[LinkedIn](https://www.linkedin.com/in/dilangi-thilakarathne)