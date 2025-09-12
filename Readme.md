# ArponPosSystem API Documentation

## User Management APIs

This document provides detailed information about the user-related APIs in the ArponPosSystem application.

### Base URL
```
http://localhost:8080
```

---

## Authentication APIs

### 1. User Registration (Signup)

**Endpoint:** `POST /auth/signup`

**Description:** Register a new user account

**Request Body:**
```json
{
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "phone": "1234567890",
  "password": "password123",
  "role": "ROLE_USER"
}
```

**Response:**
```json
{
  "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "Registration successful",
  "user": {
    "id": 1,
    "fullName": "John Doe",
    "email": "john.doe@example.com",
    "phone": "1234567890",
    "role": "ROLE_USER",
    "createdAt": "2025-09-12T10:30:00",
    "updatedAt": "2025-09-12T10:30:00",
    "lastLogin": null
  }
}
```

**Status Codes:**
- `200 OK` - Registration successful
- `400 Bad Request` - Invalid input data
- `409 Conflict` - User already exists

---

### 2. User Login

**Endpoint:** `POST /auth/login`

**Description:** Authenticate user and get JWT token

**Request Body:**
```json
{
  "email": "john.doe@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "Login successful",
  "user": {
    "id": 1,
    "fullName": "John Doe",
    "email": "john.doe@example.com",
    "phone": "1234567890",
    "role": "ROLE_USER",
    "createdAt": "2025-09-12T10:30:00",
    "updatedAt": "2025-09-12T10:30:00",
    "lastLogin": "2025-09-12T11:00:00"
  }
}
```

**Status Codes:**
- `200 OK` - Login successful
- `401 Unauthorized` - Invalid credentials
- `404 Not Found` - User not found

---

## User Management APIs

### 3. Get User Profile

**Endpoint:** `GET /api/users/profile`

**Description:** Get current user's profile information

**Headers:**
```
Authorization: Bearer <JWT_TOKEN>
```

**Response:**
```json
{
  "id": 1,
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "phone": "1234567890",
  "role": "ROLE_USER",
  "createdAt": "2025-09-12T10:30:00",
  "updatedAt": "2025-09-12T10:30:00",
  "lastLogin": "2025-09-12T11:00:00"
}
```

**Status Codes:**
- `200 OK` - Profile retrieved successfully
- `401 Unauthorized` - Invalid or missing JWT token
- `404 Not Found` - User not found

---

### 4. Get User by ID

**Endpoint:** `GET /api/users/{id}`

**Description:** Get user information by user ID

**Path Parameters:**
- `id` (Long) - User ID

**Headers:**
```
Authorization: Bearer <JWT_TOKEN>
```

**Example Request:**
```
GET /api/users/1
```

**Response:**
```json
{
  "id": 1,
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "phone": "1234567890",
  "role": "ROLE_USER",
  "createdAt": "2025-09-12T10:30:00",
  "updatedAt": "2025-09-12T10:30:00",
  "lastLogin": "2025-09-12T11:00:00"
}
```

**Status Codes:**
- `200 OK` - User retrieved successfully
- `401 Unauthorized` - Invalid or missing JWT token
- `404 Not Found` - User not found

---

## User Roles

The system supports the following user roles:

| Role | Description |
|------|-------------|
| `ROLE_USER` | Standard user with basic permissions |
| `ROLE_ADMIN` | Administrator with full system access |
| `ROLE_BRAND_MANAGER` | Brand manager with brand-specific permissions |
| `ROLE_STORE_MANAGER` | Store manager with store-specific permissions |

---

## Data Models

### UserDto

```json
{
  "id": "Long - User ID (auto-generated)",
  "fullName": "String - Full name of the user",
  "email": "String - Email address (unique)",
  "phone": "String - Phone number",
  "password": "String - Password (only for requests, not in responses)",
  "role": "UserRole - User role (ROLE_USER, ROLE_ADMIN, etc.)",
  "createdAt": "LocalDateTime - Account creation timestamp",
  "updatedAt": "LocalDateTime - Last update timestamp",
  "lastLogin": "LocalDateTime - Last login timestamp"
}
```

### AuthResponse

```json
{
  "jwt": "String - JWT authentication token",
  "message": "String - Response message",
  "user": "UserDto - User information"
}
```

---

## Authentication

Most endpoints require authentication using JWT tokens:

1. Include the JWT token in the `Authorization` header
2. Format: `Authorization: Bearer <JWT_TOKEN>`
3. Obtain JWT token through the login endpoint
4. Token expires after a certain period (check with system administrator)

---

## Error Handling

### Common Error Responses

**400 Bad Request:**
```json
{
  "message": "Invalid input data",
  "status": 400,
  "timestamp": 1694518800000
}
```

**401 Unauthorized:**
```json
{
  "message": "Invalid or expired token",
  "status": 401,
  "timestamp": 1694518800000
}
```

**404 Not Found:**
```json
{
  "message": "User not found",
  "status": 404,
  "timestamp": 1694518800000
}
```

**409 Conflict:**
```json
{
  "message": "User already exists",
  "status": 409,
  "timestamp": 1694518800000
}
```

---

## Example Usage

### JavaScript/Fetch API

```javascript
// Login
const loginResponse = await fetch('/auth/login', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({
    email: 'john.doe@example.com',
    password: 'password123'
  })
});

const loginData = await loginResponse.json();
const token = loginData.jwt;

// Get user profile
const profileResponse = await fetch('/api/users/profile', {
  headers: {
    'Authorization': `Bearer ${token}`
  }
});

const userProfile = await profileResponse.json();
```

### cURL Examples

**Register a new user:**
```bash
curl -X POST http://localhost:8080/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "John Doe",
    "email": "john.doe@example.com",
    "phone": "1234567890",
    "password": "password123",
    "role": "ROLE_USER"
  }'
```

**Login:**
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john.doe@example.com",
    "password": "password123"
  }'
```

**Get user profile:**
```bash
curl -X GET http://localhost:8080/api/users/profile \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**Get user by ID:**
```bash
curl -X GET http://localhost:8080/api/users/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

---

## Notes

- All timestamps are in ISO 8601 format
- Passwords are not returned in API responses for security reasons
- Email addresses must be unique across the system
- JWT tokens should be stored securely on the client side
- Always use HTTPS in production environments
