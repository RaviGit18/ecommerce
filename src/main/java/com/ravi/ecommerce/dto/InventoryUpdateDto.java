package com.ravi.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

/**
 * Data Transfer Object for updating existing Inventory items.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryUpdateDto {

    private String brandName;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;

    private String color;

    private String size;

    private String skuId;

    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    private Long productId;

    private Long supplierId;
}
