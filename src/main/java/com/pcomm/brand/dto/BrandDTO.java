package com.pcomm.brand.dto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class BrandDTO {


    protected Long id;

    @NotNull
    protected String brandName;


    protected String createUserId;

    protected String updateUserId;

    public BrandDTO() {
    }

}
