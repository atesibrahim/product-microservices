package com.pcomm.product.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.collect.Lists;
import com.pcomm.product.dto.ProductDTO;
import com.pcomm.product.mapper.ProductMapper;
import com.pcomm.product.model.Product;
import com.pcomm.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("product")
public class ProductController {

    private static final  Logger logger = Logger.getLogger(ProductController.class
            .getName());

    @Autowired
    ProductService productService;


    @GetMapping("list")
    public ResponseEntity<List<ProductDTO>> listAll() {

        Iterable<ProductDTO> productDTOs = productService.allProducts();

        return new ResponseEntity<>(Lists.newArrayList(productDTOs), HttpStatus.OK);


    }

    @GetMapping("productName/{productName}")
    public ResponseEntity<List<ProductDTO>> listByProductName(@PathVariable("productName") String productName) {

        logger.log(Level.INFO,"product-service  invoked:{0}" , productName);
        List<ProductDTO> productDTOs = productService.findByProductNameContainingIgnoreCase(productName);
        logger.log(Level.INFO,"product-service found:{0} " ,productName);

        return new ResponseEntity<>(productDTOs, HttpStatus.OK);


    }


    @GetMapping("productType/{productType}")
    public ResponseEntity<List<ProductDTO>> listByProductType(@PathVariable("productType") String productType) {
        logger.log(Level.INFO,"product-service list  method invoked={0} "
                ,productService.getClass().getTypeName());

        List<ProductDTO> productDTOs = productService
                .findByProductTypeContainingIgnoreCase(productType);

        return new ResponseEntity<>(productDTOs, HttpStatus.OK);

    }

    @GetMapping("read/{productId}")
    public ResponseEntity<ProductDTO> findProduct(@PathVariable("productId") Long id){
        logger.log(Level.INFO,"product-service read will called for this ProductId ={0} ", id);
        ProductDTO productDTO = productService.findEntityById(id);
        logger.log(Level.INFO,"product-service read called  for this ProductId ={0} ", id);
        if(productDTO==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDTO> add(@Valid @RequestBody ProductDTO productDTO){
        logger.log(Level.INFO,"product-service add method will called={0} ", productDTO);
        Product productM = ProductMapper.convertToEntity(productDTO);
        ProductDTO productDTO_Out = productService.addEntity(productM);
        logger.log(Level.INFO,"product-service add method called= {0}", productDTO_Out);
        if(productDTO_Out==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDTO_Out, HttpStatus.OK);
    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<ProductDTO> update(@Valid @RequestBody ProductDTO productDTO){
        logger.log(Level.INFO,"product-service update method will called={0}", productDTO);
        Product productM = ProductMapper.convertToEntity(productDTO);
        productM.setId(productDTO.getId());
        ProductDTO productDTO_Out = productService.updateEntity(productM);
        logger.log(Level.INFO,"product-service add method will called={0} ", productDTO_Out);
        if(productDTO_Out==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDTO_Out, HttpStatus.OK);
    }


    @DeleteMapping("delete/{productId}")
    public ResponseEntity<Product> deleteById(@PathVariable("productId") Long id){
        logger.log(Level.INFO,"product-service delete method will called for productId:{0} " , id);
        productService.deleteEntity(id);
        logger.log(Level.INFO,"product-service delete method called for productId:{0} " , id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
