# E-Commerce API Documentation

## Overview
This document provides comprehensive API testing documentation for the improved e-commerce application. The application now follows RESTful best practices with proper validation, error handling, and DTO responses.

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
  "timestamp": "2026-04-16 11:55:22",
  "status": 404,
  "error": "Not Found",
  "message": "Inventory does not exist!",
  "code": 30,
  "path": "/api/v1/inventory/brand/NonExistentBrand"
}
```

## Validation Error Response Format
```json
{
  "timestamp": "2026-04-16 11:55:22",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "validationErrors": {
    "brandName": "Brand name cannot be blank"
  }
}
```

---

## API Endpoints

### 1. Get Inventory by Brand Name

**Endpoint:** `GET /api/v1/inventory/brand/{brandName}`

**Description:** Retrieves all inventory items for a specific brand.

**Request Parameters:**
- `brandName` (path parameter): The brand name to search for
  - Type: String
  - Required: Yes
  - Validation: Cannot be blank

**Example Request:**
```bash
GET http://localhost:8080/api/v1/inventory/brand/Nike
```

**Success Response (200 OK):**
```json
[
  {
    "inventoryId": 4003,
    "brandName": "Nike",
    "price": 100.0,
    "color": "Grey",
    "size": "S",
    "skuId": "sku_103",
    "quantity": 3,
    "product": {
      "productId": 3000,
      "productName": "Shirt"
    },
    "supplier": {
      "supplierId": 1002,
      "supplierName": "Retailnet"
    }
  },
  {
    "inventoryId": 4004,
    "brandName": "Nike",
    "price": 1000.0,
    "color": "Green",
    "size": "L",
    "skuId": "sku_104",
    "quantity": 18,
    "product": {
      "productId": 3001,
      "productName": "Pant"
    },
    "supplier": {
      "supplierId": 1003,
      "supplierName": "Appario Retail"
    }
  },
  {
    "inventoryId": 4008,
    "brandName": "Nike",
    "price": 890.0,
    "color": "Green",
    "size": "S",
    "skuId": "sku_108",
    "quantity": 13,
    "product": {
      "productId": 3002,
      "productName": "Socks"
    },
    "supplier": {
      "supplierId": 1002,
      "supplierName": "Retailnet"
    }
  }
]
```

**No Content Response (204 No Content):**
- Returned when the brand exists but has no inventory items

**Error Responses:**
- `400 Bad Request`: Brand name is blank or invalid
- `404 Not Found`: No inventory found for the specified brand

---

### 2. Get Inventory by Color

**Endpoint:** `GET /api/v1/inventory/color/{color}`

**Description:** Retrieves all inventory items for a specific color.

**Request Parameters:**
- `color` (path parameter): The color to search for
  - Type: String
  - Required: Yes
  - Validation: Cannot be blank

**Example Request:**
```bash
GET http://localhost:8080/api/v1/inventory/color/Black
```

**Success Response (200 OK):**
```json
[
  {
    "inventoryId": 4000,
    "brandName": "Invictus",
    "price": 800.0,
    "color": "Black",
    "size": "XL",
    "skuId": "sku_100",
    "quantity": 14,
    "product": {
      "productId": 3000,
      "productName": "Shirt"
    },
    "supplier": {
      "supplierId": 1000,
      "supplierName": "CloudTail"
    }
  },
  {
    "inventoryId": 4001,
    "brandName": "Invictus",
    "price": 600.0,
    "color": "Black",
    "size": "XS",
    "skuId": "sku_101",
    "quantity": 5,
    "product": {
      "productId": 3000,
      "productName": "Shirt"
    },
    "supplier": {
      "supplierId": 1000,
      "supplierName": "CloudTail"
    }
  },
  {
    "inventoryId": 4007,
    "brandName": "Reebok",
    "price": 670.0,
    "color": "Black",
    "size": "S",
    "skuId": "sku_107",
    "quantity": 11,
    "product": {
      "productId": 3002,
      "productName": "Socks"
    },
    "supplier": {
      "supplierId": 1003,
      "supplierName": "Appario Retail"
    }
  }
]
```

**Error Responses:**
- `400 Bad Request`: Color is blank or invalid
- `404 Not Found`: No inventory found for the specified color

---

### 3. Get Inventory by Size

**Endpoint:** `GET /api/v1/inventory/size/{size}`

**Description:** Retrieves all inventory items for a specific size.

**Request Parameters:**
- `size` (path parameter): The size to search for
  - Type: String
  - Required: Yes
  - Validation: Cannot be blank

**Example Request:**
```bash
GET http://localhost:8080/api/v1/inventory/size/S
```

**Success Response (200 OK):**
```json
[
  {
    "inventoryId": 4003,
    "brandName": "Nike",
    "price": 100.0,
    "color": "Grey",
    "size": "S",
    "skuId": "sku_103",
    "quantity": 3,
    "product": {
      "productId": 3000,
      "productName": "Shirt"
    },
    "supplier": {
      "supplierId": 1002,
      "supplierName": "Retailnet"
    }
  },
  {
    "inventoryId": 4007,
    "brandName": "Reebok",
    "price": 670.0,
    "color": "Black",
    "size": "S",
    "skuId": "sku_107",
    "quantity": 11,
    "product": {
      "productId": 3002,
      "productName": "Socks"
    },
    "supplier": {
      "supplierId": 1003,
      "supplierName": "Appario Retail"
    }
  },
  {
    "inventoryId": 4008,
    "brandName": "Nike",
    "price": 890.0,
    "color": "Green",
    "size": "S",
    "skuId": "sku_108",
    "quantity": 13,
    "product": {
      "productId": 3002,
      "productName": "Socks"
    },
    "supplier": {
      "supplierId": 1002,
      "supplierName": "Retailnet"
    }
  }
]
```

**Error Responses:**
- `400 Bad Request`: Size is blank or invalid
- `404 Not Found`: No inventory found for the specified size

---

### 4. Get Available Product Count by Seller

**Endpoint:** `GET /api/v1/inventory/seller/{sellerId}`

**Description:** Retrieves the total number of available products for a specific seller.

**Request Parameters:**
- `sellerId` (path parameter): The seller ID to search for
  - Type: String
  - Required: Yes
  - Validation: Cannot be blank

**Example Request:**
```bash
GET http://localhost:8080/api/v1/inventory/seller/1000
```

**Success Response (200 OK):**
```json
3
```

**Error Responses:**
- `400 Bad Request`: Seller ID is blank or invalid

---

## Testing Examples

### PowerShell Testing Commands

#### Test Brand Endpoint
```powershell
$response = [System.Net.WebRequest]::Create("http://localhost:8080/api/v1/inventory/brand/Nike").GetResponse()
$stream = $response.GetResponseStream()
$reader = New-Object System.IO.StreamReader($stream)
$reader.ReadToEnd()
```

#### Test Color Endpoint
```powershell
$response = [System.Net.WebRequest]::Create("http://localhost:8080/api/v1/inventory/color/Black").GetResponse()
$stream = $response.GetResponseStream()
$reader = New-Object System.IO.StreamReader($stream)
$reader.ReadToEnd()
```

#### Test Size Endpoint
```powershell
$response = [System.Net.WebRequest]::Create("http://localhost:8080/api/v1/inventory/size/S").GetResponse()
$stream = $response.GetResponseStream()
$reader = New-Object System.IO.StreamReader($stream)
$reader.ReadToEnd()
```

#### Test Seller Endpoint
```powershell
$response = [System.Net.WebRequest]::Create("http://localhost:8080/api/v1/inventory/seller/1000").GetResponse()
$stream = $response.GetResponseStream()
$reader = New-Object System.IO.StreamReader($stream)
$reader.ReadToEnd()
```

### cURL Testing Commands

#### Test Brand Endpoint
```bash
curl -X GET "http://localhost:8080/api/v1/inventory/brand/Nike" \
     -H "Content-Type: application/json"
