# Product Controller API Documentation

## Overview

The Product Controller manages products within stores and categories. It provides comprehensive CRUD operations with search functionality and category-based filtering.

**Base URL:** `/api/products`

---

## Endpoints

### 1. Create Product

Create a new product in a store with optional category assignment.

**Endpoint:** `POST /api/products`

**Headers:**

```http
Content-Type: application/json
Authorization: Bearer YOUR_JWT_TOKEN
```

**Request Body:**

```json
{
  "name": "Men's Cotton Casual Shirt",
  "sku": "SHRT-M-COTTON-BLU-2025",
  "description": "Comfortable blue cotton shirt for men. Ideal for casual and semi-formal occasions.",
  "mrp": 1299.0,
  "sellingPrice": 899.0,
  "brand": "Zara",
  "color": "Blue",
  "imageUrl": "https://example.com/images/shirt.jpg",
  "categoryId": 1,
  "storeId": 1
}
```

**Example Response:**

```json
{
  "id": 1,
  "name": "Men's Cotton Casual Shirt",
  "sku": "SHRT-M-COTTON-BLU-2025",
  "description": "Comfortable blue cotton shirt for men. Ideal for casual and semi-formal occasions.",
  "mrp": 1299.0,
  "sellingPrice": 899.0,
  "brand": "Zara",
  "color": "Blue",
  "imageUrl": "https://example.com/images/shirt.jpg",
  "categoryId": 1,
  "storeId": 1,
  "createdAt": "2025-09-13T10:30:00",
  "updatedAt": "2025-09-13T10:30:00"
}
```

**Status Codes:**

- `200 OK` - Product created successfully
- `400 Bad Request` - Invalid data or duplicate SKU
- `401 Unauthorized` - Invalid JWT token

---

### 2. Update Product

Update an existing product.

**Endpoint:** `PUT /api/products/{id}`

**Headers:**

```http
Content-Type: application/json
Authorization: Bearer YOUR_JWT_TOKEN
```

**Request Body:**

```json
{
  "name": "Updated Product Name",
  "sku": "UPDATED-SKU-2025",
  "description": "Updated description",
  "mrp": 1499.0,
  "sellingPrice": 999.0,
  "brand": "Updated Brand",
  "color": "Red",
  "imageUrl": "https://example.com/images/updated.jpg",
  "categoryId": 2,
  "storeId": 1
}
```

**Example Response:**

```json
{
  "id": 1,
  "name": "Updated Product Name",
  "sku": "UPDATED-SKU-2025",
  "description": "Updated description",
  "mrp": 1499.0,
  "sellingPrice": 999.0,
  "brand": "Updated Brand",
  "color": "Red",
  "imageUrl": "https://example.com/images/updated.jpg",
  "categoryId": 2,
  "storeId": 1,
  "createdAt": "2025-09-13T10:30:00",
  "updatedAt": "2025-09-13T11:45:00"
}
```

**Status Codes:**

- `200 OK` - Product updated successfully
- `404 Not Found` - Product not found
- `400 Bad Request` - Invalid data

---

### 3. Delete Product

Delete a product by ID.

**Endpoint:** `DELETE /api/products/{id}`

**Headers:**

```http
Authorization: Bearer YOUR_JWT_TOKEN
```

**Example Response:** No content (204)

**Status Codes:**

- `204 No Content` - Product deleted successfully
- `404 Not Found` - Product not found
- `401 Unauthorized` - Invalid permissions

---

### 4. Get Products by Store

Retrieve all products for a specific store.

**Endpoint:** `GET /api/products/store/{storeId}`

**Headers:** None required (public endpoint)

**Example Response:**

```json
[
  {
    "id": 1,
    "name": "Men's Cotton Casual Shirt",
    "sku": "SHRT-M-COTTON-BLU-2025",
    "description": "Comfortable blue cotton shirt",
    "mrp": 1299.0,
    "sellingPrice": 899.0,
    "brand": "Zara",
    "color": "Blue",
    "imageUrl": "https://example.com/images/shirt.jpg",
    "categoryId": 1,
    "storeId": 1,
    "createdAt": "2025-09-13T10:30:00",
    "updatedAt": "2025-09-13T10:30:00"
  }
]
```

---

### 5. Search Products

Search products by keyword within a store.

**Endpoint:** `GET /api/products/store/{storeId}/search?keyword={keyword}`

**Parameters:**

- `storeId` (path): Store ID
- `keyword` (query): Search keyword

**Example URL:** `GET /api/products/store/1/search?keyword=shirt`

**Example Response:**

```json
[
  {
    "id": 1,
    "name": "Men's Cotton Casual Shirt",
    "sku": "SHRT-M-COTTON-BLU-2025",
    "description": "Comfortable blue cotton shirt",
    "mrp": 1299.0,
    "sellingPrice": 899.0,
    "brand": "Zara",
    "color": "Blue",
    "imageUrl": "https://example.com/images/shirt.jpg",
    "categoryId": 1,
    "storeId": 1
  }
]
```

---

### 6. Get Products by Category

