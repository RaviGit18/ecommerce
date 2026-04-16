package com.ravi.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ravi.ecommerce.dto.InventoryCreateDto;
import com.ravi.ecommerce.dto.InventoryDto;
import com.ravi.ecommerce.dto.InventoryUpdateDto;
import com.ravi.ecommerce.dto.PagedResponse;
import com.ravi.ecommerce.entity.Inventory;
import com.ravi.ecommerce.enums.InventoryEnum;
import com.ravi.ecommerce.exception.BaseException;
import com.ravi.ecommerce.mapper.InventoryMapper;
import com.ravi.ecommerce.projection.InventoryBasicProjection;
import com.ravi.ecommerce.projection.InventoryPriceProjection;
import com.ravi.ecommerce.projection.InventorySummaryProjection;
import com.ravi.ecommerce.repository.InventoryRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
public class EcommerceAppServiceImpl implements EcommerceAppService {

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private InventoryMapper inventoryMapper;

	@Override
	public List<Inventory> getInventoryDetailsByBrand(String brandName) {
		log.debug("Fetching inventory details for brand: {}", brandName);
		List<Inventory> inventoryList = inventoryRepository.findByBrandName(brandName);
		
		if (inventoryList.isEmpty()) {
			log.warn("No inventory found for brand: {}", brandName);
			throw new BaseException(InventoryEnum.INVENTORY_NOT_FOUND);
		}
		
		log.info("Found {} inventory items for brand: {}", inventoryList.size(), brandName);
		return inventoryList;
	}

	@Override
	public int getAvailableNumberOfProductBySeller(String sellerId) {
		log.debug("Counting available products for seller: {}", sellerId);
		int availableNumberOfProduct = inventoryRepository.getAvailableNumberOfProductBySeller(sellerId);
		
		log.info("Found {} available products for seller: {}", availableNumberOfProduct, sellerId);
		return availableNumberOfProduct;
	}

	@Override
	public List<Inventory> getInventoryDetailsBySize(String size) {
		log.debug("Fetching inventory details for size: {}", size);
		List<Inventory> inventoryList = inventoryRepository.findBySize(size);
		
		if (inventoryList.isEmpty()) {
			log.warn("No inventory found for size: {}", size);
			throw new BaseException(InventoryEnum.INVENTORY_NOT_FOUND);
		}
		
		log.info("Found {} inventory items for size: {}", inventoryList.size(), size);
		return inventoryList;
	}

	@Override
	public List<Inventory> getInventoryDetailsByColor(String color) {
		log.debug("Fetching inventory details for color: {}", color);
		List<Inventory> inventoryList = inventoryRepository.findByColor(color);
		
		if (inventoryList.isEmpty()) {
			log.warn("No inventory found for color: {}", color);
			throw new BaseException(InventoryEnum.INVENTORY_NOT_FOUND);
		}
		
		log.info("Found {} inventory items for color: {}", inventoryList.size(), color);
		return inventoryList;
	}

	@Override
	@Transactional
	public Inventory createInventory(InventoryCreateDto inventoryCreateDto) {
		log.debug("Creating new inventory item: {}", inventoryCreateDto.getSkuId());
		
		// Check if SKU ID already exists
		if (inventoryRepository.existsBySkuId(inventoryCreateDto.getSkuId())) {
			throw new BaseException(409, "Inventory with SKU ID " + inventoryCreateDto.getSkuId() + " already exists");
		}
		
		Inventory inventory = new Inventory();
		inventory.setBrandName(inventoryCreateDto.getBrandName());
		inventory.setPrice(inventoryCreateDto.getPrice());
		inventory.setColor(inventoryCreateDto.getColor());
		inventory.setSize(inventoryCreateDto.getSize());
		inventory.setSkuId(inventoryCreateDto.getSkuId());
		inventory.setQuantity(inventoryCreateDto.getQuantity());
		inventory.setProductId(inventoryCreateDto.getProductId());
		inventory.setSupplierId(inventoryCreateDto.getSupplierId());
		
		Inventory savedInventory = inventoryRepository.save(inventory);
		log.info("Created new inventory item with ID: {}", savedInventory.getInventoryId());
		return savedInventory;
	}

