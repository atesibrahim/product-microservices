package com.pcomm.product.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class ProductDTO {


    protected Long id;

    @NotNull
    protected String productName;


    @NotNull
    protected Long categoryId;

    @NotNull
    protected Long brandId;

    protected String createUserId;

    protected String updateUserId;

    public ProductDTO() {
    }

    public ProductDTO(String productName, Long categoryId, Long brandId, String createUserId, String updateUserId) {
        this.productName = productName;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.createUserId = createUserId;
        this.updateUserId = updateUserId;
    }
}
