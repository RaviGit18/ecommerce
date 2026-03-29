package com.ravi.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ravi.ecommerce.entity.Inventory;
import com.ravi.ecommerce.enums.InventoryEnum;
import com.ravi.ecommerce.exception.BaseException;
import com.ravi.ecommerce.repository.InventoryRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EcommerceAppServiceImpl implements EcommerceAppService {

	@Autowired
	InventoryRepository inventoryRepository;

	@Override
	public List<Inventory> getInventoryDeatilsByBrand(String brandName) {
		log.info("ENTERED INTO EcommerceAppServiceImpl.getInventoryDeatilsByBrand()");
		List<Inventory> inventoryList = inventoryRepository.findByBrandName(brandName);
		if(inventoryList == null) throw new BaseException(InventoryEnum.INVENTORY_NOT_FOUND);
		return inventoryList;
	}

	@Override
	public int getAvailableNumberOfProductBySeller(String sellerId) {
		log.info("ENTERED INTO EcommerceAppServiceImpl.getAvailableNumberOfProductBySeller()");
		int availableNumberOfProduct = inventoryRepository.getAvailableNumberOfProductBySeller(sellerId);
		return availableNumberOfProduct;
	}

	@Override
	public List<Inventory> getInventoryDeatilsBySize(String size) {
		log.info("ENTERED INTO EcommerceAppServiceImpl.getInventoryDeatilsBySize()");
		List<Inventory> inventoryList = inventoryRepository.findBySize(size);
		if(inventoryList == null) throw new BaseException(InventoryEnum.INVENTORY_NOT_FOUND);
		return inventoryList;
	}

	@Override
	public List<Inventory> getInventoryDeatilsByColor(String color) {
		log.info("ENTERED INTO EcommerceAppServiceImpl.getInventoryDeatilsByColor()");
		List<Inventory> inventoryList = inventoryRepository.findByColor(color);
		if(inventoryList == null) throw new BaseException(InventoryEnum.INVENTORY_NOT_FOUND);
		return inventoryList;
	}

}
