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
  "message": "User Registered Successfully",
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

**Note:** Cannot register as ROLE_ADMIN using this endpoint. Use `/auth/signup-admin` for admin registration.

---

### 2. Admin Registration (Signup Admin)

**Endpoint:** `POST /auth/signup-admin`

**Description:** Register a new admin user account

**Request Body:**
```json
{
  "fullName": "Admin User",
  "email": "admin@example.com",
  "phone": "1234567890",
  "password": "adminpassword123"
}
```

**Response:**
```json
{
  "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "Admin User Registered Successfully",
  "user": {
    "id": 1,
    "fullName": "Admin User",
    "email": "admin@example.com",
    "phone": "1234567890",
    "role": "ROLE_ADMIN",
    "createdAt": "2025-09-12T10:30:00",
    "updatedAt": "2025-09-12T10:30:00",
    "lastLogin": null
  }
}
```

**Status Codes:**
- `200 OK` - Admin registration successful
- `400 Bad Request` - Invalid input data
- `409 Conflict` - User already exists

**Note:** This endpoint automatically assigns ROLE_ADMIN regardless of the role field in the request body.

---

### 3. User Login

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

## Store Management APIs

This section provides detailed information about the store-related APIs in the ArponPosSystem application.

---

### 1. Create Store

**Endpoint:** `POST /api/stores`

**Description:** Create a new store (requires authentication)

**Headers:**
```
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
```

**Request Body:**
```json
{
  "brand": "SuperMart",
  "description": "A premium grocery store chain",
  "storeType": "GROCERY",
  "contract": {
    "address": "123 Main Street, Downtown, City 12345",
    "phone": "+1-555-0123",
    "email": "contact@supermart.com"
  }
}
```

**Response:**
```json
{
  "id": 1,
  "brand": "SuperMart",
  "description": "A premium grocery store chain",
  "storeType": "GROCERY",
  "status": "PENDING",
  "storeAdmin": {
    "id": 1,
    "fullName": "John Doe",
    "email": "john.doe@example.com",
    "phone": "1234567890",
    "role": "ROLE_ADMIN"
  },
  "contract": {
    "address": "123 Main Street, Downtown, City 12345",
    "phone": "+1-555-0123",
    "email": "contact@supermart.com"
  },
  "createdAt": "2025-09-12T10:30:00",
  "updatedAt": null
}
```

**Status Codes:**
- `200 OK` - Store created successfully
- `400 Bad Request` - Invalid input data
- `401 Unauthorized` - Invalid or missing JWT token

---

### 2. Get Store by ID

**Endpoint:** `GET /api/stores/{id}`

**Description:** Retrieve store information by store ID

**Path Parameters:**
- `id` (Long) - Store ID

**Example Request:**
```
GET /api/stores/1
```

**Response:**
```json
{
  "id": 1,
  "brand": "SuperMart",
  "description": "A premium grocery store chain",
  "storeType": "GROCERY",
  "status": "ACTIVE",
  "storeAdmin": {
    "id": 1,
    "fullName": "John Doe",
    "email": "john.doe@example.com",
    "phone": "1234567890",
    "role": "ROLE_ADMIN"
  },
  "contract": {
    "address": "123 Main Street, Downtown, City 12345",
    "phone": "+1-555-0123",
    "email": "contact@supermart.com"
  },
  "createdAt": "2025-09-12T10:30:00",
  "updatedAt": "2025-09-12T12:00:00"
}
```

**Status Codes:**
- `200 OK` - Store retrieved successfully
- `404 Not Found` - Store not found

---

### 3. Get All Stores

**Endpoint:** `GET /api/stores`

**Description:** Retrieve all stores in the system

**Response:**
```json
[
  {
    "id": 1,
    "brand": "SuperMart",
    "description": "A premium grocery store chain",
    "storeType": "GROCERY",
    "status": "ACTIVE",
    "storeAdmin": {
      "id": 1,
      "fullName": "John Doe",
      "email": "john.doe@example.com",
      "phone": "1234567890",
      "role": "ROLE_ADMIN"
    },
    "contract": {
      "address": "123 Main Street, Downtown, City 12345",
      "phone": "+1-555-0123",
      "email": "contact@supermart.com"
    },
    "createdAt": "2025-09-12T10:30:00",
    "updatedAt": "2025-09-12T12:00:00"
  }
]
```

