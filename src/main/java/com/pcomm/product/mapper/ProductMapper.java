package com.pcomm.product.mapper;

import com.pcomm.product.dto.ProductDTO;
import com.pcomm.product.model.Product;

public class ProductMapper {

    private ProductMapper(){}
    public static ProductDTO convertToDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategoryId(product.getCategoryId());
        productDTO.setBrandId(product.getBrandId());
        productDTO.setCreateUserId(product.getCreateUserId());
        productDTO.setProductName(product.getProductName());
        productDTO.setUpdateUserId(product.getUpdateUserId());
        productDTO.setId(product.getId());
        return productDTO;
    }


    public static Product convertToEntity(ProductDTO productDTO){
        Product product= new Product();

        product.setBrandId(productDTO.getBrandId());
        product.setCategoryId(productDTO.getCategoryId());
        product.setProductName(productDTO.getProductName());
        product.setUpdateUserId(productDTO.getUpdateUserId());
        product.setCreateUserId(productDTO.getCreateUserId());
        if(productDTO.getId()!=null)
        {
            product.setId(productDTO.getId());

        }
        return product;
    }
}
