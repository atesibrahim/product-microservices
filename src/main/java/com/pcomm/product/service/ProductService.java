package com.pcomm.product.service;

import com.pcomm.product.dto.ProductDTO;
import com.pcomm.product.model.Product;

import java.util.List;

public interface ProductService {


    public List<ProductDTO> findByProductTypeContainingIgnoreCase(String productType);

    public List<ProductDTO> findByProductNameContainingIgnoreCase(String productName);

    public ProductDTO findEntityById(Long id);

    public List<ProductDTO> allProducts();


    public void deleteEntity(Long id);

    public ProductDTO addEntity(Product product);

    public ProductDTO updateEntity(Product product);







}
