# Category Controller API Documentation

## Overview

The Category Controller manages product categories within stores. It provides CRUD operations for categories with proper authentication and authorization.

**Base URL:** `/api/categories`

---

## Endpoints

### 1. Create Category

Create a new category in a store.

**Endpoint:** `POST /api/categories`

**Headers:**

```http
Content-Type: application/json
Authorization: Bearer YOUR_JWT_TOKEN
```

**Request Body:**

```json
{
  "name": "Men's Clothing",
  "storeId": 1
}
```

**Example Response:**

```json
{
  "id": 1,
  "name": "Men's Clothing",
  "storeId": 1
}
```

**Status Codes:**

- `201 Created` - Category created successfully
- `400 Bad Request` - Invalid data or unauthorized
- `401 Unauthorized` - Invalid or missing JWT token

---

### 2. Update Category

Update an existing category.

**Endpoint:** `PUT /api/categories/{id}`

**Headers:**

```http
Content-Type: application/json
Authorization: Bearer YOUR_JWT_TOKEN
```

**Request Body:**

```json
{
  "name": "Updated Category Name",
  "storeId": 1
}
```

**Example Response:**

```json
{
  "id": 1,
  "name": "Updated Category Name",
  "storeId": 1
}
```

**Status Codes:**

- `200 OK` - Category updated successfully
- `400 Bad Request` - Invalid data or unauthorized
- `404 Not Found` - Category not found

---

### 3. Delete Category

Delete a category by ID.

**Endpoint:** `DELETE /api/categories/{id}`

**Headers:**

```http
Authorization: Bearer YOUR_JWT_TOKEN
```

**Example Response:** No content (204)

**Status Codes:**

- `204 No Content` - Category deleted successfully
- `404 Not Found` - Category not found
- `401 Unauthorized` - Invalid permissions

---

### 4. Get Categories by Store

Retrieve all categories for a specific store.

**Endpoint:** `GET /api/categories/store/{storeId}`

**Headers:** None required (public endpoint)

**Example Response:**

```json
[
  {
    "id": 1,
    "name": "Men's Clothing",
    "storeId": 1
  },
  {
    "id": 2,
    "name": "Electronics",
    "storeId": 1
  }
]
```

**Status Codes:**

- `200 OK` - Categories retrieved successfully
- `404 Not Found` - Store not found

---

## Postman Testing Instructions

### Step 1: Setup Environment

1. Create Postman environment with:
   - `baseUrl` = `http://localhost:8080`
   - `authToken` = `Bearer YOUR_JWT_TOKEN` (from login)

### Step 2: Create Category

1. **Method:** POST
2. **URL:** `{{baseUrl}}/api/categories`
3. **Headers:**
   - `Content-Type: application/json`
   - `Authorization: {{authToken}}`
4. **Body (Raw JSON):**

```json
{
  "name": "Electronics",
  "storeId": 1
}
```

### Step 3: Update Category

1. **Method:** PUT
2. **URL:** `{{baseUrl}}/api/categories/1`
3. **Headers:**
   - `Content-Type: application/json`
   - `Authorization: {{authToken}}`
4. **Body (Raw JSON):**

```json
{
  "name": "Consumer Electronics",
  "storeId": 1
}
```

### Step 4: Get Categories by Store

1. **Method:** GET
2. **URL:** `{{baseUrl}}/api/categories/store/1`
3. **Headers:** None required

### Step 5: Delete Category

1. **Method:** DELETE
2. **URL:** `{{baseUrl}}/api/categories/1`
3. **Headers:**
   - `Authorization: {{authToken}}`

---

## Sample Test Data

### Category Examples:

```json
{
  "name": "Men's Fashion",
  "storeId": 1
}
```

```json
{
  "name": "Women's Fashion",
  "storeId": 1
}
```

```json
{
  "name": "Home & Kitchen",
  "storeId": 1
}
```

```json
{
  "name": "Sports & Fitness",
  "storeId": 1
}
```

---

## Authorization Requirements

### Required Permissions:

- **Create Category:** Must be store admin or manager
- **Update Category:** Must be store admin or manager
- **Delete Category:** Must be store admin or manager
- **Get Categories:** No authentication required

### User Roles:

- `ROLE_STORE_ADMIN` - Full access to store categories
- `ROLE_STORE_MANAGER` - Full access to store categories
- `ROLE_STORE_EMPLOYEE` - Read-only access

---

## Error Handling

### Common Error Responses:

```json
{
  "error": "UserException",
  "message": "You don't have permission to manage this category",
  "status": 403
}
```

```json
{
  "error": "RuntimeException",
  "message": "Store not found with id: 999",
  "status": 404
}
```

### Troubleshooting:

- **403 Forbidden:** Check user permissions and store ownership
- **404 Not Found:** Verify store ID and category ID exist
- **401 Unauthorized:** Ensure valid JWT token is provided
- **400 Bad Request:** Validate request body format and required fields

---

## Testing Workflow

1. **Prerequisites:** Login to get JWT token
2. **Create Store:** Ensure store exists (use Store Controller)
3. **Create Category:** Test category creation
4. **Read Categories:** Verify category was created
5. **Update Category:** Test category modification
6. **Delete Category:** Test category removal
7. **Verify Deletion:** Confirm category no longer exists

---

## Notes

- Categories are linked to specific stores
- Only store admins and managers can modify categories
- Categories can be retrieved without authentication
- Ensure store exists before creating categories
- Category names should be unique within a store (recommended)
