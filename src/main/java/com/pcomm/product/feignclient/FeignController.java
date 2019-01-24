package com.pcomm.product.feignclient;
import com.pcomm.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FeignController {

    @Autowired
    ProductServiceClient productServiceClient;

    @GetMapping("/allProducts")
    public ResponseEntity<?> fetchProducts() {

        return ResponseEntity.ok(productServiceClient.getAllProducts());
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<?> fetchProduct(@PathVariable Long id) {
        Product product = productServiceClient.getProduct(id);

        return ResponseEntity.ok(product);
    }
}
