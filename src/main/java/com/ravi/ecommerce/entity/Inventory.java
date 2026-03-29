package com.ravi.ecommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "inventory")
public class Inventory {

	@Id
	@NotNull
	@Column(name = "inventory_Id")
	private Long inventoryId;

	@NotEmpty
	@Column(name = "brand_Name")
	private String brandName;

	@NotEmpty
	@Column(name = "price")
	private Double price;

	@NotEmpty
	@Column(name = "color")
	private String color;

	@NotEmpty
	@Column(name = "size")
	private String size;

	@NotEmpty
	@Column(name = "sku_Id")
	private String skuId;

	@NotEmpty
	@Min(0)
	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "product_Id")
	private Long productId;

	@Column(name = "supplier_Id")
	private Long supplierId;

}
