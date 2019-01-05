package com.pcomm.brand.mapper;

import com.pcomm.brand.dto.BrandDTO;
import com.pcomm.brand.model.Brand;

public class BrandMapper {

    private BrandMapper(){}
    public static BrandDTO convertToDTO(Brand brand){
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setBrandName(brand.getBrandName());
        brandDTO.setCreateUserId(brand.getCreateUserId());
        brandDTO.setUpdateUserId(brand.getUpdateUserId());
        brandDTO.setId(brand.getId());
        return brandDTO;
    }


    public static Brand convertToEntity(BrandDTO brandDTO){
        Brand brand= new Brand();

        brand.setBrandName(brandDTO.getBrandName());
        brand.setCreateUserId(brandDTO.getCreateUserId());
        brand.setUpdateUserId(brandDTO.getUpdateUserId());
        if(brandDTO.getId()!=null)
        {
            brand.setId(brandDTO.getId());

        }
        return brand;
    }
}
