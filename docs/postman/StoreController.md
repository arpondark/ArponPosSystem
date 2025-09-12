# Store Controller API Documentation

## Overview

The Store Controller manages store operations including creation, retrieval, updates, and deletion. It handles store management for different user roles.

**Base URL:** `/api/stores`

---

## Endpoints

### 1. Create Store

Create a new store (requires authentication).

**Endpoint:** `POST /api/stores`

**Headers:**

```http
Content-Type: application/json
Authorization: Bearer YOUR_JWT_TOKEN
```

**Request Body:**

```json
{
  "brand": "TechMart Electronics",
  "description": "Premium electronics and gadgets store",
  "storeType": "ELECTRONICS",
  "contract": "FRANCHISE",
  "status": "PENDING"
}
```

**Available Store Types:**

- `ELECTRONICS`
- `CLOTHING`
- `GROCERY`
- `RESTAURANT`
- `PHARMACY`
- `BOOKS`
- `SPORTS`

**Available Contracts:**

- `OWNED`
- `FRANCHISE`
- `PARTNERSHIP`

**Available Status:**

- `PENDING`
- `ACTIVE`
- `INACTIVE`
- `SUSPENDED`

**Example Response:**

```json
{
  "id": 1,
  "brand": "TechMart Electronics",
  "description": "Premium electronics and gadgets store",
  "storeType": "ELECTRONICS",
  "contract": "FRANCHISE",
  "status": "PENDING",
  "storeAdmin": {
    "id": 1,
    "fullName": "John Doe",
    "email": "john.doe@example.com"
  },
  "createdAt": "2025-09-13T10:30:00",
  "updatedAt": "2025-09-13T10:30:00"
}
```

**Status Codes:**

- `200 OK` - Store created successfully
- `400 Bad Request` - Invalid data
- `401 Unauthorized` - Invalid JWT token

---

### 2. Get Store by ID

Retrieve a specific store by its ID.

**Endpoint:** `GET /api/stores/{id}`

**Headers:** None required (public endpoint)

**Example Response:**

```json
{
  "id": 1,
  "brand": "TechMart Electronics",
  "description": "Premium electronics and gadgets store",
  "storeType": "ELECTRONICS",
  "contract": "FRANCHISE",
  "status": "ACTIVE",
  "storeAdmin": {
    "id": 1,
    "fullName": "John Doe",
    "email": "john.doe@example.com"
  },
  "createdAt": "2025-09-13T10:30:00",
  "updatedAt": "2025-09-13T10:30:00"
}
```

**Status Codes:**

- `200 OK` - Store retrieved successfully
- `404 Not Found` - Store not found

---

### 3. Get All Stores

Retrieve all stores in the system.

**Endpoint:** `GET /api/stores`

**Headers:** None required (public endpoint)

**Example Response:**

```json
[
  {
    "id": 1,
    "brand": "TechMart Electronics",
    "description": "Premium electronics and gadgets store",
    "storeType": "ELECTRONICS",
    "contract": "FRANCHISE",
    "status": "ACTIVE"
  },
  {
    "id": 2,
    "brand": "Fashion Hub",
    "description": "Trendy clothing and accessories",
    "storeType": "CLOTHING",
    "contract": "OWNED",
    "status": "ACTIVE"
  }
]
```

**Status Codes:**

- `200 OK` - Stores retrieved successfully

---

### 4. Get Store by Admin

Retrieve the store managed by the authenticated admin user.

**Endpoint:** `GET /api/stores/admin`

**Headers:**

```http
Authorization: Bearer YOUR_JWT_TOKEN
```

**Example Response:**

```json
{
  "id": 1,
  "brand": "TechMart Electronics",
  "description": "Premium electronics and gadgets store",
  "storeType": "ELECTRONICS",
  "contract": "FRANCHISE",
  "status": "ACTIVE",
  "storeAdmin": {
    "id": 1,
    "fullName": "John Doe",
    "email": "john.doe@example.com"
  }
}
```

**Status Codes:**

- `200 OK` - Store retrieved successfully
- `404 Not Found` - No store found for admin
- `401 Unauthorized` - Invalid JWT token

---

### 5. Update Store

Update an existing store.

**Endpoint:** `PUT /api/stores/{id}`

**Headers:**

```http
Content-Type: application/json
```

**Request Body:**

```json
{
  "brand": "Updated Store Name",
  "description": "Updated store description",
  "storeType": "ELECTRONICS",
  "contract": "OWNED",
  "status": "ACTIVE"
}
```

**Example Response:**

```json
{
  "id": 1,
  "brand": "Updated Store Name",
  "description": "Updated store description",
  "storeType": "ELECTRONICS",
  "contract": "OWNED",
  "status": "ACTIVE",
  "updatedAt": "2025-09-13T11:45:00"
}
```

**Status Codes:**

- `200 OK` - Store updated successfully
- `404 Not Found` - Store not found
- `400 Bad Request` - Invalid data

---

### 6. Delete Store

Delete a store by ID.

**Endpoint:** `DELETE /api/stores/{id}`

**Headers:** None required

**Example Response:**

```json
{
  "id": 1,
  "brand": "TechMart Electronics",
  "description": "Premium electronics and gadgets store",
  "status": "DELETED"
}
```

