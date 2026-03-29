package com.ravi.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ravi.ecommerce.entity.Inventory;


public interface EcommerceAppService {

	List<Inventory> getInventoryDeatilsByBrand(String brandName);

	int getAvailableNumberOfProductBySeller(String sellerId);

	List<Inventory> getInventoryDeatilsBySize(String size);

	List<Inventory> getInventoryDeatilsByColor(String color);

}
