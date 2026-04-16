package com.ravi.ecommerce.exception;

import com.ravi.ecommerce.enums.InventoryEnum;

public class BaseException extends RuntimeException {

    private Integer code;

    public BaseException(InventoryEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
