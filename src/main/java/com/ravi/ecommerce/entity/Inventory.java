package com.ravi.ecommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity representing inventory items in the e-commerce system.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"product", "supplier"})
@ToString(exclude = {"product", "supplier"})
@Entity
@Table(name = "inventory")
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inventory_Id")
	private Long inventoryId;

	@NotBlank(message = "Brand name cannot be blank")
	@Column(name = "brand_Name")
	private String brandName;

	@NotNull(message = "Price cannot be null")
	@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
	@Column(name = "price")
	private Double price;

	@NotBlank(message = "Color cannot be blank")
	@Column(name = "color")
	private String color;

	@NotBlank(message = "Size cannot be blank")
	@Column(name = "size")
	private String size;

	@NotBlank(message = "SKU ID cannot be blank")
	@Column(name = "sku_Id")
	private String skuId;

	@NotNull(message = "Quantity cannot be null")
	@Min(value = 0, message = "Quantity cannot be negative")
	@Column(name = "quantity")
	private Integer quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_Id", referencedColumnName = "product_Id")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplier_Id", referencedColumnName = "supplier_Id")
	private Supplier supplier;

	// Legacy fields for backward compatibility
	@Column(name = "product_Id", insertable = false, updatable = false)
	private Long productId;

	@Column(name = "supplier_Id", insertable = false, updatable = false)
	private Long supplierId;
}
