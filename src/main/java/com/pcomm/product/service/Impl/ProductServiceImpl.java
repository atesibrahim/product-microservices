package com.pcomm.product.service.Impl;


import com.google.common.collect.Lists;
import com.pcomm.product.exceptions.ProductNotFoundExceptions;
import com.pcomm.product.dto.ProductDTO;
import com.pcomm.product.mapper.ProductMapper;
import com.pcomm.product.model.Product;
import com.pcomm.product.repository.ProductRepository;
import com.pcomm.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class ProductServiceImpl implements ProductService {

    private Logger logger = LoggerFactory.getLogger(ProductService.class
            .getName());

    @Autowired
    ProductRepository productRepository;

    Instant now = Instant.now();


    @Override
    public List<ProductDTO> findByProductNameContainingIgnoreCase(String productName) {

        return productRepository
                .findByProductNameContaining(productName)
                .parallelStream()
                .map(productIn -> ProductMapper.convertToDTO(productIn))
                .collect(Collectors.toList());

    }


    @Override
    public List<ProductDTO> allProducts() {

        return Lists.newArrayList(productRepository.findAll())
                .parallelStream()
                .map(productIn -> ProductMapper.convertToDTO(productIn))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO findProductById(Long id) {

            checkProduct(productRepository.findById(id), id);

            return ProductMapper.convertToDTO(productRepository.findById(id).get());

    }

    @Override
    public void deleteProduct(Long id) {

            checkProduct(productRepository.findById(id), id);

            Product product = productRepository.findById(id).get();

            productRepository.delete(product);

    }


    @Override
    public ProductDTO addProduct(Product product) {


        product.setCreateInstanceId(LocalDateTime.now());

        product.setUpdateInstanceId(LocalDateTime.now());

        logger.info("product service impl / product-service addEntity method will called and found product " );

        return ProductMapper.convertToDTO(productRepository.save(product));

    }

    @Override
    public ProductDTO updateProduct(Product product) {

        logger.info("product service impl / product-service updateEntity method will called and found product " );
        Optional<Product>  productOpt = productRepository.findById(product.getId());
        checkProduct(productOpt, product.getId());

        product.setUpdateInstanceId(LocalDateTime.now());
        product.setCreateInstanceId(productOpt.get().getCreateInstanceId());

        return ProductMapper.convertToDTO(productRepository.save(product));

    }

    private void checkProduct(Optional<Product> product, Long id){
        if(!product.isPresent()){
            throw new ProductNotFoundExceptions(id);
        }
    }
}
