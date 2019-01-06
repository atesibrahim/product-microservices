package com.pcomm.product.service;

import com.pcomm.product.dto.ProductDTO;
import com.pcomm.product.model.Product;

import java.util.List;

public interface ProductService {


    public List<ProductDTO> findByProductNameContainingIgnoreCase(String productName);

    public ProductDTO findProductById(Long id);

    public List<ProductDTO> allProducts();

    public void deleteProduct(Long id);

    public ProductDTO addProduct(Product product);

    public ProductDTO updateProduct(Product product);







}
