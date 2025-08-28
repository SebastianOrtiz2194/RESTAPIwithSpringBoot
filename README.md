##Spring Boot REST API

This project is a simple but robust RESTful API built with Spring Boot. It demonstrates a complete user management system that follows modern best practices, including a service layer, Data Transfer Objects (DTOs), validation, and global exception handling.

Features
User Management: Complete CRUD (Create, Read, Update, Delete) operations for a User entity.

Data Validation: Utilizes Jakarta Bean Validation to ensure incoming data meets specified criteria.

Global Exception Handling: Provides a structured and consistent way to handle errors and return appropriate HTTP status codes (e.g., 404 Not Found, 400 Bad Request).

Service Layer: Separates business logic from the controller, making the code more modular, testable, and maintainable.

Data Transfer Objects (DTOs): Uses a UserDTO to decouple the API contract from the internal data model.

Unit Testing: Includes both unit tests for the service layer and integration tests for the controller.
