package com.ravi.ecommerce.controller;

import java.util.List;

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
public class EcommerceAppController {

	@Autowired
	private EcommerceAppService ecommerceAppService;

	@Autowired
	private InventoryMapper inventoryMapper;

	@GetMapping("/brand/{brandName}")
	public ResponseEntity<List<InventoryDto>> getInventoryDetailsByBrand(
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
	public ResponseEntity<List<InventoryDto>> getInventoryDetailsByColor(
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
	public ResponseEntity<List<InventoryDto>> getInventoryDetailsBySize(
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
	public ResponseEntity<Integer> getAvailableNumberOfProductBySeller(
			@PathVariable @NotBlank(message = "Seller ID cannot be blank") String sellerId) {
		log.info("Fetching available product count for seller: {}", sellerId);
		int availableNumberOfProduct = ecommerceAppService.getAvailableNumberOfProductBySeller(sellerId);

		return ResponseEntity.ok(availableNumberOfProduct);
	}

	@PostMapping
	public ResponseEntity<InventoryDto> createInventory(
			@RequestBody @Valid InventoryCreateDto inventoryCreateDto) {
		log.info("Creating new inventory item: {}", inventoryCreateDto.getSkuId());
		Inventory createdInventory = ecommerceAppService.createInventory(inventoryCreateDto);
		InventoryDto inventoryDto = inventoryMapper.toDto(createdInventory);
		return ResponseEntity.status(HttpStatus.CREATED).body(inventoryDto);
	}

	@PutMapping("/{inventoryId}")
	public ResponseEntity<InventoryDto> updateInventory(
			@PathVariable Long inventoryId,
			@RequestBody @Valid InventoryUpdateDto inventoryUpdateDto) {
		log.info("Updating inventory item with ID: {}", inventoryId);
		Inventory updatedInventory = ecommerceAppService.updateInventory(inventoryId, inventoryUpdateDto);
		InventoryDto inventoryDto = inventoryMapper.toDto(updatedInventory);
		return ResponseEntity.ok(inventoryDto);
	}

	@DeleteMapping("/{inventoryId}")
	public ResponseEntity<Void> deleteInventory(@PathVariable Long inventoryId) {
		log.info("Deleting inventory item with ID: {}", inventoryId);
		ecommerceAppService.deleteInventory(inventoryId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{inventoryId}")
	public ResponseEntity<InventoryDto> getInventoryById(@PathVariable Long inventoryId) {
		log.info("Fetching inventory item with ID: {}", inventoryId);
		Inventory inventory = ecommerceAppService.getInventoryById(inventoryId);
		InventoryDto inventoryDto = inventoryMapper.toDto(inventory);
		return ResponseEntity.ok(inventoryDto);
	}

	// Projection endpoints
	
	@GetMapping("/projections/basic")
	public ResponseEntity<List<InventoryBasicProjection>> getAllBasicProjections() {
		log.info("Fetching all basic projections");
		List<InventoryBasicProjection> projections = ecommerceAppService.getAllBasicProjections();
		return ResponseEntity.ok(projections);
	}
	
	@GetMapping("/projections/basic/brand/{brandName}")
	public ResponseEntity<List<InventoryBasicProjection>> getBasicProjectionsByBrand(
			@PathVariable @NotBlank(message = "Brand name cannot be blank") String brandName) {
		log.info("Fetching basic projections for brand: {}", brandName);
		List<InventoryBasicProjection> projections = ecommerceAppService.getBasicProjectionsByBrand(brandName);
		return ResponseEntity.ok(projections);
	}
	
	@GetMapping("/projections/price")
	public ResponseEntity<List<InventoryPriceProjection>> getAllPriceProjections() {
		log.info("Fetching all price projections");
		List<InventoryPriceProjection> projections = ecommerceAppService.getAllPriceProjections();
		return ResponseEntity.ok(projections);
	}
	
	@GetMapping("/projections/price/brand/{brandName}")
	public ResponseEntity<List<InventoryPriceProjection>> getPriceProjectionsByBrand(
			@PathVariable @NotBlank(message = "Brand name cannot be blank") String brandName) {
		log.info("Fetching price projections for brand: {}", brandName);
		List<InventoryPriceProjection> projections = ecommerceAppService.getPriceProjectionsByBrand(brandName);
		return ResponseEntity.ok(projections);
	}
	
	@GetMapping("/projections/summary")
	public ResponseEntity<List<InventorySummaryProjection>> getAllSummaryProjections() {
		log.info("Fetching all summary projections");
		List<InventorySummaryProjection> projections = ecommerceAppService.getAllSummaryProjections();
		return ResponseEntity.ok(projections);
	}
	
	@GetMapping("/projections/summary/brand/{brandName}")
	public ResponseEntity<List<InventorySummaryProjection>> getSummaryProjectionsByBrand(
			@PathVariable @NotBlank(message = "Brand name cannot be blank") String brandName) {
		log.info("Fetching summary projections for brand: {}", brandName);
		List<InventorySummaryProjection> projections = ecommerceAppService.getSummaryProjectionsByBrand(brandName);
		return ResponseEntity.ok(projections);
	}

}
