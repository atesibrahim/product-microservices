package com.pcomm.product.feignclient;
import java.util.List;

import com.pcomm.product.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "http://localhost:8761")
public interface ProductServiceClient {

    @GetMapping("list")
    public List<Product> getAllProducts();

    @GetMapping("read/{productId}")
    public Product getProduct(@PathVariable("productId") Long productId);


}
