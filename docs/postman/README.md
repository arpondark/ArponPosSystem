# ArponPosSystem API Documentation

## Overview

This documentation provides comprehensive testing guides for all REST API controllers in the ArponPosSystem. Each controller has detailed Postman testing instructions with sample requests and responses.

**Base URL:** `http://localhost:8080`

---

## Controllers Documentation

### 1. [Auth Controller](AuthController.md)
Handles user authentication and registration.

**Base Path:** `/auth`

**Key Endpoints:**
- `POST /auth/signup` - User registration
- `POST /auth/login` - User login
- `POST /auth/signup-admin` - Admin registration

### 2. [User Controller](UserController.md)
Manages user profile operations.

**Base Path:** `/api/users`

**Key Endpoints:**
- `GET /api/users/profile` - Get current user profile
- `GET /api/users/{id}` - Get user by ID

### 3. [Store Controller](StoreController.md)
Handles store management operations.

**Base Path:** `/api/stores`

**Key Endpoints:**
- `POST /api/stores` - Create store
- `GET /api/stores` - Get all stores
- `GET /api/stores/{id}` - Get store by ID
- `PUT /api/stores/{id}` - Update store
- `DELETE /api/stores/{id}` - Delete store

### 4. [Category Controller](CategoryController.md)
Manages product categories.

**Base Path:** `/api/categories`

**Key Endpoints:**
- `POST /api/categories` - Create category
- `GET /api/categories/store/{storeId}` - Get categories by store
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category

### 5. [Product Controller](ProductController.md)
Handles product management and search.

**Base Path:** `/api/products`

**Key Endpoints:**
- `POST /api/products` - Create product
- `GET /api/products/store/{storeId}` - Get products by store
- `GET /api/products/category/{categoryId}` - Get products by category
- `GET /api/products/store/{storeId}/search` - Search products
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

---

## Quick Start Guide

### Step 1: Setup Postman Environment

Create a new environment with these variables:

```json
{
  "baseUrl": "http://localhost:8080",
  "authToken": "",
  "userId": "",
  "storeId": "",
  "categoryId": "",
  "productId": ""
}
```

### Step 2: Authentication Flow

1. **Register User** (Auth Controller)
2. **Login** (Auth Controller)
3. **Save JWT Token** to `authToken` variable

### Step 3: Basic Workflow

1. **Create Store** (Store Controller)
2. **Create Category** (Category Controller)
3. **Create Product** (Product Controller)
4. **Test Other Operations**

---

## Common Postman Headers

### For Authentication Required Endpoints

```http
Content-Type: application/json
Authorization: {{authToken}}
```

### For Public Endpoints

```http
Content-Type: application/json
```

---

## Sample Test Sequence

### 1. Authentication

```json
POST {{baseUrl}}/auth/signup
{
  "fullName": "Test User",
  "email": "test@example.com",
  "password": "TestPassword123",
  "phone": "+1234567890",
  "role": "ROLE_STORE_ADMIN"
}
```

### 2. Store Creation

```json
POST {{baseUrl}}/api/stores
Authorization: {{authToken}}
{
  "brand": "Test Store",
  "description": "Test store description",
  "storeType": "ELECTRONICS",
  "contract": "OWNED",
  "status": "PENDING"
}
```

### 3. Category Creation

```json
POST {{baseUrl}}/api/categories
Authorization: {{authToken}}
{
  "name": "Electronics",
  "storeId": 1
}
```

### 4. Product Creation

```json
POST {{baseUrl}}/api/products
Authorization: {{authToken}}
{
  "name": "Test Product",
  "sku": "TEST-PRODUCT-001",
  "description": "Test product description",
  "mrp": 1000.0,
  "sellingPrice": 900.0,
  "brand": "Test Brand",
  "color": "Blue",
  "imageUrl": "https://example.com/image.jpg",
  "categoryId": 1,
  "storeId": 1
}
```

---

## Error Handling

### Common HTTP Status Codes

