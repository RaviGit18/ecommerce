# Test script for E-commerce APIs

# Base URL
$baseUrl = "http://localhost:8080/api/v1/inventory"

Write-Host "=== Testing E-commerce APIs ===" -ForegroundColor Green

# 1. Create inventory items
Write-Host "`n1. Creating inventory items..." -ForegroundColor Yellow

$item1 = @{
    brandName = "Nike"
    price = 99.99
    color = "Red"
    size = "M"
    skuId = "NIKE-RED-M-001"
    quantity = 50
    productId = 1
    supplierId = 1
} | ConvertTo-Json

$item2 = @{
    brandName = "Adidas"
    price = 89.99
    color = "Blue"
    size = "L"
    skuId = "ADI-BLU-L-001"
    quantity = 30
    productId = 2
    supplierId = 2
} | ConvertTo-Json

$item3 = @{
    brandName = "Nike"
    price = 109.99
    color = "Black"
    size = "S"
    skuId = "NIKE-BLK-S-001"
    quantity = 25
    productId = 3
    supplierId = 1
} | ConvertTo-Json

try {
    $response1 = Invoke-RestMethod -Uri "$baseUrl" -Method POST -ContentType "application/json" -Body $item1
    Write-Host "Created item 1: ID = $($response1.inventoryId)" -ForegroundColor Green
} catch {
    Write-Host "Error creating item 1: $($_.Exception.Message)" -ForegroundColor Red
}

try {
    $response2 = Invoke-RestMethod -Uri "$baseUrl" -Method POST -ContentType "application/json" -Body $item2
    Write-Host "Created item 2: ID = $($response2.inventoryId)" -ForegroundColor Green
} catch {
    Write-Host "Error creating item 2: $($_.Exception.Message)" -ForegroundColor Red
}

try {
    $response3 = Invoke-RestMethod -Uri "$baseUrl" -Method POST -ContentType "application/json" -Body $item3
    Write-Host "Created item 3: ID = $($response3.inventoryId)" -ForegroundColor Green
} catch {
    Write-Host "Error creating item 3: $($_.Exception.Message)" -ForegroundColor Red
}

# 2. Test GET by ID
Write-Host "`n2. Testing GET by ID..." -ForegroundColor Yellow
if ($response1) {
    try {
        $item = Invoke-RestMethod -Uri "$baseUrl/$($response1.inventoryId)" -Method GET
        Write-Host "Retrieved item: $($item.brandName) - $($item.color)" -ForegroundColor Green
    } catch {
        Write-Host "Error retrieving item: $($_.Exception.Message)" -ForegroundColor Red
    }
}

# 3. Test search by brand
Write-Host "`n3. Testing search by brand..." -ForegroundColor Yellow
try {
    $items = Invoke-RestMethod -Uri "$baseUrl/brand/Nike" -Method GET
    Write-Host "Found $($items.Count) Nike items" -ForegroundColor Green
    $items | ForEach-Object { Write-Host "  - $($_.brandName) $($_.color) $($_.size) - $($_.price)" -ForegroundColor Cyan }
} catch {
    Write-Host "Error searching by brand: $($_.Exception.Message)" -ForegroundColor Red
}

# 4. Test search by color
Write-Host "`n4. Testing search by color..." -ForegroundColor Yellow
try {
    $items = Invoke-RestMethod -Uri "$baseUrl/color/Red" -Method GET
    Write-Host "Found $($items.Count) Red items" -ForegroundColor Green
    $items | ForEach-Object { Write-Host "  - $($_.brandName) $($_.color) $($_.size) - $($_.price)" -ForegroundColor Cyan }
} catch {
    Write-Host "Error searching by color: $($_.Exception.Message)" -ForegroundColor Red
}

# 5. Test search by size
Write-Host "`n5. Testing search by size..." -ForegroundColor Yellow
try {
    $items = Invoke-RestMethod -Uri "$baseUrl/size/M" -Method GET
    Write-Host "Found $($items.Count) M size items" -ForegroundColor Green
    $items | ForEach-Object { Write-Host "  - $($_.brandName) $($_.color) $($_.size) - $($_.price)" -ForegroundColor Cyan }
} catch {
    Write-Host "Error searching by size: $($_.Exception.Message)" -ForegroundColor Red
}

# 6. Test seller product count
Write-Host "`n6. Testing seller product count..." -ForegroundColor Yellow
try {
    $count = Invoke-RestMethod -Uri "$baseUrl/seller/1" -Method GET
    Write-Host "Seller 1 has $count available products" -ForegroundColor Green
} catch {
    Write-Host "Error getting seller product count: $($_.Exception.Message)" -ForegroundColor Red
}

# 7. Test projections
Write-Host "`n7. Testing projections..." -ForegroundColor Yellow

# Basic projections
try {
    $projections = Invoke-RestMethod -Uri "$baseUrl/projections/basic" -Method GET
    Write-Host "Basic projections: $($projections.Count) items" -ForegroundColor Green
} catch {
    Write-Host "Error getting basic projections: $($_.Exception.Message)" -ForegroundColor Red
}

# Price projections
try {
    $projections = Invoke-RestMethod -Uri "$baseUrl/projections/price" -Method GET
    Write-Host "Price projections: $($projections.Count) items" -ForegroundColor Green
} catch {
    Write-Host "Error getting price projections: $($_.Exception.Message)" -ForegroundColor Red
}

# Summary projections
try {
    $projections = Invoke-RestMethod -Uri "$baseUrl/projections/summary" -Method GET
    Write-Host "Summary projections: $($projections.Count) items" -ForegroundColor Green
} catch {
    Write-Host "Error getting summary projections: $($_.Exception.Message)" -ForegroundColor Red
}

# 8. Test pagination
Write-Host "`n8. Testing pagination..." -ForegroundColor Yellow
try {
    $paginated = Invoke-RestMethod -Uri "$baseUrl/paginated?page=0&size=2" -Method GET
    Write-Host "Paginated results: Page $($paginated.number) of $($paginated.totalPages), Total items: $($paginated.totalElements)" -ForegroundColor Green
    $paginated.content | ForEach-Object { Write-Host "  - $($_.brandName) $($_.color) $($_.size) - $($_.price)" -ForegroundColor Cyan }
} catch {
    Write-Host "Error with pagination: $($_.Exception.Message)" -ForegroundColor Red
}

# 9. Test update
Write-Host "`n9. Testing update..." -ForegroundColor Yellow
if ($response1) {
    $updateData = @{
        price = 119.99
        quantity = 45
    } | ConvertTo-Json
    
    try {
        $updated = Invoke-RestMethod -Uri "$baseUrl/$($response1.inventoryId)" -Method PUT -ContentType "application/json" -Body $updateData
        Write-Host "Updated item: New price = $($updated.price), New quantity = $($updated.quantity)" -ForegroundColor Green
    } catch {
        Write-Host "Error updating item: $($_.Exception.Message)" -ForegroundColor Red
    }
}

# 10. Test delete
Write-Host "`n10. Testing delete..." -ForegroundColor Yellow
if ($response3) {
    try {
        Invoke-RestMethod -Uri "$baseUrl/$($response3.inventoryId)" -Method DELETE
        Write-Host "Deleted item 3 successfully" -ForegroundColor Green
    } catch {
        Write-Host "Error deleting item: $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host "`n=== API Testing Complete ===" -ForegroundColor Green