```

#### Test Color Endpoint
```bash
curl -X GET "http://localhost:8080/api/v1/inventory/color/Black" \
     -H "Content-Type: application/json"
```

#### Test Size Endpoint
```bash
curl -X GET "http://localhost:8080/api/v1/inventory/size/S" \
     -H "Content-Type: application/json"
```

#### Test Seller Endpoint
```bash
curl -X GET "http://localhost:8080/api/v1/inventory/seller/1000" \
     -H "Content-Type: application/json"
```

---

## Sample Data

The application is pre-populated with the following sample data:

### Products
- ID: 3000, Name: Shirt
- ID: 3001, Name: Pant
- ID: 3002, Name: Socks
- ID: 3003, Name: Caps

### Suppliers
- ID: 1000, Name: CloudTail
- ID: 1001, Name: WS Retail
- ID: 1002, Name: Retailnet
- ID: 1003, Name: Appario Retail

### Inventory Items
The database contains 10 inventory items with various combinations of brands (Nike, Invictus, Peter England, Wrangler, Reebok, Puma), colors, sizes, and quantities.

---

## Error Handling Test Cases

### Validation Errors
```bash
# Empty brand name
GET http://localhost:8080/api/v1/inventory/brand/
# Expected: 400 Bad Request
```

### Business Logic Errors
```bash
# Non-existent brand
GET http://localhost:8080/api/v1/inventory/brand/NonExistentBrand
# Expected: 404 Not Found with custom error message
```

---

## Performance Considerations

- All endpoints use `@Transactional(readOnly = true)` for optimal performance
- JPA relationships are configured with `FetchType.LAZY` to prevent unnecessary data loading
- Database queries are optimized using derived queries where possible
- DTO pattern prevents entity exposure and reduces response size

---

## Security Notes

- Input validation is implemented on all path parameters
- SQL injection protection via JPA parameter binding
- No sensitive entity information is exposed in API responses
- Error messages are sanitized for production use

---

## Version Information

- API Version: v1
- Spring Boot Version: 4.0.1
- Java Version: 17
- Database: H2 (In-memory)

---

*Last Updated: April 16, 2026*
