package com.ravi.ecommerce.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ravi.ecommerce.entity.Inventory;
import com.ravi.ecommerce.projection.InventoryBasicProjection;
import com.ravi.ecommerce.projection.InventoryPriceProjection;
import com.ravi.ecommerce.projection.InventorySummaryProjection;

/**
 * Repository interface for Inventory entity operations.
 */
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	/**
	 * Find inventory items by brand name.
	 * 
	 * @param brandName the brand name to search for
	 * @return list of inventory items matching the brand name
	 */
	List<Inventory> findByBrandName(String brandName);
	
	/**
	 * Find inventory items by size.
	 * 
	 * @param size the size to search for
	 * @return list of inventory items matching the size
	 */
	List<Inventory> findBySize(String size);
	  
	/**
	 * Find inventory items by color.
	 * 
	 * @param color the color to search for
	 * @return list of inventory items matching the color
	 */
	List<Inventory> findByColor(String color);
	  
	/**
	 * Count the total number of products available for a specific seller.
	 * 
	 * @param sellerId the seller ID to search for
	 * @return total count of available products for the seller
	 */
	@Query("SELECT COUNT(i) FROM Inventory i WHERE CAST(i.supplierId AS string) = :sellerId") 
	int getAvailableNumberOfProductBySeller(@Param("sellerId") String sellerId);

	/**
	 * Check if inventory exists by SKU ID.
	 * 
	 * @param skuId the SKU ID to check
	 * @return true if inventory exists, false otherwise
	 */
	boolean existsBySkuId(String skuId);

	// Projection methods
	
	/**
	 * Find basic projection of all inventory items.
	 * 
	 * @return list of basic inventory projections
	 */
	List<InventoryBasicProjection> findAllBasicProjectedBy();
	
	/**
	 * Find basic projection of inventory items by brand name.
	 * 
	 * @param brandName the brand name to search for
	 * @return list of basic inventory projections matching brand
	 */
	@Query("SELECT i.inventoryId as inventoryId, i.brandName as brandName, i.price as price, i.color as color, i.size as size, i.skuId as skuId, i.quantity as quantity FROM Inventory i WHERE i.brandName = :brandName")
	List<InventoryBasicProjection> findBasicProjectionsByBrandName(@Param("brandName") String brandName);
	
	/**
	 * Find price projection of all inventory items.
	 * 
	 * @return list of price inventory projections
	 */
	@Query("SELECT i.inventoryId as inventoryId, i.brandName as brandName, i.skuId as skuId, i.price as price, i.quantity as quantity FROM Inventory i")
	List<InventoryPriceProjection> findAllPriceProjections();
	
	/**
	 * Find price projection of inventory items by brand name.
	 * 
	 * @param brandName the brand name to search for
	 * @return list of price inventory projections matching brand
	 */
	@Query("SELECT i.inventoryId as inventoryId, i.brandName as brandName, i.skuId as skuId, i.price as price, i.quantity as quantity FROM Inventory i WHERE i.brandName = :brandName")
	List<InventoryPriceProjection> findPriceProjectionsByBrandName(@Param("brandName") String brandName);
	
	/**
	 * Find summary projection of all inventory items.
	 * 
	 * @return list of summary inventory projections
	 */
	@Query("SELECT i.inventoryId as inventoryId, i.brandName as brandName, i.price as price, i.color as color, i.size as size, i.quantity as quantity, i.productId as productId, p.productName as productName, i.supplierId as supplierId, s.supplierName as supplierName FROM Inventory i LEFT JOIN i.product p LEFT JOIN i.supplier s")
	List<InventorySummaryProjection> findAllSummaryProjections();
	
	/**
	 * Find summary projection of inventory items by brand name.
	 * 
	 * @param brandName the brand name to search for
	 * @return list of summary inventory projections matching brand
	 */
	@Query("SELECT i.inventoryId as inventoryId, i.brandName as brandName, i.price as price, i.color as color, i.size as size, i.quantity as quantity, i.productId as productId, p.productName as productName, i.supplierId as supplierId, s.supplierName as supplierName FROM Inventory i LEFT JOIN i.product p LEFT JOIN i.supplier s WHERE i.brandName = :brandName")
	List<InventorySummaryProjection> findSummaryProjectionsByBrandName(@Param("brandName") String brandName);

	// Pagination methods
	
	/**
	 * Find all inventory items with pagination.
	 * 
	 * @param pageable pagination and sorting information
	 * @return paginated list of inventory items
	 */
	Page<Inventory> findAll(Pageable pageable);
	
	/**
	 * Find inventory items by brand name with pagination.
	 * 
	 * @param brandName brand name to search for
	 * @param pageable pagination and sorting information
	 * @return paginated list of inventory items matching brand
	 */
	Page<Inventory> findByBrandName(String brandName, Pageable pageable);
	
	/**
	 * Find inventory items by color with pagination.
	 * 
	 * @param color color to search for
	 * @param pageable pagination and sorting information
	 * @return paginated list of inventory items matching color
	 */
	Page<Inventory> findByColor(String color, Pageable pageable);
	
	/**
	 * Find inventory items by size with pagination.
	 * 
	 * @param size size to search for
	 * @param pageable pagination and sorting information
	 * @return paginated list of inventory items matching size
	 */
	Page<Inventory> findBySize(String size, Pageable pageable);
	
	/**
	 * Find inventory items by supplier ID with pagination.
	 * 
	 * @param supplierId supplier ID to search for
	 * @param pageable pagination and sorting information
	 * @return paginated list of inventory items matching supplier
	 */
	Page<Inventory> findBySupplierId(Long supplierId, Pageable pageable);

	// Projection pagination methods
	
	/**
	 * Find basic projection of all inventory items with pagination.
	 * 
	 * @param pageable pagination and sorting information
	 * @return paginated list of basic inventory projections
	 */
	@Query("SELECT i.inventoryId as inventoryId, i.brandName as brandName, i.price as price, i.color as color, i.size as size, i.skuId as skuId, i.quantity as quantity FROM Inventory i")
	Page<InventoryBasicProjection> findAllBasicProjectedBy(Pageable pageable);
	
	/**
	 * Find basic projection of inventory items by brand name with pagination.
	 * 
	 * @param brandName the brand name to search for
	 * @param pageable pagination and sorting information
	 * @return paginated list of basic inventory projections matching brand
	 */
	@Query("SELECT i.inventoryId as inventoryId, i.brandName as brandName, i.price as price, i.color as color, i.size as size, i.skuId as skuId, i.quantity as quantity FROM Inventory i WHERE i.brandName = :brandName")
	Page<InventoryBasicProjection> findBasicProjectionsByBrandName(@Param("brandName") String brandName, Pageable pageable);
	
	/**
	 * Find price projection of all inventory items with pagination.
	 * 
	 * @param pageable pagination and sorting information
	 * @return paginated list of price inventory projections
	 */
	@Query("SELECT i.inventoryId as inventoryId, i.brandName as brandName, i.skuId as skuId, i.price as price, i.quantity as quantity FROM Inventory i")
	Page<InventoryPriceProjection> findAllPriceProjectedBy(Pageable pageable);
	
	/**
	 * Find price projection of inventory items by brand name with pagination.
	 * 
	 * @param brandName the brand name to search for
	 * @param pageable pagination and sorting information
	 * @return paginated list of price inventory projections matching brand
	 */
	@Query("SELECT i.inventoryId as inventoryId, i.brandName as brandName, i.skuId as skuId, i.price as price, i.quantity as quantity FROM Inventory i WHERE i.brandName = :brandName")
	Page<InventoryPriceProjection> findPriceProjectionsByBrandName(@Param("brandName") String brandName, Pageable pageable);
	
	/**
	 * Find summary projection of all inventory items with pagination.
	 * 
	 * @param pageable pagination and sorting information
	 * @return paginated list of summary inventory projections
	 */
	@Query("SELECT i.inventoryId as inventoryId, i.brandName as brandName, i.price as price, i.color as color, i.size as size, i.quantity as quantity, i.productId as productId, p.productName as productName, i.supplierId as supplierId, s.supplierName as supplierName FROM Inventory i LEFT JOIN i.product p LEFT JOIN i.supplier s")
	Page<InventorySummaryProjection> findAllSummaryProjectedBy(Pageable pageable);
	
	/**
	 * Find summary projection of inventory items by brand name with pagination.
	 * 
	 * @param brandName the brand name to search for
	 * @param pageable pagination and sorting information
	 * @return paginated list of summary inventory projections matching brand
	 */
	@Query("SELECT i.inventoryId as inventoryId, i.brandName as brandName, i.price as price, i.color as color, i.size as size, i.quantity as quantity, i.productId as productId, p.productName as productName, i.supplierId as supplierId, s.supplierName as supplierName FROM Inventory i LEFT JOIN i.product p LEFT JOIN i.supplier s WHERE i.brandName = :brandName")
	Page<InventorySummaryProjection> findSummaryProjectionsByBrandName(@Param("brandName") String brandName, Pageable pageable);









}
