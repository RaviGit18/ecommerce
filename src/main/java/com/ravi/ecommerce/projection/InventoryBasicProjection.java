package com.ravi.ecommerce.projection;

import java.math.BigDecimal;

/**
 * Basic projection for inventory items with essential fields only.
 * Used for lightweight API responses when full entity details are not needed.
 */
public interface InventoryBasicProjection {

    Long getInventoryId();
    String getBrandName();
    BigDecimal getPrice();
    String getColor();
    String getSize();
    String getSkuId();
    Integer getQuantity();
}
