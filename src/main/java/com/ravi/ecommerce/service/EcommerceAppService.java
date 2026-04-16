package com.ravi.ecommerce.service;

import java.util.List;

import com.ravi.ecommerce.dto.InventoryCreateDto;
import com.ravi.ecommerce.dto.InventoryDto;
import com.ravi.ecommerce.dto.InventoryUpdateDto;
import com.ravi.ecommerce.dto.PagedResponse;
import com.ravi.ecommerce.entity.Inventory;
import com.ravi.ecommerce.projection.InventoryBasicProjection;
import com.ravi.ecommerce.projection.InventoryPriceProjection;
import com.ravi.ecommerce.projection.InventorySummaryProjection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for e-commerce inventory management operations.
 */
public interface EcommerceAppService {

	/**
	 * Retrieve inventory details by brand name.
	 * 
	 * @param brandName the brand name to search for
	 * @return list of inventory items for the specified brand
	 */
	List<Inventory> getInventoryDetailsByBrand(String brandName);

	/**
	 * Get the total number of available products for a specific seller.
	 * 
	 * @param sellerId the seller ID to search for
	 * @return total count of available products for the seller
	 */
	int getAvailableNumberOfProductBySeller(String sellerId);

	/**
	 * Retrieve inventory details by size.
	 * 
	 * @param size the size to search for
	 * @return list of inventory items for the specified size
	 */
	List<Inventory> getInventoryDetailsBySize(String size);

	/**
	 * Retrieve inventory details by color.
	 * 
	 * @param color the color to search for
	 * @return list of inventory items for the specified color
	 */
	List<Inventory> getInventoryDetailsByColor(String color);

	/**
	 * Create a new inventory item.
	 * 
	 * @param inventoryCreateDto the inventory data to create
	 * @return the created inventory item
	 */
	Inventory createInventory(InventoryCreateDto inventoryCreateDto);

	/**
	 * Update an existing inventory item.
	 * 
	 * @param inventoryId the ID of the inventory item to update
	 * @param inventoryUpdateDto the updated inventory data
	 * @return the updated inventory item
	 */
	Inventory updateInventory(Long inventoryId, InventoryUpdateDto inventoryUpdateDto);

	/**
	 * Delete an inventory item by ID.
	 * 
	 * @param inventoryId the ID of the inventory item to delete
	 */
	void deleteInventory(Long inventoryId);

	/**
	 * Get inventory item by ID.
	 * 
	 * @param inventoryId the ID of the inventory item
	 * @return the inventory item
	 */
	Inventory getInventoryById(Long inventoryId);

	// Projection methods
	
	/**
	 * Get basic projection of all inventory items.
	 * 
	 * @return list of basic inventory projections
	 */
	List<InventoryBasicProjection> getAllBasicProjections();
	
	/**
	 * Get basic projection of inventory items by brand name.
	 * 
	 * @param brandName the brand name to search for
	 * @return list of basic inventory projections matching brand
	 */
	List<InventoryBasicProjection> getBasicProjectionsByBrand(String brandName);
	
	/**
	 * Get price projection of all inventory items.
	 * 
	 * @return list of price inventory projections
	 */
	List<InventoryPriceProjection> getAllPriceProjections();
	
	/**
	 * Get price projection of inventory items by brand name.
	 * 
	 * @param brandName the brand name to search for
	 * @return list of price inventory projections matching brand
	 */
	List<InventoryPriceProjection> getPriceProjectionsByBrand(String brandName);
	
	/**
	 * Get summary projection of all inventory items.
	 * 
	 * @return list of summary inventory projections
	 */
	List<InventorySummaryProjection> getAllSummaryProjections();
	
	/**
	 * Get summary projection of inventory items by brand name.
	 * 
	 * @param brandName the brand name to search for
	 * @return list of summary inventory projections matching brand
	 */
	List<InventorySummaryProjection> getSummaryProjectionsByBrand(String brandName);

	// Pagination methods
	
	/**
	 * Get all inventory items with pagination.
	 * 
	 * @param pageable pagination and sorting information
	 * @return paginated list of inventory items
	 */
	Page<Inventory> getAllInventoryPaginated(Pageable pageable);
	
	/**
	 * Get inventory items by brand name with pagination.
	 * 
	 * @param brandName the brand name to search for
	 * @param pageable pagination and sorting information
	 * @return paginated list of inventory items matching brand
	 */
	Page<Inventory> getInventoryByBrandPaginated(String brandName, Pageable pageable);
	
	/**
	 * Get inventory items by color with pagination.
	 * 
	 * @param color the color to search for
	 * @param pageable pagination and sorting information
	 * @return paginated list of inventory items matching color
	 */
	Page<Inventory> getInventoryByColorPaginated(String color, Pageable pageable);
	
	/**
	 * Get inventory items by size with pagination.
	 * 
	 * @param size the size to search for
	 * @param pageable pagination and sorting information
	 * @return paginated list of inventory items matching size
	 */
	Page<Inventory> getInventoryBySizePaginated(String size, Pageable pageable);
	
	/**
	 * Get inventory items by supplier ID with pagination.
	 * 
	 * @param supplierId the supplier ID to search for
	 * @param pageable pagination and sorting information
	 * @return paginated list of inventory items matching supplier
	 */
	Page<Inventory> getInventoryBySupplierPaginated(Long supplierId, Pageable pageable);

	// Projection pagination methods
	
	/**
	 * Get basic projection of all inventory items with pagination.
	 * 
	 * @param pageable pagination and sorting information
	 * @return paginated list of basic inventory projections
	 */
	Page<InventoryBasicProjection> getAllBasicProjectionsPaginated(Pageable pageable);
	
	/**
	 * Get basic projection of inventory items by brand name with pagination.
	 * 
	 * @param brandName brand name to search for
	 * @param pageable pagination and sorting information
	 * @return paginated list of basic inventory projections matching brand
	 */
	Page<InventoryBasicProjection> getBasicProjectionsByBrandPaginated(String brandName, Pageable pageable);
	
	/**
	 * Get price projection of all inventory items with pagination.
	 * 
	 * @param pageable pagination and sorting information
	 * @return paginated list of price inventory projections
	 */
	Page<InventoryPriceProjection> getAllPriceProjectionsPaginated(Pageable pageable);
	
	/**
	 * Get price projection of inventory items by brand name with pagination.
	 * 
	 * @param brandName brand name to search for
	 * @param pageable pagination and sorting information
	 * @return paginated list of price inventory projections matching brand
	 */
	Page<InventoryPriceProjection> getPriceProjectionsByBrandPaginated(String brandName, Pageable pageable);
	
	/**
	 * Get summary projection of all inventory items with pagination.
	 * 
	 * @param pageable pagination and sorting information
	 * @return paginated list of summary inventory projections
	 */
	Page<InventorySummaryProjection> getAllSummaryProjectionsPaginated(Pageable pageable);
	
	/**
	 * Get summary projection of inventory items by brand name with pagination.
	 * 
	 * @param brandName brand name to search for
	 * @param pageable pagination and sorting information
	 * @return paginated list of summary inventory projections matching brand
	 */
	Page<InventorySummaryProjection> getSummaryProjectionsByBrandPaginated(String brandName, Pageable pageable);

}
