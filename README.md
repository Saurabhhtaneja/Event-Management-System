# 🎟️ Event Management System

A secure and scalable **Event Management REST API** built using **Spring Boot**, designed to simplify event creation, user authentication, and event registrations.

The application follows a layered backend architecture and uses **JWT-based authentication**, **Spring Security**, **Spring Data JPA**, and **MySQL** to provide secure and efficient event management functionality.

---

## 🚀 Features

### 🔐 Authentication & Security

* User registration and login
* JWT-based authentication
* Spring Security integration
* Password encryption
* Protected API endpoints
* Stateless authentication

### 📅 Event Management

* Create new events
* Retrieve available events
* Retrieve event details
* Update event information
* Delete events
* Manage event data through RESTful APIs

### 🎫 Event Registration

* Register users for events
* Manage event registrations
* Store registration information in MySQL
* Associate users with event registrations

### ⚙️ Backend Architecture

* RESTful API design
* Layered architecture
* DTO-based request/response handling
* Repository pattern using Spring Data JPA
* Centralized exception handling
* Input validation
* Database persistence using Hibernate/JPA

---

## 🛠️ Tech Stack

| Technology             | Purpose                              |
| ---------------------- | ------------------------------------ |
| **Java 21**            | Backend programming language         |
| **Spring Boot 3.3.5**  | Application framework                |
| **Spring Web**         | REST API development                 |
| **Spring Security**    | Authentication and authorization     |
| **JWT (JJWT)**         | Token-based authentication           |
| **Spring Data JPA**    | Database persistence                 |
| **Hibernate**          | ORM                                  |
| **MySQL**              | Relational database                  |
| **Jakarta Validation** | Request validation                   |
| **Lombok**             | Reduce boilerplate code              |
| **Maven**              | Dependency management and build tool |

---

## 🏗️ Project Architecture

The project follows a layered architecture to maintain separation of concerns and improve maintainability.

```text
Client
   │
   ▼
Controller Layer
   │
   ▼
Service Layer
   │
   ▼
Repository Layer
   │
   ▼
MySQL Database
```

Authentication requests are validated using Spring Security and JWT before protected resources are accessed.

---

## 📂 Project Structure

```text
eventmngmt
│
├── config/
│   └── Security and application configuration
│
├── controller/
│   ├── AuthController.java
│   ├── EventController.java
│   └── RegistrationController.java
│
├── dto/
│   └── Data Transfer Objects
│
├── entity/
│   ├── User.java
│   ├── Event.java
│   └── Registration.java
│
├── exception/
│   └── Exception handling
│
├── repository/
│   └── Spring Data JPA repositories
│
├── security/
│   └── JWT authentication and security components
│
├── service/
│   └── Business logic
│
└── EventmngmtApplication.java
```

---

## 🔐 Authentication Flow

The application uses **JWT (JSON Web Token)** authentication.

```text
1. User registers an account
            │
            ▼
2. User logs in with credentials
            │
            ▼
3. Server validates credentials
            │
            ▼
4. JWT token is generated
            │
            ▼
5. Client sends JWT with protected requests
            │
            ▼
6. Spring Security validates the token
            │
            ▼
7. Access to protected resource is granted
```

For authenticated endpoints, include the JWT token in the request header:

```http
Authorization: Bearer <your-jwt-token>
```

---

## 🗄️ Database

The application uses **MySQL** as its relational database and **Spring Data JPA/Hibernate** for persistence.

Main entities include:

### User

Stores application user information and authentication-related data.

### Event

Stores information related to events.

### Registration

Represents registrations made by users for events.

The basic relationship can be represented as:

```text
USER
  │
  │ registers
  ▼
REGISTRATION
  │
  │ belongs to
  ▼
EVENT
```

---

## ⚙️ Getting Started

### Prerequisites

Make sure the following are installed:

* Java 21+
* MySQL
* Maven
* Git
* IntelliJ IDEA / Eclipse / VS Code
* Postman (recommended for API testing)

### 1. Clone the Repository

```bash
git clone https://github.com/Saurabhhtaneja/Event-Management-System.git
```

Move into the project directory:

```bash
cd Event-Management-System/eventmngmt-fixed/fixed
```

### 2. Create MySQL Database

Open MySQL and create a database:

```sql
CREATE DATABASE event_management;
```

### 3. Configure Database

Update `src/main/resources/application.properties` with your MySQL configuration.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/event_management
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> Do not commit real database passwords, JWT secrets, or other credentials to GitHub. Environment variables should be used for sensitive configuration in production.

### 4. Build the Application

```bash
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

Or run:

```text
EventmngmtApplication.java
```

from your IDE.

The backend will typically start at:

```text
http://localhost:8080
```

---

## 🧪 Testing the API

The APIs can be tested using tools such as **Postman**.

Typical workflow:

```text
Register User
     ↓
Login
     ↓
Receive JWT
     ↓
Add JWT to Authorization Header
     ↓
Access Protected APIs
     ↓
Create / Manage Events
     ↓
Register for Events
```

Example authorization header:

```http
Authorization: Bearer eyJhbGciOi...
```

---

## 🧠 Concepts Demonstrated

This project demonstrates practical implementation of important Java backend development concepts:

* REST API development
* Spring Boot application architecture
* Dependency Injection
* Spring Security
* JWT authentication
* Authentication & authorization
* Object Relational Mapping (ORM)
* Spring Data JPA repositories
* MySQL database integration
* DTO pattern
* Entity relationships
* Exception handling
* Request validation
* Maven dependency management
* Layered architecture

---

## 🔮 Future Improvements

Potential improvements include:

* Role-based authorization for Admin and User
* Event search and filtering
* Pagination and sorting
* Email notifications
* Event capacity management
* Registration cancellation
* Swagger / OpenAPI documentation
* Docker support
* Unit and integration tests
* Cloud deployment
* React frontend
* CI/CD pipeline

---

## 👨‍💻 Author

**Saurabh Taneja**

Java / Spring Boot Developer

GitHub: `Saurabhhtaneja`

---

## ⭐ Support

If you found this project useful or interesting, consider giving the repository a ⭐.

Contributions, suggestions, and feedback are welcome.
