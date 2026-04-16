package com.ravi.ecommerce.projection;

import java.math.BigDecimal;

/**
 * Price-focused projection for inventory items.
 * Used when only pricing information is needed.
 */
public interface InventoryPriceProjection {

    Long getInventoryId();
    String getBrandName();
    String getSkuId();
    BigDecimal getPrice();
    Integer getQuantity();
}
