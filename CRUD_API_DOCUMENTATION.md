# E-Commerce CRUD API Documentation

## Overview
This document provides comprehensive API testing documentation for the CRUD operations in the improved e-commerce application. The application now supports full Create, Read, Update, and Delete operations.

## Base URL
```
http://localhost:8080/api/v1/inventory
```

## Authentication
Currently, no authentication is required for these endpoints.

## Common Response Headers
- `Content-Type: application/json`
- `Transfer-Encoding: chunked`
- `Connection: keep-alive`

## Error Response Format
```json
{
  "timestamp": "2026-04-16 12:16:53",
  "status": 404,
  "error": "Not Found",
  "message": "Inventory does not exist!",
  "code": 30,
  "path": "/api/v1/inventory/999"
}
```

## Validation Error Response Format
```json
{
  "timestamp": "2026-04-16 12:16:53",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "validationErrors": {
    "brandName": "Brand name cannot be blank",
    "price": "Price must be greater than 0"
  }
}
```

---

## CRUD API Endpoints

### 1. Create Inventory Item (POST)

**Endpoint:** `POST /api/v1/inventory`

**Description:** Creates a new inventory item in the system.

**Request Headers:**
- `Content-Type: application/json`

**Request Body:**
```json
{
  "brandName": "Adidas",
  "price": 150.0,
  "color": "Blue",
  "size": "M",
  "skuId": "sku_test_001",
  "quantity": 25,
  "productId": 3000,
  "supplierId": 1000
}
```

**Validation Rules:**
- `brandName`: Required, cannot be blank
- `price`: Required, must be greater than 0
- `color`: Required, cannot be blank
- `size`: Required, cannot be blank
- `skuId`: Required, cannot be blank, must be unique
- `quantity`: Required, cannot be negative
- `productId`: Required, must be a valid product ID
- `supplierId`: Required, must be a valid supplier ID

**Success Response (201 Created):**
```json
{
  "inventoryId": 1,
  "brandName": "Adidas",
  "price": 150.0,
  "color": "Blue",
  "size": "M",
  "skuId": "sku_test_001",
  "quantity": 25,
  "product": {
    "productId": 3000,
    "productName": "Shirt"
  },
  "supplier": {
    "supplierId": 1000,
    "supplierName": "CloudTail"
  }
}
```

**Error Responses:**
- `400 Bad Request`: Validation errors in request body
- `409 Conflict`: Inventory with SKU ID already exists

---

### 2. Get Inventory Item by ID (GET)

**Endpoint:** `GET /api/v1/inventory/{inventoryId}`

**Description:** Retrieves a specific inventory item by its ID.

**Request Parameters:**
- `inventoryId` (path parameter): The ID of the inventory item to retrieve
  - Type: Long
  - Required: Yes

**Example Request:**
```bash
GET http://localhost:8080/api/v1/inventory/1
```

**Success Response (200 OK):**
```json
{
  "inventoryId": 1,
  "brandName": "Adidas",
  "price": 150.0,
  "color": "Blue",
  "size": "M",
  "skuId": "sku_test_001",
  "quantity": 25,
  "product": {
    "productId": 3000,
    "productName": "Shirt"
  },
  "supplier": {
    "supplierId": 1000,
    "supplierName": "CloudTail"
  }
}
```

**Error Responses:**
- `404 Not Found`: Inventory item with specified ID does not exist

---

### 3. Update Inventory Item (PUT)

**Endpoint:** `PUT /api/v1/inventory/{inventoryId}`

**Description:** Updates an existing inventory item. Only non-null fields in the request body will be updated.

**Request Headers:**
- `Content-Type: application/json`

**Request Parameters:**
- `inventoryId` (path parameter): The ID of the inventory item to update
  - Type: Long
  - Required: Yes

**Request Body (Partial Update):**
```json
{
  "price": 250.0,
  "quantity": 20
}
```

**Validation Rules for Update:**
- `price`: If provided, must be greater than 0
- `quantity`: If provided, cannot be negative
- All other fields: If provided, appropriate validation applies

**Success Response (200 OK):**
```json
{
  "inventoryId": 1,
  "brandName": "Adidas",
  "price": 250.0,
  "color": "Blue",
  "size": "M",
  "skuId": "sku_test_001",
  "quantity": 20,
  "product": {
    "productId": 3000,
    "productName": "Shirt"
  },
  "supplier": {
    "supplierId": 1000,
    "supplierName": "CloudTail"
  }
}
```

**Error Responses:**
- `400 Bad Request`: Validation errors in request body
- `404 Not Found`: Inventory item with specified ID does not exist

---

### 4. Delete Inventory Item (DELETE)

**Endpoint:** `DELETE /api/v1/inventory/{inventoryId}`