	@Override
	@Transactional
	public Inventory updateInventory(Long inventoryId, InventoryUpdateDto inventoryUpdateDto) {
		log.debug("Updating inventory item with ID: {}", inventoryId);
		
		Inventory existingInventory = inventoryRepository.findById(inventoryId)
				.orElseThrow(() -> new BaseException(InventoryEnum.INVENTORY_NOT_FOUND));
		
		// Update only non-null fields
		if (inventoryUpdateDto.getBrandName() != null) {
			existingInventory.setBrandName(inventoryUpdateDto.getBrandName());
		}
		if (inventoryUpdateDto.getPrice() != null) {
			existingInventory.setPrice(inventoryUpdateDto.getPrice());
		}
		if (inventoryUpdateDto.getColor() != null) {
			existingInventory.setColor(inventoryUpdateDto.getColor());
		}
		if (inventoryUpdateDto.getSize() != null) {
			existingInventory.setSize(inventoryUpdateDto.getSize());
		}
		if (inventoryUpdateDto.getSkuId() != null) {
			existingInventory.setSkuId(inventoryUpdateDto.getSkuId());
		}
		if (inventoryUpdateDto.getQuantity() != null) {
			existingInventory.setQuantity(inventoryUpdateDto.getQuantity());
		}
		if (inventoryUpdateDto.getProductId() != null) {
			existingInventory.setProductId(inventoryUpdateDto.getProductId());
		}
		if (inventoryUpdateDto.getSupplierId() != null) {
			existingInventory.setSupplierId(inventoryUpdateDto.getSupplierId());
		}
		
		Inventory updatedInventory = inventoryRepository.save(existingInventory);
		log.info("Updated inventory item with ID: {}", updatedInventory.getInventoryId());
		return updatedInventory;
	}

	@Override
	@Transactional
	public void deleteInventory(Long inventoryId) {
		log.debug("Deleting inventory item with ID: {}", inventoryId);
		
		if (!inventoryRepository.existsById(inventoryId)) {
			throw new BaseException(InventoryEnum.INVENTORY_NOT_FOUND);
		}
		
		inventoryRepository.deleteById(inventoryId);
		log.info("Deleted inventory item with ID: {}", inventoryId);
	}

	@Override
	public Inventory getInventoryById(Long inventoryId) {
		log.debug("Fetching inventory item with ID: {}", inventoryId);
		
		return inventoryRepository.findById(inventoryId)
			.orElseThrow(() -> new BaseException(InventoryEnum.INVENTORY_NOT_FOUND));
	}

	// Projection method implementations
	
	@Override
	public List<InventoryBasicProjection> getAllBasicProjections() {
		log.debug("Fetching basic projections for all inventory items");
		List<InventoryBasicProjection> projections = inventoryRepository.findAllBasicProjectedBy();
		log.info("Found {} basic projections", projections.size());
		return projections;
	}
	
	@Override
	public List<InventoryBasicProjection> getBasicProjectionsByBrand(String brandName) {
		log.debug("Fetching basic projections for brand: {}", brandName);
		List<InventoryBasicProjection> projections = inventoryRepository.findBasicProjectionsByBrandName(brandName);
		
		if (projections.isEmpty()) {
			log.warn("No inventory found for brand: {}", brandName);
			throw new BaseException(InventoryEnum.INVENTORY_NOT_FOUND);
		}
		
		log.info("Found {} basic projections for brand: {}", projections.size(), brandName);
		return projections;
	}
	
	@Override
	public List<InventoryPriceProjection> getAllPriceProjections() {
		log.debug("Fetching price projections for all inventory items");
		List<InventoryPriceProjection> projections = inventoryRepository.findAllPriceProjections();
		log.info("Found {} price projections", projections.size());
		return projections;
	}
	
	@Override
	public List<InventoryPriceProjection> getPriceProjectionsByBrand(String brandName) {
		log.debug("Fetching price projections for brand: {}", brandName);
		List<InventoryPriceProjection> projections = inventoryRepository.findPriceProjectionsByBrandName(brandName);
		
		if (projections.isEmpty()) {
			log.warn("No inventory found for brand: {}", brandName);
			throw new BaseException(InventoryEnum.INVENTORY_NOT_FOUND);
		}
		
		log.info("Found {} price projections for brand: {}", projections.size(), brandName);
		return projections;
	}
	
	@Override
	public List<InventorySummaryProjection> getAllSummaryProjections() {
		log.debug("Fetching summary projections for all inventory items");
		List<InventorySummaryProjection> projections = inventoryRepository.findAllSummaryProjections();
		log.info("Found {} summary projections", projections.size());
		return projections;
	}
	
	@Override
	public List<InventorySummaryProjection> getSummaryProjectionsByBrand(String brandName) {
		log.debug("Fetching summary projections for brand: {}", brandName);
		List<InventorySummaryProjection> projections = inventoryRepository.findSummaryProjectionsByBrandName(brandName);
		
		if (projections.isEmpty()) {
			log.warn("No inventory found for brand: {}", brandName);
			throw new BaseException(InventoryEnum.INVENTORY_NOT_FOUND);
		}
		
		log.info("Found {} summary projections for brand: {}", projections.size(), brandName);
		return projections;
	}

}