- `200 OK` - Success
- `201 Created` - Resource created successfully
- `204 No Content` - Success with no response body
- `400 Bad Request` - Invalid request data
- `401 Unauthorized` - Authentication required
- `403 Forbidden` - Insufficient permissions
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

### Common Error Response Format

```json
{
  "error": "ErrorType",
  "message": "Detailed error message",
  "status": 400,
  "timestamp": "2025-09-13T10:30:00"
}
```

---

## User Roles and Permissions

### System Roles

- `ROLE_ADMIN` - System administrator
- `ROLE_STORE_ADMIN` - Store owner/administrator
- `ROLE_STORE_MANAGER` - Store manager
- `ROLE_STORE_EMPLOYEE` - Store employee

### Permission Matrix

| Operation | Admin | Store Admin | Store Manager | Store Employee |
|-----------|-------|-------------|---------------|----------------|
| Create Store | ✅ | ✅ | ❌ | ❌ |
| Manage Products | ✅ | ✅ | ✅ | ❌ |
| Manage Categories | ✅ | ✅ | ✅ | ❌ |
| View Reports | ✅ | ✅ | ✅ | ✅ |
| User Management | ✅ | ✅ | ❌ | ❌ |

---

## Testing Best Practices

### 1. Environment Setup

- Use Postman environments for different stages (dev, staging, prod)
- Store common variables (baseUrl, tokens, IDs)
- Use pre-request scripts for dynamic data

### 2. Test Data Management

- Use unique values for email, SKU, and other unique fields
- Create test data sets for different scenarios
- Clean up test data after testing

### 3. Authentication

- Always test with fresh tokens
- Test with expired tokens
- Test without authentication where applicable

### 4. Error Scenarios

- Test with invalid data
- Test with non-existent IDs
- Test permission boundaries

### 5. Workflow Testing

- Test complete user journeys
- Verify data consistency across endpoints
- Test cascade operations (delete store → categories → products)

---

## Postman Collection Import

### Collection Structure

```
ArponPosSystem API/
├── Auth/
│   ├── User Signup
│   ├── Admin Signup
│   └── Login
├── Users/
│   ├── Get Profile
│   └── Get User by ID
├── Stores/
│   ├── Create Store
│   ├── Get All Stores
│   ├── Get Store by ID
│   ├── Update Store
│   └── Delete Store
├── Categories/
│   ├── Create Category
│   ├── Get Categories by Store
│   ├── Update Category
│   └── Delete Category
└── Products/
    ├── Create Product
    ├── Get Products by Store
    ├── Search Products
    ├── Update Product
    └── Delete Product
```

### Environment Variables

```json
{
  "id": "env-id",
  "name": "ArponPosSystem Local",
  "values": [
    {"key": "baseUrl", "value": "http://localhost:8080"},
    {"key": "authToken", "value": ""},
    {"key": "userId", "value": ""},
    {"key": "storeId", "value": ""},
    {"key": "categoryId", "value": ""},
    {"key": "productId", "value": ""}
  ]
}
```

---

## Troubleshooting

### Common Issues

1. **Authentication Failures**
   - Check JWT token format
   - Verify token expiration
   - Ensure proper Authorization header

2. **Data Validation Errors**
   - Check required fields
   - Verify data types
   - Validate enum values

3. **Permission Errors**
   - Verify user role
   - Check resource ownership
   - Confirm endpoint permissions

4. **Connection Issues**
   - Verify application is running
   - Check port configuration
   - Confirm network connectivity

### Debug Tips

- Enable Postman console for detailed logs
- Use network inspection tools
- Check application logs for server-side errors
- Verify database connectivity

---

## Support

For additional support:

1. Check individual controller documentation
2. Review error messages carefully
3. Verify API endpoint URLs and methods
4. Ensure proper authentication and permissions
5. Test with minimal valid data first

---

## Notes

- All timestamps are in ISO 8601 format
- Use HTTPS in production environments
- Implement proper error handling in client applications
- Consider rate limiting for production usage
- Regular token refresh is recommended for long-running applications