**Description:** Deletes an inventory item from the system.

**Request Parameters:**
- `inventoryId` (path parameter): The ID of the inventory item to delete
  - Type: Long
  - Required: Yes

**Example Request:**
```bash
DELETE http://localhost:8080/api/v1/inventory/1
```

**Success Response (204 No Content):**
- Empty response body with status code 204

**Error Responses:**
- `404 Not Found`: Inventory item with specified ID does not exist

---

## Testing Examples

### PowerShell Testing Commands

#### Create Inventory Item
```powershell
$body = '{"brandName":"Puma","price":200.0,"color":"Red","size":"L","skuId":"sku_test_002","quantity":15,"productId":3001,"supplierId":1001}'
$headers = @{"Content-Type"="application/json"}
$response = Invoke-RestMethod -Uri "http://localhost:8080/api/v1/inventory" -Method POST -Body $body -Headers $headers
Write-Host "POST Response: " $response
```

#### Get Inventory Item by ID
```powershell
$response = Invoke-RestMethod -Uri "http://localhost:8080/api/v1/inventory/1" -Method GET
Write-Host "GET Response: " $response
```

#### Update Inventory Item
```powershell
$body = '{"price":250.0,"quantity":20}'
$headers = @{"Content-Type"="application/json"}
$response = Invoke-RestMethod -Uri "http://localhost:8080/api/v1/inventory/1" -Method PUT -Body $body -Headers $headers
Write-Host "PUT Response: " $response
```

#### Delete Inventory Item
```powershell
$response = Invoke-RestMethod -Uri "http://localhost:8080/api/v1/inventory/1" -Method DELETE
Write-Host "DELETE Response: " $response.StatusCode
```

### cURL Testing Commands

#### Create Inventory Item
```bash
curl -X POST "http://localhost:8080/api/v1/inventory" \
     -H "Content-Type: application/json" \
     -d '{
       "brandName": "Puma",
       "price": 200.0,
       "color": "Red",
       "size": "L",
       "skuId": "sku_test_002",
       "quantity": 15,
       "productId": 3001,
       "supplierId": 1001
     }'
```

#### Get Inventory Item by ID
```bash
curl -X GET "http://localhost:8080/api/v1/inventory/1" \
     -H "Content-Type: application/json"
```

#### Update Inventory Item
```bash
curl -X PUT "http://localhost:8080/api/v1/inventory/1" \
     -H "Content-Type: application/json" \
     -d '{
       "price": 250.0,
       "quantity": 20
     }'
```

#### Delete Inventory Item
```bash
curl -X DELETE "http://localhost:8080/api/v1/inventory/1" \
     -H "Content-Type: application/json"
```

---

## Test Results Summary

### ✅ **Successful Operations Tested**

1. **POST /api/v1/inventory** - ✅ Working
   - Successfully creates new inventory items
   - Returns proper 201 status with created entity
   - Validation working for required fields
   - Duplicate SKU ID prevention working

2. **GET /api/v1/inventory/{id}** - ✅ Working
   - Successfully retrieves inventory items by ID
   - Returns proper 200 status with DTO response
   - Returns 404 for non-existent items

3. **PUT /api/v1/inventory/{id}** - ✅ Working
   - Successfully updates existing inventory items
   - Supports partial updates (only non-null fields)
   - Returns proper 200 status with updated entity
   - Returns 404 for non-existent items

4. **DELETE /api/v1/inventory/{id}** - ✅ Working
   - Successfully deletes inventory items
   - Returns proper 204 No Content status
   - Returns 404 for non-existent items
   - Subsequent GET requests return 404 after deletion

---

## Business Logic Features

### Duplicate Prevention
- System prevents creation of inventory items with duplicate SKU IDs
- Returns 409 Conflict for duplicate SKU attempts

### Partial Updates
- PUT operation supports partial updates
- Only non-null fields in request body are updated
- Existing values are preserved for null fields

### Validation
- Comprehensive validation on all fields during creation
- Conditional validation during updates
- Proper error messages for validation failures

### Relationships
- Product and Supplier relationships are properly loaded
- DTO responses include nested product and supplier information

---

## Performance Considerations

- All write operations use `@Transactional` for data consistency
- Read operations use `@Transactional(readOnly = true)` for optimal performance
- JPA relationships are configured with `FetchType.LAZY`
- Partial updates minimize database writes

---

## Security Notes

- Input validation on all endpoints
- SQL injection protection via JPA parameter binding
- No sensitive entity information exposed in API responses
- Proper HTTP status codes for different error scenarios

---

## Version Information

- API Version: v1
- Spring Boot Version: 4.0.1
- Java Version: 17
- Database: H2 (In-memory)

---

*Last Updated: April 16, 2026*
