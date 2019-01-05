package com.pcomm.brand.exceptions;

import com.pcomm.brand.model.Brand;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BrandNotFoundExceptions extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public BrandNotFoundExceptions(Long brandId) {
        super("No brand found by brand id: " + brandId);
    }

    public BrandNotFoundExceptions(String brandName) {
        super("No brand found by brand name: " + brandName);
    }
}
