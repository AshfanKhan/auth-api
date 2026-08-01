# Auth API

## Overview
The **Auth API** is a secure authentication and authorization service designed to handle user login, registration, and role-based access control. This project is built with Java and Spring Boot, ensuring scalability and maintainability.

## Core Features
- **Authentication**: All incoming requests are authenticated except for the `/api/auth/login` and `/api/auth/register` endpoints.
- **User Roles**: Currently, the system supports a single role, `ROLE_USER`. Future releases will introduce multiple roles to enhance authorization capabilities.
- **Secure JWT Implementation**: The API uses JSON Web Tokens (JWT) for secure and stateless authentication.
- **User Management**: Provides endpoints for user registration and login.

## Endpoints
### Public Endpoints
These endpoints do not require authentication:
- `POST /api/auth/login`: Authenticate a user and generate a JWT.
- `POST /api/auth/register`: Register a new user.

### Protected Endpoints
All other endpoints require authentication and a valid JWT.

## Future Plans
- **Role-Based Access Control**: Extend functionality to support multiple roles (e.g., `ROLE_ADMIN`, `ROLE_MANAGER`) for better authorization.
- **Enhanced Security**: Implement additional security measures such as account lockout after multiple failed login attempts.
- **Audit Logging**: Add logging for critical actions like login, registration, and role changes.

## Getting Started
### Prerequisites
- Java 17 or higher
- Maven

### Installation
1. Clone the repository:
   ```
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```
   cd auth-api
   ```
3. Build the project:
   ```
   mvn clean install
   ```
4. Run the application:
   ```
   mvn spring-boot:run
   ```

### Configuration
Update the `application.properties` file in the `src/main/resources` directory to configure database and security settings.

## Contributing
Contributions are welcome! Please fork the repository and create a pull request for any enhancements or bug fixes.

## Contact
For any inquiries or support, please contact the repository owner.