**Status Codes:**

- `200 OK` - Store deleted successfully
- `404 Not Found` - Store not found

---

### 7. Get Store by Employee

Retrieve the store where the authenticated employee works.

**Endpoint:** `GET /api/stores/employee`

**Headers:**

```http
Authorization: Bearer YOUR_JWT_TOKEN
```

**Example Response:**

```json
{
  "id": 1,
  "brand": "TechMart Electronics",
  "description": "Premium electronics and gadgets store",
  "storeType": "ELECTRONICS",
  "contract": "FRANCHISE",
  "status": "ACTIVE"
}
```

**Status Codes:**

- `200 OK` - Store retrieved successfully
- `404 Not Found` - No store found for employee
- `401 Unauthorized` - Invalid JWT token

---

## Postman Testing Instructions

### Step 1: Setup Environment

1. Create Postman environment:
   - `baseUrl` = `http://localhost:8080`
   - `authToken` = `Bearer YOUR_JWT_TOKEN`

### Step 2: Create Store

1. **Method:** POST
2. **URL:** `{{baseUrl}}/api/stores`
3. **Headers:**
   - `Content-Type: application/json`
   - `Authorization: {{authToken}}`
4. **Body (Raw JSON):**

```json
{
  "brand": "SuperMart Grocery",
  "description": "Fresh groceries and daily essentials",
  "storeType": "GROCERY",
  "contract": "OWNED",
  "status": "PENDING"
}
```

### Step 3: Get Store by ID

1. **Method:** GET
2. **URL:** `{{baseUrl}}/api/stores/1`
3. **Headers:** None

### Step 4: Get All Stores

1. **Method:** GET
2. **URL:** `{{baseUrl}}/api/stores`
3. **Headers:** None

### Step 5: Get Store by Admin

1. **Method:** GET
2. **URL:** `{{baseUrl}}/api/stores/admin`
3. **Headers:**
   - `Authorization: {{authToken}}`

### Step 6: Update Store

1. **Method:** PUT
2. **URL:** `{{baseUrl}}/api/stores/1`
3. **Headers:**
   - `Content-Type: application/json`
4. **Body (Raw JSON):**

```json
{
  "brand": "Updated SuperMart",
  "description": "Updated description",
  "storeType": "GROCERY",
  "contract": "OWNED",
  "status": "ACTIVE"
}
```

### Step 7: Get Store by Employee

1. **Method:** GET
2. **URL:** `{{baseUrl}}/api/stores/employee`
3. **Headers:**
   - `Authorization: {{authToken}}`

### Step 8: Delete Store

1. **Method:** DELETE
2. **URL:** `{{baseUrl}}/api/stores/1`
3. **Headers:** None

---

## Sample Test Data

### Electronics Store

```json
{
  "brand": "ElectroWorld",
  "description": "Latest electronics and tech gadgets",
  "storeType": "ELECTRONICS",
  "contract": "FRANCHISE",
  "status": "PENDING"
}
```

### Clothing Store

```json
{
  "brand": "Fashion Palace",
  "description": "Trendy clothes for all ages",
  "storeType": "CLOTHING",
  "contract": "OWNED",
  "status": "PENDING"
}
```

### Restaurant

```json
{
  "brand": "Spice Garden",
  "description": "Authentic Indian cuisine",
  "storeType": "RESTAURANT",
  "contract": "PARTNERSHIP",
  "status": "PENDING"
}
```

---

## Authorization Requirements

### Store Creation

- Requires authentication with valid JWT token
- User becomes the store admin automatically

### Store Access Levels

- **Store Admin:** Full access to their store
- **Store Manager:** Management access to assigned store
- **Store Employee:** Basic access to assigned store
- **Public:** Read access to store information

---

## Error Handling

### Common Error Responses

```json
{
  "error": "RuntimeException",
  "message": "Store not found with id: 999",
  "status": 404
}
```

```json
{
  "error": "RuntimeException",
  "message": "Store not found for admin: 123",
  "status": 404
}
```

### Troubleshooting

- **Store not found:** Verify store ID exists
- **Admin not found:** Ensure user is a store admin
- **Employee not found:** Ensure user is assigned to a store
- **Authentication errors:** Check JWT token validity
- **Validation errors:** Verify required fields and enum values

---

## Business Rules

1. **Store Creation:** Only authenticated users can create stores
2. **Store Admin:** User who creates the store becomes the admin
3. **Store Status:** New stores start with "PENDING" status
4. **Store Types:** Must use predefined store type values
5. **Contract Types:** Must use predefined contract type values

---

## Testing Workflow

1. **Prerequisites:** User registration and login
2. **Create Store:** Test store creation with different types
3. **Verify Creation:** Check store appears in listings
4. **Admin Access:** Test admin-specific endpoints
5. **Update Store:** Modify store details
6. **Employee Access:** Test employee-specific endpoints
7. **Public Access:** Verify public endpoints work without auth
8. **Delete Store:** Test store removal

---

## Notes

- Store creation automatically assigns the creator as store admin
- Some endpoints require authentication while others are public
- Store status can be managed through updates
- Soft delete functionality returns the deleted store data
- Store types and contracts are predefined enums
- Timestamps are automatically managed by the system