Retrieve all products for a specific category.

**Endpoint:** `GET /api/products/category/{categoryId}`

**Headers:** None required (public endpoint)

**Example Response:**

```json
[
  {
    "id": 1,
    "name": "Men's Cotton Casual Shirt",
    "sku": "SHRT-M-COTTON-BLU-2025",
    "categoryId": 1,
    "storeId": 1
  }
]
```

---

### 7. Get Products by Store and Category

Retrieve products filtered by both store and category.

**Endpoint:** `GET /api/products/store/{storeId}/category/{categoryId}`

**Headers:** None required (public endpoint)

---

## Postman Testing Instructions

### Step 1: Setup Environment

1. Create Postman environment:
   - `baseUrl` = `http://localhost:8080`
   - `authToken` = `Bearer YOUR_JWT_TOKEN`

### Step 2: Create Product

1. **Method:** POST
2. **URL:** `{{baseUrl}}/api/products`
3. **Headers:**
   - `Content-Type: application/json`
   - `Authorization: {{authToken}}`
4. **Body (Raw JSON):**

```json
{
  "name": "iPhone 15 Pro",
  "sku": "IPHONE-15-PRO-256GB",
  "description": "Latest iPhone with Pro camera system",
  "mrp": 129900.0,
  "sellingPrice": 119900.0,
  "brand": "Apple",
  "color": "Natural Titanium",
  "imageUrl": "https://example.com/iphone15pro.jpg",
  "categoryId": 1,
  "storeId": 1
}
```

### Step 3: Update Product

1. **Method:** PUT
2. **URL:** `{{baseUrl}}/api/products/1`
3. **Headers:**
   - `Content-Type: application/json`
   - `Authorization: {{authToken}}`
4. **Body:** Updated product JSON

### Step 4: Get Products by Store

1. **Method:** GET
2. **URL:** `{{baseUrl}}/api/products/store/1`
3. **Headers:** None

### Step 5: Search Products

1. **Method:** GET
2. **URL:** `{{baseUrl}}/api/products/store/1/search?keyword=iPhone`
3. **Headers:** None

### Step 6: Get Products by Category

1. **Method:** GET
2. **URL:** `{{baseUrl}}/api/products/category/1`
3. **Headers:** None

### Step 7: Delete Product

1. **Method:** DELETE
2. **URL:** `{{baseUrl}}/api/products/1`
3. **Headers:**
   - `Authorization: {{authToken}}`

---

## Sample Test Data

### Electronics Category

```json
{
  "name": "Samsung Galaxy S24",
  "sku": "SAMSUNG-S24-128GB",
  "description": "Flagship Android smartphone",
  "mrp": 79999.0,
  "sellingPrice": 69999.0,
  "brand": "Samsung",
  "color": "Phantom Black",
  "imageUrl": "https://example.com/galaxy-s24.jpg",
  "categoryId": 1,
  "storeId": 1
}
```

### Clothing Category

```json
{
  "name": "Nike Air Max",
  "sku": "NIKE-AIRMAX-270-WHITE",
  "description": "Comfortable running shoes",
  "mrp": 12995.0,
  "sellingPrice": 9995.0,
  "brand": "Nike",
  "color": "White",
  "imageUrl": "https://example.com/nike-airmax.jpg",
  "categoryId": 2,
  "storeId": 1
}
```

---

## Field Validations

### Required Fields

- `name` - Product name (not null)
- `sku` - Stock Keeping Unit (unique, not null)
- `storeId` - Must reference existing store

### Optional Fields

- `categoryId` - Must reference existing category if provided
- `description` - Product description
- `mrp` - Maximum Retail Price
- `sellingPrice` - Current selling price
- `brand` - Product brand
- `color` - Product color
- `imageUrl` - Product image URL

### Business Rules

- SKU must be unique across all products
- Selling price should not exceed MRP
- Category must belong to the same store as the product

---

## Error Handling

### Common Error Responses

```json
{
  "error": "UserException",
  "message": "Failed to create product: SKU already exists",
  "status": 400
}
```

```json
{
  "error": "RuntimeException",
  "message": "Store not found",
  "status": 404
}
```

```json
{
  "error": "RuntimeException",
  "message": "Category not found",
  "status": 404
}
```

### Troubleshooting

- **Duplicate SKU:** Use a unique SKU value
- **Store not found:** Verify store ID exists
- **Category not found:** Verify category ID exists
- **Permission denied:** Ensure user has store access
- **Invalid JSON:** Check request body format

---

## Testing Workflow

1. **Prerequisites:** Login and create store/category
2. **Create Product:** Test product creation with all fields
3. **Read Products:** Verify product appears in listings
4. **Search Products:** Test keyword search functionality
5. **Update Product:** Modify product details
6. **Filter by Category:** Test category-based filtering
7. **Delete Product:** Remove product and verify deletion

---

## Notes

- Products are automatically linked to stores and categories
- SKU must be unique across the entire system
- Search functionality works on product name, description, and brand
- Categories are optional but recommended for better organization
- Image URLs should be publicly accessible
- All price fields support decimal values
