package com.ravi.ecommerce.projection;

import java.math.BigDecimal;

/**
 * Summary projection for inventory items with product and supplier information.
 * Used for API responses that need related entity data.
 */
public interface InventorySummaryProjection {

    Long getInventoryId();
    String getBrandName();
    BigDecimal getPrice();
    String getColor();
    String getSize();
    Integer getQuantity();
    
    // Product information
    Long getProductId();
    String getProductName();
    
    // Supplier information
    Long getSupplierId();
    String getSupplierName();
}
