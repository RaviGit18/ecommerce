package com.ravi.ecommerce.mapper;

import com.ravi.ecommerce.dto.InventoryDto;
import com.ravi.ecommerce.dto.ProductDto;
import com.ravi.ecommerce.dto.SupplierDto;
import com.ravi.ecommerce.entity.Inventory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for converting between Inventory entities and DTOs.
 */
@Component
public class InventoryMapper {

    /**
     * Convert Inventory entity to InventoryDto.
     */
    public InventoryDto toDto(Inventory inventory) {
        if (inventory == null) {
            return null;
        }

        ProductDto productDto = null;
        if (inventory.getProduct() != null) {
            productDto = ProductDto.builder()
                    .productId(inventory.getProduct().getProductId())
                    .productName(inventory.getProduct().getProductName())
                    .build();
        }

        SupplierDto supplierDto = null;
        if (inventory.getSupplier() != null) {
            supplierDto = SupplierDto.builder()
                    .supplierId(inventory.getSupplier().getSupplierId())
                    .supplierName(inventory.getSupplier().getSupplierName())
                    .build();
        }

        return InventoryDto.builder()
                .inventoryId(inventory.getInventoryId())
                .brandName(inventory.getBrandName())
                .price(inventory.getPrice() != null ? java.math.BigDecimal.valueOf(inventory.getPrice()) : null)
                .color(inventory.getColor())
                .size(inventory.getSize())
                .skuId(inventory.getSkuId())
                .quantity(inventory.getQuantity())
                .product(productDto)
                .supplier(supplierDto)
                .build();
    }

    /**
     * Convert list of Inventory entities to list of InventoryDto.
     */
    public List<InventoryDto> toDtoList(List<Inventory> inventories) {
        if (inventories == null) {
            return List.of();
        }
        return inventories.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
