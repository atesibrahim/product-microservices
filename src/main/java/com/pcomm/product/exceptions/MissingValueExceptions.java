package com.pcomm.product.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class MissingValueExceptions extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MissingValueExceptions(Long productId) {
        super("No product found by product id: " + productId);
    }
}
