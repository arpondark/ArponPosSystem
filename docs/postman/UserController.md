# User Controller API Documentation

## Overview

The User Controller manages user profile operations and user information retrieval. It provides endpoints for accessing user data with proper authentication.

**Base URL:** `/api/users`

---

## Endpoints

### 1. Get User Profile

Retrieve the profile of the currently authenticated user.

**Endpoint:** `GET /api/users/profile`

**Headers:**

```http
Authorization: Bearer YOUR_JWT_TOKEN
```

**Example Response:**

```json
{
  "id": 1,
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "phone": "+1234567890",
  "role": "ROLE_STORE_ADMIN",
  "lastLogin": "2025-09-13T10:30:00",
  "createdAt": "2025-09-10T08:00:00",
  "updatedAt": "2025-09-13T10:30:00"
}
```

**Status Codes:**

- `200 OK` - Profile retrieved successfully
- `401 Unauthorized` - Invalid or missing JWT token
- `404 Not Found` - User not found

---

### 2. Get User by ID

Retrieve a specific user's information by their ID.

**Endpoint:** `GET /api/users/{id}`

**Headers:**

```http
Authorization: Bearer YOUR_JWT_TOKEN
```

**Path Parameters:**

- `id` (Long): User ID to retrieve

**Example Response:**

```json
{
  "id": 2,
  "fullName": "Jane Smith",
  "email": "jane.smith@example.com",
  "phone": "+1987654321",
  "role": "ROLE_STORE_MANAGER",
  "lastLogin": "2025-09-12T15:45:00",
  "createdAt": "2025-09-08T12:00:00",
  "updatedAt": "2025-09-12T15:45:00"
}
```

**Status Codes:**

- `200 OK` - User retrieved successfully
- `401 Unauthorized` - Invalid or missing JWT token
- `404 Not Found` - User not found

---

## Postman Testing Instructions

### Step 1: Setup Environment

1. Create Postman environment:
   - `baseUrl` = `http://localhost:8080`
   - `authToken` = `Bearer YOUR_JWT_TOKEN`

### Step 2: Get Current User Profile

1. **Method:** GET
2. **URL:** `{{baseUrl}}/api/users/profile`
3. **Headers:**
   - `Authorization: {{authToken}}`

### Step 3: Get User by ID

1. **Method:** GET
2. **URL:** `{{baseUrl}}/api/users/1`
3. **Headers:**
   - `Authorization: {{authToken}}`

---

## User Roles

The system supports the following user roles:

### Admin Roles

- `ROLE_ADMIN` - System administrator with full access

### Store Roles

- `ROLE_STORE_ADMIN` - Store owner/administrator
- `ROLE_STORE_MANAGER` - Store manager with management privileges
- `ROLE_STORE_EMPLOYEE` - Store employee with limited access

---

## Response Fields

### User DTO Fields

| Field | Type | Description |
|-------|------|-------------|
| `id` | Long | Unique user identifier |
| `fullName` | String | User's full name |
| `email` | String | User's email address (unique) |
| `phone` | String | User's phone number |
| `role` | String | User's role in the system |
| `lastLogin` | DateTime | Last login timestamp |
| `createdAt` | DateTime | Account creation timestamp |
| `updatedAt` | DateTime | Last profile update timestamp |

---

## Authorization Requirements

### Profile Access

- **Get Profile:** User can access their own profile
- **Get User by ID:** User can access other users' basic information

### Security Notes

- All endpoints require valid JWT authentication
- Users can view other users' profiles (for business collaboration)
- Sensitive information like passwords are never returned
- JWT tokens must be included in the Authorization header

---

## Error Handling

### Common Error Responses

```json
{
  "error": "UserException",
  "message": "Invalid or expired token",
  "status": 401
}
```

```json
{
  "error": "UserException",
  "message": "User not found",
  "status": 404
}
```

### Authentication Errors

```json
{
  "error": "Unauthorized",
  "message": "JWT token is missing or invalid",
  "status": 401
}
```

### Troubleshooting

- **401 Unauthorized:** Check if JWT token is valid and properly formatted
- **404 Not Found:** Verify the user ID exists in the system
- **Token Expired:** Login again to get a fresh JWT token
- **Missing Authorization Header:** Ensure Authorization header is included

---

## Testing Workflow

### Step 1: Prerequisites

1. **Register User:** Use Auth Controller to create an account
2. **Login:** Use Auth Controller to get JWT token
3. **Save Token:** Store JWT token in Postman environment

### Step 2: Test Profile Access

1. **Get Own Profile:** Test `/api/users/profile` endpoint
2. **Verify Response:** Check that response contains correct user data
3. **Validate Fields:** Ensure all expected fields are present

### Step 3: Test User Lookup

1. **Get User by ID:** Test `/api/users/{id}` with different user IDs
2. **Test Invalid IDs:** Try non-existent user IDs
3. **Test Permissions:** Verify access control works correctly

### Step 4: Test Error Scenarios

1. **Invalid Token:** Test with expired or malformed JWT
2. **Missing Token:** Test without Authorization header
3. **Non-existent User:** Test with invalid user IDs

---

## Sample Postman Collection

### Collection Variables

```json
{
  "baseUrl": "http://localhost:8080",
  "authToken": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Request Examples

#### Get Current User Profile

```http
GET {{baseUrl}}/api/users/profile
Authorization: {{authToken}}
```

#### Get User by ID

```http
GET {{baseUrl}}/api/users/2
Authorization: {{authToken}}
```

---

## Integration with Other Controllers

### Related Endpoints

- **Auth Controller:** Login to get JWT tokens
- **Store Controller:** User roles determine store access levels
- **Product Controller:** User permissions affect product operations
- **Category Controller:** User roles control category management

### Workflow Integration

1. **Authentication:** Login via Auth Controller
2. **Profile Management:** Access user information
3. **Store Operations:** Use user data for store-related operations
4. **Permission Checking:** Validate user roles for protected operations

---

## Security Considerations

### Data Protection

- **Password Security:** Passwords are never returned in responses
- **Token Validation:** All requests validate JWT tokens
- **Role-Based Access:** Different endpoints respect user roles
- **Data Filtering:** Sensitive information is filtered from responses

### Best Practices

- **Token Storage:** Store JWT tokens securely in client applications
- **Token Refresh:** Implement token refresh mechanisms
- **HTTPS Usage:** Always use HTTPS in production
- **Input Validation:** Validate all user inputs

---

## Notes

- User profiles are read-only through this controller
- Profile updates should be handled through dedicated endpoints
- JWT tokens contain user role information for authorization
- The system automatically updates `lastLogin` timestamp during authentication
- User data is filtered to exclude sensitive information
- All timestamps are in ISO 8601 format
- Phone numbers should include country codes for international support
