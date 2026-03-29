package com.ravi.ecommerce.enums;

import lombok.Getter;

@Getter
public enum InventoryEnum  {

    INVENTORY_NOT_FOUND(30, "Inventory does not exit!")
    ;

    private Integer code;

    private String message;

    InventoryEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
