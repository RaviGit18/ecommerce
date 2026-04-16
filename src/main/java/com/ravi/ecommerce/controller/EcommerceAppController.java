package com.ravi.ecommerce.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.ravi.ecommerce.dto.InventoryCreateDto;
import com.ravi.ecommerce.dto.InventoryDto;
import com.ravi.ecommerce.dto.InventoryUpdateDto;
import com.ravi.ecommerce.dto.PagedResponse;
import com.ravi.ecommerce.entity.Inventory;
import com.ravi.ecommerce.mapper.InventoryMapper;
import com.ravi.ecommerce.projection.InventoryBasicProjection;
import com.ravi.ecommerce.projection.InventoryPriceProjection;
import com.ravi.ecommerce.projection.InventorySummaryProjection;
import com.ravi.ecommerce.service.EcommerceAppService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/inventory")
@Validated
@Tag(name = "Inventory Management", description = "APIs for managing e-commerce inventory items")
public class EcommerceAppController {

	@Autowired
	private EcommerceAppService ecommerceAppService;

	@Autowired
	private InventoryMapper inventoryMapper;

	@GetMapping("/brand/{brandName}")
	@Operation(summary = "Get inventory by brand", description = "Retrieve all inventory items for a specific brand")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved inventory items"),
		@ApiResponse(responseCode = "204", description = "No inventory items found for the brand"),
		@ApiResponse(responseCode = "400", description = "Invalid brand name provided")
	})
	public ResponseEntity<List<InventoryDto>> getInventoryDetailsByBrand(
			@Parameter(description = "Brand name to search for", required = true, example = "Nike")
			@PathVariable @NotBlank(message = "Brand name cannot be blank") String brandName) {
		log.info("Fetching inventory details for brand: {}", brandName);
		List<Inventory> inventoryList = ecommerceAppService.getInventoryDetailsByBrand(brandName);
		
		if (inventoryList.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		List<InventoryDto> inventoryDtoList = inventoryMapper.toDtoList(inventoryList);
		return ResponseEntity.ok(inventoryDtoList);
	}

	@GetMapping("/color/{color}")
	@Operation(summary = "Get inventory by color", description = "Retrieve all inventory items for a specific color")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved inventory items"),
		@ApiResponse(responseCode = "204", description = "No inventory items found for the color"),
		@ApiResponse(responseCode = "400", description = "Invalid color provided")
	})
	public ResponseEntity<List<InventoryDto>> getInventoryDetailsByColor(
			@Parameter(description = "Color to search for", required = true, example = "Red")
			@PathVariable @NotBlank(message = "Color cannot be blank") String color) {
		log.info("Fetching inventory details for color: {}", color);
		List<Inventory> inventoryList = ecommerceAppService.getInventoryDetailsByColor(color);

		if (inventoryList.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<InventoryDto> inventoryDtoList = inventoryMapper.toDtoList(inventoryList);
		return ResponseEntity.ok(inventoryDtoList);
	}

	@GetMapping("/size/{size}")
	@Operation(summary = "Get inventory by size", description = "Retrieve all inventory items for a specific size")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved inventory items"),
		@ApiResponse(responseCode = "204", description = "No inventory items found for the size"),
		@ApiResponse(responseCode = "400", description = "Invalid size provided")
	})
	public ResponseEntity<List<InventoryDto>> getInventoryDetailsBySize(
			@Parameter(description = "Size to search for", required = true, example = "M")
			@PathVariable @NotBlank(message = "Size cannot be blank") String size) {
		log.info("Fetching inventory details for size: {}", size);
		List<Inventory> inventoryList = ecommerceAppService.getInventoryDetailsBySize(size);

		if (inventoryList.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<InventoryDto> inventoryDtoList = inventoryMapper.toDtoList(inventoryList);
		return ResponseEntity.ok(inventoryDtoList);
	}

	@GetMapping("/seller/{sellerId}")
	@Operation(summary = "Get product count by seller", description = "Get the total number of available products for a specific seller")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved product count"),
		@ApiResponse(responseCode = "400", description = "Invalid seller ID provided")
	})
	public ResponseEntity<Integer> getAvailableNumberOfProductBySeller(
			@Parameter(description = "Seller ID to search for", required = true, example = "1")
			@PathVariable @NotBlank(message = "Seller ID cannot be blank") String sellerId) {
		log.info("Fetching available product count for seller: {}", sellerId);
		int availableNumberOfProduct = ecommerceAppService.getAvailableNumberOfProductBySeller(sellerId);

		return ResponseEntity.ok(availableNumberOfProduct);
	}

	@PostMapping
	@Operation(summary = "Create inventory item", description = "Create a new inventory item in the system")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Inventory item created successfully"),
		@ApiResponse(responseCode = "400", description = "Invalid inventory data provided"),
		@ApiResponse(responseCode = "409", description = "Inventory with SKU ID already exists")
	})
	public ResponseEntity<InventoryDto> createInventory(
			@Parameter(description = "Inventory item data to create", required = true)
			@RequestBody @Valid InventoryCreateDto inventoryCreateDto) {
		log.info("Creating new inventory item: {}", inventoryCreateDto.getSkuId());
		Inventory createdInventory = ecommerceAppService.createInventory(inventoryCreateDto);
		InventoryDto inventoryDto = inventoryMapper.toDto(createdInventory);
		return ResponseEntity.status(HttpStatus.CREATED).body(inventoryDto);
	}

	@PutMapping("/{inventoryId}")
	@Operation(summary = "Update inventory item", description = "Update an existing inventory item")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Inventory item updated successfully"),
		@ApiResponse(responseCode = "400", description = "Invalid inventory data provided"),
		@ApiResponse(responseCode = "404", description = "Inventory item not found")
	})
	public ResponseEntity<InventoryDto> updateInventory(
			@Parameter(description = "ID of the inventory item to update", required = true, example = "1")
			@PathVariable Long inventoryId,
			@Parameter(description = "Updated inventory item data", required = true)
			@RequestBody @Valid InventoryUpdateDto inventoryUpdateDto) {
		log.info("Updating inventory item with ID: {}", inventoryId);
		Inventory updatedInventory = ecommerceAppService.updateInventory(inventoryId, inventoryUpdateDto);
		InventoryDto inventoryDto = inventoryMapper.toDto(updatedInventory);
		return ResponseEntity.ok(inventoryDto);
	}

	@DeleteMapping("/{inventoryId}")
	@Operation(summary = "Delete inventory item", description = "Delete an inventory item from the system")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Inventory item deleted successfully"),
		@ApiResponse(responseCode = "404", description = "Inventory item not found")
	})
	public ResponseEntity<Void> deleteInventory(
			@Parameter(description = "ID of the inventory item to delete", required = true, example = "1")
			@PathVariable Long inventoryId) {
		log.info("Deleting inventory item with ID: {}", inventoryId);
		ecommerceAppService.deleteInventory(inventoryId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{inventoryId}")
	@Operation(summary = "Get inventory by ID", description = "Retrieve a specific inventory item by its ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved inventory item"),
		@ApiResponse(responseCode = "404", description = "Inventory item not found")
	})
	public ResponseEntity<InventoryDto> getInventoryById(
			@Parameter(description = "ID of the inventory item to retrieve", required = true, example = "1")
			@PathVariable Long inventoryId) {
		log.info("Fetching inventory item with ID: {}", inventoryId);
		Inventory inventory = ecommerceAppService.getInventoryById(inventoryId);
		InventoryDto inventoryDto = inventoryMapper.toDto(inventory);
		return ResponseEntity.ok(inventoryDto);
	}

	// Projection endpoints
	
	@GetMapping("/projections/basic")
	@Operation(summary = "Get all basic projections", description = "Retrieve all inventory items with basic projection fields")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved basic projections")
	})
	public ResponseEntity<List<InventoryBasicProjection>> getAllBasicProjections() {
		log.info("Fetching all basic projections");
		List<InventoryBasicProjection> projections = ecommerceAppService.getAllBasicProjections();
		return ResponseEntity.ok(projections);
	}
	
	@GetMapping("/projections/basic/brand/{brandName}")
	@Operation(summary = "Get basic projections by brand", description = "Retrieve basic projections for inventory items of a specific brand")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved basic projections"),
		@ApiResponse(responseCode = "400", description = "Invalid brand name provided")
	})
	public ResponseEntity<List<InventoryBasicProjection>> getBasicProjectionsByBrand(
			@Parameter(description = "Brand name to search for", required = true, example = "Nike")
			@PathVariable @NotBlank(message = "Brand name cannot be blank") String brandName) {
		log.info("Fetching basic projections for brand: {}", brandName);
		List<InventoryBasicProjection> projections = ecommerceAppService.getBasicProjectionsByBrand(brandName);
		return ResponseEntity.ok(projections);
	}
	
	@GetMapping("/projections/price")
	@Operation(summary = "Get all price projections", description = "Retrieve all inventory items with price projection fields")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved price projections")
	})
	public ResponseEntity<List<InventoryPriceProjection>> getAllPriceProjections() {
		log.info("Fetching all price projections");
		List<InventoryPriceProjection> projections = ecommerceAppService.getAllPriceProjections();
		return ResponseEntity.ok(projections);
	}
	
	@GetMapping("/projections/price/brand/{brandName}")
	@Operation(summary = "Get price projections by brand", description = "Retrieve price projections for inventory items of a specific brand")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved price projections"),
		@ApiResponse(responseCode = "400", description = "Invalid brand name provided")
	})
	public ResponseEntity<List<InventoryPriceProjection>> getPriceProjectionsByBrand(
			@Parameter(description = "Brand name to search for", required = true, example = "Nike")
			@PathVariable @NotBlank(message = "Brand name cannot be blank") String brandName) {
		log.info("Fetching price projections for brand: {}", brandName);
		List<InventoryPriceProjection> projections = ecommerceAppService.getPriceProjectionsByBrand(brandName);
		return ResponseEntity.ok(projections);
	}
	
	@GetMapping("/projections/summary")
	@Operation(summary = "Get all summary projections", description = "Retrieve all inventory items with summary projection fields")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved summary projections")
	})
	public ResponseEntity<List<InventorySummaryProjection>> getAllSummaryProjections() {
		log.info("Fetching all summary projections");
		List<InventorySummaryProjection> projections = ecommerceAppService.getAllSummaryProjections();
		return ResponseEntity.ok(projections);
	}
	
	@GetMapping("/projections/summary/brand/{brandName}")
	@Operation(summary = "Get summary projections by brand", description = "Retrieve summary projections for inventory items of a specific brand")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved summary projections"),
		@ApiResponse(responseCode = "400", description = "Invalid brand name provided")
	})
	public ResponseEntity<List<InventorySummaryProjection>> getSummaryProjectionsByBrand(
			@Parameter(description = "Brand name to search for", required = true, example = "Nike")
			@PathVariable @NotBlank(message = "Brand name cannot be blank") String brandName) {
		log.info("Fetching summary projections for brand: {}", brandName);
		List<InventorySummaryProjection> projections = ecommerceAppService.getSummaryProjectionsByBrand(brandName);
		return ResponseEntity.ok(projections);
	}

	// Pagination endpoints
	
	@GetMapping("/paginated")
	@Operation(summary = "Get all inventory with pagination", description = "Retrieve all inventory items with pagination support")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved paginated inventory items")
	})
	public ResponseEntity<Page<InventoryDto>> getAllInventoryPaginated(
			@Parameter(description = "Page number (0-based)", example = "0")
			@RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Number of items per page", example = "10")
			@RequestParam(defaultValue = "10") int size,
			@Parameter(description = "Field to sort by", example = "inventoryId")
			@RequestParam(defaultValue = "inventoryId") String sortBy,
			@Parameter(description = "Sort direction (asc/desc)", example = "asc")
			@RequestParam(defaultValue = "asc") String sortDir) {
		log.info("Fetching all inventory items with pagination - page: {}, size: {}, sort: {} {}", 
			page, size, sortBy, sortDir);
		
		Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
		Page<Inventory> inventoryPage = ecommerceAppService.getAllInventoryPaginated(pageable);
		
		Page<InventoryDto> dtoPage = inventoryPage.map(inventoryMapper::toDto);
		return ResponseEntity.ok(dtoPage);
	}
	
	@GetMapping("/paginated/brand/{brandName}")
	@Operation(summary = "Get inventory by brand with pagination", description = "Retrieve inventory items for a specific brand with pagination support")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved paginated inventory items"),
		@ApiResponse(responseCode = "400", description = "Invalid brand name provided")
	})
	public ResponseEntity<Page<InventoryDto>> getInventoryByBrandPaginated(
			@Parameter(description = "Brand name to search for", required = true, example = "Nike")
			@PathVariable @NotBlank(message = "Brand name cannot be blank") String brandName,
			@Parameter(description = "Page number (0-based)", example = "0")
			@RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Number of items per page", example = "10")
			@RequestParam(defaultValue = "10") int size,
			@Parameter(description = "Field to sort by", example = "inventoryId")
			@RequestParam(defaultValue = "inventoryId") String sortBy,
			@Parameter(description = "Sort direction (asc/desc)", example = "asc")
			@RequestParam(defaultValue = "asc") String sortDir) {
		log.info("Fetching inventory items for brand: {} with pagination - page: {}, size: {}, sort: {} {}", 
			brandName, page, size, sortBy, sortDir);
		
		Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
		Page<Inventory> inventoryPage = ecommerceAppService.getInventoryByBrandPaginated(brandName, pageable);
		
		Page<InventoryDto> dtoPage = inventoryPage.map(inventoryMapper::toDto);
		return ResponseEntity.ok(dtoPage);
	}
	
	@GetMapping("/paginated/color/{color}")
	@Operation(summary = "Get inventory by color with pagination", description = "Retrieve inventory items for a specific color with pagination support")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved paginated inventory items"),
		@ApiResponse(responseCode = "400", description = "Invalid color provided")
	})
	public ResponseEntity<Page<InventoryDto>> getInventoryByColorPaginated(
			@Parameter(description = "Color to search for", required = true, example = "Red")
			@PathVariable @NotBlank(message = "Color cannot be blank") String color,
			@Parameter(description = "Page number (0-based)", example = "0")
			@RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Number of items per page", example = "10")
			@RequestParam(defaultValue = "10") int size,
			@Parameter(description = "Field to sort by", example = "inventoryId")
			@RequestParam(defaultValue = "inventoryId") String sortBy,
			@Parameter(description = "Sort direction (asc/desc)", example = "asc")
			@RequestParam(defaultValue = "asc") String sortDir) {
		log.info("Fetching inventory items for color: {} with pagination - page: {}, size: {}, sort: {} {}", 
			color, page, size, sortBy, sortDir);
		
		Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
		Page<Inventory> inventoryPage = ecommerceAppService.getInventoryByColorPaginated(color, pageable);
		
		Page<InventoryDto> dtoPage = inventoryPage.map(inventoryMapper::toDto);
		return ResponseEntity.ok(dtoPage);
	}
	
	@GetMapping("/paginated/size/{size}")
	@Operation(summary = "Get inventory by size with pagination", description = "Retrieve inventory items for a specific size with pagination support")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved paginated inventory items"),
		@ApiResponse(responseCode = "400", description = "Invalid size provided")
	})
	public ResponseEntity<Page<InventoryDto>> getInventoryBySizePaginated(
			@Parameter(description = "Size to search for", required = true, example = "M")
			@PathVariable @NotBlank(message = "Size cannot be blank") String size,
			@Parameter(description = "Page number (0-based)", example = "0")
			@RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Number of items per page", example = "10")
			@RequestParam(defaultValue = "10") int pageSize,
			@Parameter(description = "Field to sort by", example = "inventoryId")
			@RequestParam(defaultValue = "inventoryId") String sortBy,
			@Parameter(description = "Sort direction (asc/desc)", example = "asc")
			@RequestParam(defaultValue = "asc") String sortDir) {
		log.info("Fetching inventory items for size: {} with pagination - page: {}, size: {}, sort: {} {}", 
			size, page, pageSize, sortBy, sortDir);
		
		Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(page, pageSize, Sort.by(direction, sortBy));
		Page<Inventory> inventoryPage = ecommerceAppService.getInventoryBySizePaginated(size, pageable);
		
		Page<InventoryDto> dtoPage = inventoryPage.map(inventoryMapper::toDto);
		return ResponseEntity.ok(dtoPage);
	}
	
	@GetMapping("/paginated/supplier/{supplierId}")
	@Operation(summary = "Get inventory by supplier with pagination", description = "Retrieve inventory items for a specific supplier with pagination support")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Successfully retrieved paginated inventory items")
	})
	public ResponseEntity<Page<InventoryDto>> getInventoryBySupplierPaginated(
			@Parameter(description = "Supplier ID to search for", required = true, example = "1")
			@PathVariable Long supplierId,
			@Parameter(description = "Page number (0-based)", example = "0")
			@RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Number of items per page", example = "10")
			@RequestParam(defaultValue = "10") int size,
			@Parameter(description = "Field to sort by", example = "inventoryId")
			@RequestParam(defaultValue = "inventoryId") String sortBy,
			@Parameter(description = "Sort direction (asc/desc)", example = "asc")
			@RequestParam(defaultValue = "asc") String sortDir) {
		log.info("Fetching inventory items for supplier ID: {} with pagination - page: {}, size: {}, sort: {} {}", 
			supplierId, page, size, sortBy, sortDir);
		
		Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
		Page<Inventory> inventoryPage = ecommerceAppService.getInventoryBySupplierPaginated(supplierId, pageable);
		
		Page<InventoryDto> dtoPage = inventoryPage.map(inventoryMapper::toDto);
		return ResponseEntity.ok(dtoPage);
	}
	
	
}
