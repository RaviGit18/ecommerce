package com.ravi.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ravi.ecommerce.entity.Inventory;
import com.ravi.ecommerce.entity.Product;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	
	  @Query("select inv from Inventory inv where inv.brandName = :brandName")
	  List<Inventory> findByBrandName(@Param("brandName") String brandName);
	
	  @Query("select inv from Inventory inv where inv.size = :size")
	  List<Inventory> findBySize(@Param("size") String size);
	  
	  @Query("select inv from Inventory inv where inv.color = :color")
	  List<Inventory> findByColor(@Param("color") String color);
	  
	  @Query("select count(*) from Inventory inv where inv.supplierId = :sellerId") 
	  int getAvailableNumberOfProductBySeller(@Param("sellerId") String sellerId);
	
}
