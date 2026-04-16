package com.ravi.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object for Inventory entity.
 * Used to expose inventory data via API without exposing internal entity structure.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDto {

    private Long inventoryId;
    private String brandName;
    private BigDecimal price;
    private String color;
    private String size;
    private String skuId;
    private Integer quantity;
    private ProductDto product;
    private SupplierDto supplier;
}
