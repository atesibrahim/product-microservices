package com.pcomm.product.controller;

import java.util.List;

import com.google.common.collect.Lists;
import com.pcomm.product.dto.ProductDTO;
import com.pcomm.product.mapper.ProductMapper;
import com.pcomm.product.model.Product;
import com.pcomm.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;


    @GetMapping("list")
    public ResponseEntity<List<ProductDTO>> listAll() {

        logger.info("product controller / product-service list method will invoked:{0}" );
        Iterable<ProductDTO> productDTOs = productService.allProducts();

        logger.info("product controller / product-service list method returned:{0}" );

        return new ResponseEntity<>(Lists.newArrayList(productDTOs), HttpStatus.OK);


    }

    @GetMapping("productName/{productName}")
    public ResponseEntity<List<ProductDTO>> listByProductName(@PathVariable("productName") String productName) {

        logger.info("product controller / product-service list by productName method will  invoked:{0}" , productName);
        List<ProductDTO> productDTOs = productService.findByProductNameContainingIgnoreCase(productName);
        logger.info("product controller / product-service list by productName found and returned list size:{0} " ,productDTOs.size());

        return new ResponseEntity<>(productDTOs, HttpStatus.OK);


    }


    @GetMapping("read/{productId}")
    public ResponseEntity<ProductDTO> findProduct(@PathVariable("productId") Long id){
        logger.info("product controller / product-service findProduct read will called for this ProductId ={0} ", id);
        ProductDTO productDTO = productService.findEntityById(id);
        logger.info("product controller / product-service read called  for this ProductId ={0} ", id);
        if(productDTO==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDTO> add(@Valid @RequestBody ProductDTO productDTO){
        logger.info("product controller / product-service add method will called={0} ", productDTO);
        Product productM = ProductMapper.convertToEntity(productDTO);
        ProductDTO productDTO_Out = productService.addEntity(productM);
        logger.info("product controller / product-service add method called and returned -> = {0}", productDTO_Out);
        if(productDTO_Out==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDTO_Out, HttpStatus.OK);
    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<ProductDTO> update(@Valid @RequestBody ProductDTO productDTO){
        logger.info("product controller / product-service update method will called={0}",productDTO);
        Product productM = ProductMapper.convertToEntity(productDTO);
        productM.setId(productDTO.getId());
        ProductDTO productDTO_Out = productService.updateEntity(productM);
        logger.info("product controller / product-service add method called={0} ", productDTO_Out);
        if(productDTO_Out==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDTO_Out, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ProductDTO> deleteById(@PathVariable("productId") Long id){
        logger.info("product controller / product-service delete method will called for productId:{0} " , id);
        productService.deleteEntity(id);
        logger.info("product controller / product-service delete method called for productId:{0} " , id);

        return new ResponseEntity<>(HttpStatus.OK);
    }



}
