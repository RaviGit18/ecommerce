# Test script for error scenarios and edge cases

$baseUrl = "http://localhost:8080/api/v1/inventory"

Write-Host "=== Testing Error Scenarios and Edge Cases ===" -ForegroundColor Green

# 1. Test creating duplicate SKU
Write-Host "`n1. Testing duplicate SKU creation..." -ForegroundColor Yellow
$duplicateItem = @{
    brandName = "Nike"
    price = 99.99
    color = "Red"
    size = "M"
    skuId = "NIKE-RED-M-001"  # This SKU already exists
    quantity = 50
    productId = 1
    supplierId = 1
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri "$baseUrl" -Method POST -ContentType "application/json" -Body $duplicateItem
    Write-Host "ERROR: Duplicate SKU was allowed!" -ForegroundColor Red
} catch {
    Write-Host "SUCCESS: Duplicate SKU properly rejected - Status: $($_.Exception.Response.StatusCode)" -ForegroundColor Green
}

# 2. Test invalid data (negative price)
Write-Host "`n2. Testing invalid data (negative price)..." -ForegroundColor Yellow
$invalidItem = @{
    brandName = "Test"
    price = -10.99
    color = "Red"
    size = "M"
    skuId = "TEST-NEG-PRICE"
    quantity = 10
    productId = 1
    supplierId = 1
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri "$baseUrl" -Method POST -ContentType "application/json" -Body $invalidItem
    Write-Host "ERROR: Negative price was allowed!" -ForegroundColor Red
} catch {
    Write-Host "SUCCESS: Negative price properly rejected - Status: $($_.Exception.Response.StatusCode)" -ForegroundColor Green
}

# 3. Test missing required fields
Write-Host "`n3. Testing missing required fields..." -ForegroundColor Yellow
$incompleteItem = @{
    brandName = ""
    price = 99.99
    color = "Red"
    size = "M"
    skuId = "TEST-INCOMPLETE"
    quantity = 10
    productId = 1
    supplierId = 1
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri "$baseUrl" -Method POST -ContentType "application/json" -Body $incompleteItem
    Write-Host "ERROR: Empty brand name was allowed!" -ForegroundColor Red
} catch {
    Write-Host "SUCCESS: Empty brand name properly rejected - Status: $($_.Exception.Response.StatusCode)" -ForegroundColor Green
}

# 4. Test GET non-existent item
Write-Host "`n4. Testing GET non-existent item..." -ForegroundColor Yellow
try {
    $item = Invoke-RestMethod -Uri "$baseUrl/99999" -Method GET
    Write-Host "ERROR: Non-existent item was found!" -ForegroundColor Red
} catch {
    Write-Host "SUCCESS: Non-existent item properly rejected - Status: $($_.Exception.Response.StatusCode)" -ForegroundColor Green
}

# 5. Test UPDATE non-existent item
Write-Host "`n5. Testing UPDATE non-existent item..." -ForegroundColor Yellow
$updateData = @{
    price = 199.99
} | ConvertTo-Json

try {
    $updated = Invoke-RestMethod -Uri "$baseUrl/99999" -Method PUT -ContentType "application/json" -Body $updateData
    Write-Host "ERROR: Non-existent item update was allowed!" -ForegroundColor Red
} catch {
    Write-Host "SUCCESS: Non-existent item update properly rejected - Status: $($_.Exception.Response.StatusCode)" -ForegroundColor Green
}

# 6. Test DELETE non-existent item
Write-Host "`n6. Testing DELETE non-existent item..." -ForegroundColor Yellow
try {
    Invoke-RestMethod -Uri "$baseUrl/99999" -Method DELETE
    Write-Host "ERROR: Non-existent item deletion was allowed!" -ForegroundColor Red
} catch {
    Write-Host "SUCCESS: Non-existent item deletion properly rejected - Status: $($_.Exception.Response.StatusCode)" -ForegroundColor Green
}

# 7. Test search with non-existent brand
Write-Host "`n7. Testing search with non-existent brand..." -ForegroundColor Yellow
try {
    $items = Invoke-RestMethod -Uri "$baseUrl/brand/NonExistentBrand" -Method GET
    Write-Host "Found $($items.Count) items for non-existent brand (should be 0)" -ForegroundColor Green
} catch {
    Write-Host "Error searching non-existent brand: $($_.Exception.Message)" -ForegroundColor Red
}

# 8. Test pagination with invalid parameters
Write-Host "`n8. Testing pagination with invalid parameters..." -ForegroundColor Yellow
try {
    $paginated = Invoke-RestMethod -Uri "$baseUrl/paginated?page=-1&size=0" -Method GET
    Write-Host "Pagination with invalid params returned: Page $($paginated.number) of $($paginated.totalPages)" -ForegroundColor Yellow
} catch {
    Write-Host "Error with invalid pagination params: $($_.Exception.Message)" -ForegroundColor Red
}

# 9. Test projection endpoints with non-existent brand
Write-Host "`n9. Testing projections with non-existent brand..." -ForegroundColor Yellow
try {
    $projections = Invoke-RestMethod -Uri "$baseUrl/projections/basic/brand/NonExistentBrand" -Method GET
    Write-Host "Found $($projections.Count) basic projections for non-existent brand" -ForegroundColor Green
} catch {
    Write-Host "Error getting projections for non-existent brand: $($_.Exception.Message)" -ForegroundColor Red
}

# 10. Investigate seller product count issue
Write-Host "`n10. Investigating seller product count..." -ForegroundColor Yellow

# First, let's create an item with a specific supplier ID
$sellerItem = @{
    brandName = "TestBrand"
    price = 50.99
    color = "Green"
    size = "L"
    skuId = "TEST-SELLER-001"
    quantity = 15
    productId = 1
    supplierId = 123  # Specific seller ID
} | ConvertTo-Json

try {
    $created = Invoke-RestMethod -Uri "$baseUrl" -Method POST -ContentType "application/json" -Body $sellerItem
    Write-Host "Created test item for seller 123: ID = $($created.inventoryId)" -ForegroundColor Green
    
    # Now check seller product count
    $count = Invoke-RestMethod -Uri "$baseUrl/seller/123" -Method GET
    Write-Host "Seller 123 product count: $count" -ForegroundColor Green
    
    # Also check seller 1 count
    $count1 = Invoke-RestMethod -Uri "$baseUrl/seller/1" -Method GET
    Write-Host "Seller 1 product count: $count1" -ForegroundColor Green
    
} catch {
    Write-Host "Error investigating seller count: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n=== Error Scenario Testing Complete ===" -ForegroundColor Green
