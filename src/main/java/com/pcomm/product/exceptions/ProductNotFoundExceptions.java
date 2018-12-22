package com.pcomm.product.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundExceptions extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ProductNotFoundExceptions(Long productId) {
        super("No product found by product id: " + productId);
    }

    public ProductNotFoundExceptions(String productName) {
        super("No product found by product name: " + productName);
    }


}
