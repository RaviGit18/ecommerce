package com.ravi.ecommerce.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ravi.ecommerce.entity.Inventory;
import com.ravi.ecommerce.service.EcommerceAppService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class EcommerceAppController {

	@Autowired
	EcommerceAppService ecommerceAppService;

	@GetMapping("/brand/{brandName}")
	public List<Inventory> getInventoryDeatilsByBrandGet(@PathVariable("brandName") String brandName) {
		log.info("ENTERED INTO EcommerceAppController.getInventoryDeatilsByBrandGet()");
		List<Inventory> inventoryList = ecommerceAppService.getInventoryDeatilsByBrand(brandName);
		
		return inventoryList;
	}

	@PostMapping("/brand/{brandName}")
	public List<Inventory> getInventoryDeatilsByBrandPost(@PathVariable("brandName") String brandName) {
		log.info("ENTERED INTO EcommerceAppController.getInventoryDeatilsByBrandPost()");
		ResponseEntity<Inventory[]> inventory = new RestTemplate()
				.getForEntity("http://localhost:8080/brand/{brandName}", Inventory[].class, brandName);
		Inventory[] inventories = inventory.getBody();
		List<Inventory> inventoryList = Arrays.asList(inventories);
		return inventoryList;
	}

	@GetMapping("/color/{color}")
	public List<Inventory> getInventoryDeatilsByColorGet(@PathVariable("color") String color) {
		log.info("ENTERED INTO EcommerceAppController.getInventoryDeatilsByColorGet()");
		List<Inventory> inventoryList = ecommerceAppService.getInventoryDeatilsByColor(color);

		return inventoryList;
	}

	@PostMapping("/color/{color}")
	public List<Inventory> getInventoryDeatilsByColorPost(@PathVariable("color") String color) {
		log.info("ENTERED INTO EcommerceAppController.getInventoryDeatilsByColorPost()");
		ResponseEntity<Inventory[]> inventory = new RestTemplate()
				.getForEntity("http://localhost:8080/color/{color}", Inventory[].class, color);
		Inventory[] inventories = inventory.getBody();
		List<Inventory> inventoryList = Arrays.asList(inventories);
		return inventoryList;
	}

	@GetMapping("/size/{size}")
	public List<Inventory> getInventoryDeatilsBySizeGet(@PathVariable("size") String size) {
		log.info("ENTERED INTO EcommerceAppController.getInventoryDeatilsBySizeGet()");
		List<Inventory> inventoryList = ecommerceAppService.getInventoryDeatilsBySize(size);

		return inventoryList;
	}

	@PostMapping("/size/{size}")
	public List<Inventory> getInventoryDeatilsBySizePost(@PathVariable("size") String size) {
		log.info("ENTERED INTO EcommerceAppController.getInventoryDeatilsBySizePost()");
		ResponseEntity<Inventory[]> inventory = new RestTemplate()
				.getForEntity("http://localhost:8080/size/{size}", Inventory[].class, size);
		Inventory[] inventories = inventory.getBody();
		List<Inventory> inventoryList = Arrays.asList(inventories);
		return inventoryList;
	}

	@GetMapping("/seller/{sellerId}")
	public int getAvailableNumberOfProductBySellerGet(@PathVariable("sellerId") String sellerId) {
		log.info("ENTERED INTO EcommerceAppController.getAvailableNumberOfProductBySellerGet()");
		int availableNumberOfProduct = ecommerceAppService.getAvailableNumberOfProductBySeller(sellerId);

		return availableNumberOfProduct;
	}

}