**Status Codes:**
- `200 OK` - Stores retrieved successfully

---

### 4. Get Store by Admin

**Endpoint:** `GET /api/stores/admin`

**Description:** Get store managed by the authenticated admin

**Headers:**
```
Authorization: Bearer <JWT_TOKEN>
```

**Response:**
```json
{
  "id": 1,
  "brand": "SuperMart",
  "description": "A premium grocery store chain",
  "storeType": "GROCERY",
  "status": "ACTIVE",
  "storeAdmin": {
    "id": 1,
    "fullName": "John Doe",
    "email": "john.doe@example.com",
    "phone": "1234567890",
    "role": "ROLE_ADMIN"
  },
  "contract": {
    "address": "123 Main Street, Downtown, City 12345",
    "phone": "+1-555-0123",
    "email": "contact@supermart.com"
  },
  "createdAt": "2025-09-12T10:30:00",
  "updatedAt": "2025-09-12T12:00:00"
}
```

**Status Codes:**
- `200 OK` - Store retrieved successfully
- `401 Unauthorized` - Invalid or missing JWT token
- `404 Not Found` - Store not found for admin

---

### 5. Update Store

**Endpoint:** `PUT /api/stores/{id}`

**Description:** Update an existing store

**Path Parameters:**
- `id` (Long) - Store ID

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "brand": "SuperMart Premium",
  "description": "An upgraded premium grocery store chain",
  "storeType": "PREMIUM_GROCERY",
  "status": "ACTIVE",
  "contract": {
    "address": "456 Premium Plaza, Uptown District, City 54321",
    "phone": "+1-555-0456",
    "email": "premium@supermart.com"
  }
}
```

**Response:**
```json
{
  "id": 1,
  "brand": "SuperMart Premium",
  "description": "An upgraded premium grocery store chain",
  "storeType": "PREMIUM_GROCERY",
  "status": "ACTIVE",
  "storeAdmin": {
    "id": 1,
    "fullName": "John Doe",
    "email": "john.doe@example.com",
    "phone": "1234567890",
    "role": "ROLE_ADMIN"
  },
  "contract": {
    "address": "456 Premium Plaza, Uptown District, City 54321",
    "phone": "+1-555-0456",
    "email": "premium@supermart.com"
  },
  "createdAt": "2025-09-12T10:30:00",
  "updatedAt": "2025-09-12T14:30:00"
}
```

**Status Codes:**
- `200 OK` - Store updated successfully
- `400 Bad Request` - Invalid input data
- `404 Not Found` - Store not found

---

### 6. Delete Store

**Endpoint:** `DELETE /api/stores/{id}`

**Description:** Delete a store by ID

**Path Parameters:**
- `id` (Long) - Store ID

**Example Request:**
```
DELETE /api/stores/1
```

**Response:**
```json
{
  "id": 1,
  "brand": "SuperMart",
  "description": "A premium grocery store chain",
  "storeType": "GROCERY",
  "status": "ACTIVE",
  "storeAdmin": {
    "id": 1,
    "fullName": "John Doe",
    "email": "john.doe@example.com",
    "phone": "1234567890",
    "role": "ROLE_ADMIN"
  },
  "contract": {
    "address": "123 Main Street, Downtown, City 12345",
    "phone": "+1-555-0123",
    "email": "contact@supermart.com"
  },
  "createdAt": "2025-09-12T10:30:00",
  "updatedAt": "2025-09-12T12:00:00"
}
```

**Status Codes:**
- `200 OK` - Store deleted successfully
- `404 Not Found` - Store not found

---

### 7. Get Store by Employee

**Endpoint:** `GET /api/stores/employee`

**Description:** Get store associated with the authenticated employee

**Headers:**
```
Authorization: Bearer <JWT_TOKEN>
```

**Response:**
```json
{
  "id": 1,
  "brand": "SuperMart",
  "description": "A premium grocery store chain",
  "storeType": "GROCERY",
  "status": "ACTIVE",
  "storeAdmin": {
    "id": 1,
    "fullName": "John Doe",
    "email": "john.doe@example.com",
    "phone": "1234567890",
    "role": "ROLE_ADMIN"
  },
  "contract": {
    "address": "123 Main Street, Downtown, City 12345",
    "phone": "+1-555-0123",
    "email": "contact@supermart.com"
  },
  "createdAt": "2025-09-12T10:30:00",
  "updatedAt": "2025-09-12T12:00:00"
}
```

**Status Codes:**
- `200 OK` - Store retrieved successfully
- `401 Unauthorized` - Invalid or missing JWT token
- `404 Not Found` - Store not found for employee

---

## Store Status Types

The system supports the following store status types:

| Status | Description |
|--------|-------------|
| `PENDING` | Store application is pending approval |
| `ACTIVE` | Store is active and operational |
| `BLOCKED` | Store is blocked and cannot operate |

---

## Store Data Models

### StoreDto

```json
{
  "id": "Long - Store ID (auto-generated)",
  "brand": "String - Store brand name",
  "description": "String - Store description",
  "storeType": "String - Type of store (GROCERY, RETAIL, etc.)",
  "status": "StoreStatus - Current store status (PENDING, ACTIVE, BLOCKED)",
  "storeAdmin": "UserDto - Store administrator information",
  "contract": "StoreContract - Store contact information",
  "createdAt": "LocalDateTime - Store creation timestamp",
  "updatedAt": "LocalDateTime - Last update timestamp"
}
```

### StoreContract

```json
{
  "address": "String - Store physical address",
  "phone": "String - Store contact phone number",
  "email": "String - Store contact email address (must be valid email format)"
}
```

---

## Store API Examples

### JavaScript/Fetch API

```javascript
// Create a store
const createStoreResponse = await fetch('/api/stores', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify({
    brand: 'SuperMart',
    description: 'A premium grocery store chain',
    storeType: 'GROCERY',
    contract: {
      address: '123 Main Street, Downtown, City 12345',
      phone: '+1-555-0123',
      email: 'contact@supermart.com'
    }
  })
});

