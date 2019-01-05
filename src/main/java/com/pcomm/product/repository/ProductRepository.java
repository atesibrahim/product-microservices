package com.pcomm.product.repository;

import com.pcomm.product.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    public List<Product> findByProductNameContaining(String productName);

}
