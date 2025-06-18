# 🏡 ChâTop – Spring Boot REST API

This is the **backend API** for the ChâTop platform – a seasonal rental listing service. It handles user authentication with JWT, rental management, and messaging features. Built with **_Spring Boot_** and connected to a **_MySQL database._**

## 🛠️ Stack & Prerequisites

- **Java 17** (also compatible with Java 11)
- **Spring Boot 3.5.x**
  **MySQL 8+**
- **JWT** (for user authentication)
- **Swagger (OpenAPI)** for live API documentation
- **Postman / Mockoon** for API testing

## 📁 Project Structure

```
src/main/java/com/chaptoporg/
├── controller/ # REST controllers
├── dto/ # Request & Response DTOs
├── exception/ # Custom exceptions
├── model/ # JPA Entities
├── repo/ # Spring Data JPA repositories
├── service/ # Business logic layer
├── security/ # JWT filter
├── config/ # Security and OpenAPI config
```

## 🔧 Installation Instructions

### 1. **Clone the repo**

```bash
git clone https://github.com/your-user/chapTop.git
cd chapTop
```

### 2. Configure the database

```sql
CREATE DATABASE chatop;
```

- In application.properties, adjust these values if needed:

```
spring.datasource.url=jdbc:mysql://localhost:3306/chatop
spring.datasource.username=admin
spring.datasource.password=admin
```

### 3. Build and run the project

```bash
./mvnw clean install
./mvnw spring-boot:run
```

The API will run at: `http://localhost:3000`

## 🔐 JWT Authentication

### Register

`POST /api/auth/register`

- Request body:

```json
{
	"email": "test@example.com",
	"password": "abc123",
	"name": "John Doe"
}
```

### Login

`POST /api/auth/login`

- Request Body

```json
{
	"email": "test@example.com",
	"password": "abc123"
}
```

✅ A valid JWT token will be returned.

## 🧪 Testing Endpoints

### 🔸 Swagger UI

Live, interactive API docs available at:

```bash
http://localhost:3000/swagger-ui.html
```

- ➡ To access secured endpoints:

1. Click “Authorize” at the top.
2. Paste your JWT token in this format:

```
Bearer eyJhbGciOiJIUzI1NiJ9...
```

3. Click Authorize, then test secured routes like `/api/auth/me`

## 🧰 Tools Recommended

- **_Postman:_** Send API requests manually
- **_Mockoon:_** Simulate endpoints if needed
- **_phpMyAdmin:_** View & edit MySQL data easily


## 🖼️ Image Uploads for Rentals

This project supports uploading an image file when creating a new rental.

- Endpoint:
```bash
POST /api/rentals
Content-Type: multipart/form-data
```

- Required form fields:
  - name (String)
  - surface (Integer)
  - price (Integer)
  - description (String)
  - ownerId (Integer)
  - picture (Image file)   

- Uploaded images are:
  - Renamed with a UUID prefix to avoid filename collisions.
  - Stored locally in the /uploads directory relative to the project root.
  - Served statically at the path:
`http://localhost:3000/uploads/{filename}`

The directory is created automatically at runtime. Make sure the application has write permission to the project root.


## ⚠️ Notes

- The default role assigned to a user is ROLE_USER. Roles are currently static in the JwtAuthFilter.java.

- spring.jpa.hibernate.ddl-auto=update will create or update tables on app start. Disable this in production.

- Uploaded files are stored in the uploads/ directory (configurable).

## 🧩 Swagger / OpenAPI

The project uses springdoc-openapi:

![Tux, the Linux mascot](/swagger.png)

```xml
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.8.9</version>
</dependency>
```

Security is configured with a bearer token scheme so Swagger can send the token automatically after clicking "Authorize".

## 💬 Contact

For questions or improvements, feel free to open an issue or fork the project.

✅ This project is a backend only — intended to be used with an Angular or other frontend client.