const storeData = await createStoreResponse.json();

// Get all stores
const storesResponse = await fetch('/api/stores');
const stores = await storesResponse.json();

// Get store by admin
const adminStoreResponse = await fetch('/api/stores/admin', {
  headers: {
    'Authorization': `Bearer ${token}`
  }
});
const adminStore = await adminStoreResponse.json();
```

### cURL Examples

**Create a store:**
```bash
curl -X POST http://localhost:8080/api/stores \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "brand": "SuperMart",
    "description": "A premium grocery store chain",
    "storeType": "GROCERY",
    "contract": {
      "address": "123 Main Street, Downtown, City 12345",
      "phone": "+1-555-0123",
      "email": "contact@supermart.com"
    }
  }'
```

**Get all stores:**
```bash
curl -X GET http://localhost:8080/api/stores
```

**Get store by ID:**
```bash
curl -X GET http://localhost:8080/api/stores/1
```

**Get store by admin:**
```bash
curl -X GET http://localhost:8080/api/stores/admin \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**Update a store:**
```bash
curl -X PUT http://localhost:8080/api/stores/1 \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "SuperMart Premium",
    "description": "An upgraded premium grocery store chain",
    "storeType": "PREMIUM_GROCERY",
    "status": "ACTIVE",
    "contract": {
      "address": "456 Premium Plaza, Uptown District, City 54321",
      "phone": "+1-555-0456",
      "email": "premium@supermart.com"
    }
  }'
```

**Delete a store:**
```bash
curl -X DELETE http://localhost:8080/api/stores/1
```

**Get store by employee:**
```bash
curl -X GET http://localhost:8080/api/stores/employee \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

---

## Notes

- All timestamps are in ISO 8601 format
- Passwords are not returned in API responses for security reasons
- Email addresses must be unique across the system
- JWT tokens should be stored securely on the client side
- Always use HTTPS in production environments
