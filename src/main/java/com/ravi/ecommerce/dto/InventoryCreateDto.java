package com.ravi.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for creating new Inventory items.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryCreateDto {

    @NotBlank(message = "Brand name cannot be blank")
    private String brandName;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;

    @NotBlank(message = "Color cannot be blank")
    private String color;

    @NotBlank(message = "Size cannot be blank")
    private String size;

    @NotBlank(message = "SKU ID cannot be blank")
    private String skuId;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @NotNull(message = "Supplier ID cannot be null")
    private Long supplierId;
}
