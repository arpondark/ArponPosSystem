# Auth Controller API Documentation

## Overview
The Auth Controller handles authentication operations including user registration, admin registration, and user login.

**Base URL:** `/auth`

---

## Endpoints

### 1. User Signup
Register a new user account.

**Endpoint:** `POST /auth/signup`

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "password": "SecurePassword123",
  "phone": "+1234567890",
  "role": "ROLE_STORE_ADMIN"
}
```

**Available Roles:**
- `ROLE_STORE_ADMIN`
- `ROLE_STORE_MANAGER`
- `ROLE_STORE_EMPLOYEE`

**Example Response:**
```json
{
  "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "User Registered Successfully",
  "user": {
    "id": 1,
    "fullName": "John Doe",
    "email": "john.doe@example.com",
    "phone": "+1234567890",
    "role": "ROLE_STORE_ADMIN"
  }
}
```

**Status Codes:**
- `200 OK` - User registered successfully
- `400 Bad Request` - Email already exists or cannot register as admin

---

### 2. Admin Signup
Register a new admin user account (requires special privileges).

**Endpoint:** `POST /auth/signup-admin`

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "fullName": "Admin User",
  "email": "admin@example.com",
  "password": "AdminPassword123",
  "phone": "+1234567890"
}
```

**Note:** The role will be automatically set to `ROLE_ADMIN` regardless of the role in the request body.

**Example Response:**
```json
{
  "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "Admin User Registered Successfully",
  "user": {
    "id": 2,
    "fullName": "Admin User",
    "email": "admin@example.com",
    "phone": "+1234567890",
    "role": "ROLE_ADMIN"
  }
}
```

**Status Codes:**
- `200 OK` - Admin registered successfully
- `400 Bad Request` - Email already exists

---

### 3. Login
Authenticate a user and receive a JWT token.

**Endpoint:** `POST /auth/login`

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "email": "john.doe@example.com",
  "password": "SecurePassword123"
}
```

**Example Response:**
```json
{
  "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "User Login Successfully",
  "user": {
    "id": 1,
    "fullName": "John Doe",
    "email": "john.doe@example.com",
    "phone": "+1234567890",
    "role": "ROLE_STORE_ADMIN",
    "lastLogin": "2025-09-13T10:30:00"
  }
}
```

**Status Codes:**
- `200 OK` - Login successful
- `400 Bad Request` - Invalid credentials

---

## Postman Testing Instructions

### Step 1: Setup Environment
1. Create a new Postman environment
2. Add variable: `baseUrl` = `http://localhost:8080`
3. Add variable: `authToken` (will be set automatically after login)

### Step 2: Test User Registration
1. Create a new POST request
2. URL: `{{baseUrl}}/auth/signup`
3. Headers: `Content-Type: application/json`
4. Body: Raw JSON (use the signup example above)
5. Send request
6. Save the JWT token from response to `authToken` variable

### Step 3: Test Login
1. Create a new POST request
2. URL: `{{baseUrl}}/auth/login`
3. Headers: `Content-Type: application/json`
4. Body: Raw JSON (use the login example above)
5. Send request
6. Copy JWT token from response

### Step 4: Test Admin Registration (Optional)
1. Create a new POST request
2. URL: `{{baseUrl}}/auth/signup-admin`
3. Headers: `Content-Type: application/json`
4. Body: Raw JSON (use the admin signup example above)
5. Send request

### Step 5: Save JWT Token for Future Requests
After successful login, copy the JWT token from the response and:
1. Go to Postman Environment variables
2. Set `authToken` = `Bearer YOUR_JWT_TOKEN_HERE`
3. Use `{{authToken}}` in Authorization headers for protected endpoints

---

## Error Handling

### Common Error Responses:
```json
{
  "error": "User Not Found",
  "message": "Invalid credentials",
  "status": 400
}
```

```json
{
  "error": "Email Already Exist",
  "message": "User with this email already exists",
  "status": 400
}
```

### Troubleshooting:
- **Email already exists**: Use a different email address
- **Invalid credentials**: Check email and password
- **Validation errors**: Ensure all required fields are provided
- **Server errors**: Check if the application is running on the correct port

---

## Notes
- JWT tokens are required for accessing protected endpoints in other controllers
- Tokens expire after a certain period (check your JWT configuration)
- Store the JWT token securely for subsequent API calls
- The password should be strong and meet security requirements
