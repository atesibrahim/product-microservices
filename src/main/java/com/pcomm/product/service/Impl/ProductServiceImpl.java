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
import java.security.InvalidParameterException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class
            .getName());

    @Autowired
    ProductRepository productRepository;

    Instant now = Instant.now();


    @Override
    public List<ProductDTO> findByProductNameContainingIgnoreCase(String productName) {

        logger.info("product service impl / product-service findByProductNameContainingIgnoreCase method will called for productName:{0} " , productName);

        return productRepository.findByProductNameContaining(productName).parallelStream().map(pduct -> ProductMapper.convertToDTO(pduct)).collect(Collectors.toList());

    }


    @Override
    public List<ProductDTO> allProducts() {


        logger.info("product service impl / product-service allProducts method will called for productName:{0} " );
        List<Product> products = Lists.newArrayList(productRepository.findAll());
        return products.parallelStream().map(pduct -> ProductMapper.convertToDTO(pduct)).collect(Collectors.toList());


    }

    @Override
    public ProductDTO findEntityById(Long id) {
        if(id!=null) {
            logger.info("product service impl / product-service findEntityById method will called for id:{0} ", id );
            Optional<Product> pduct = productRepository.findById(id);

            checkProduct(pduct, id);

            ProductDTO productDTO =ProductMapper.convertToDTO(productRepository.findById(id).get());

            logger.info("product service impl / product-service findEntityById method called and found product ", productDTO );

            return productDTO;
        }
        else
        {
            throw new ProductNotFoundExceptions(id);

        }
    }

    @Override
    public void deleteEntity(Long id) {

        if(id!=null) {
            logger.info("product service impl / product-service deleteEntity method will called and found product ", id );

            Optional<Product> pduct = productRepository.findById(id);

            checkProduct(pduct, id);

            Product product = productRepository.findById(id).get();


            productRepository.delete(product);
            logger.info("product service impl / product-service deleteEntity method called and deleted ", id );
        }
        else{
            throw new ProductNotFoundExceptions(id);
        }

    }


    @Override
    public ProductDTO addEntity(Product product) {

        if((product.getProductName()==null) || (product.getProductName()==""))
        {
            throw new InvalidParameterException("Product Name could not be empty!");

        }

        product.setCreateInstanceId(getTimeStamp());

        product.setUpdateInstanceId(getTimeStamp());

        logger.info("product service impl / product-service addEntity method will called and found product " );

        ProductDTO productDTO =ProductMapper.convertToDTO(productRepository.save(product));

        logger.info("product service impl / product-service addEntity method called and found product " );


        return productDTO;



    }

    @Override
    public ProductDTO updateEntity(Product product) {

        logger.info("product service impl / product-service updateEntity method will called and found product " );
        Optional<Product>  productOpt = productRepository.findById(product.getId());
        checkProduct(productOpt, product.getId());

        product.setUpdateInstanceId(getTimeStamp());
        product.setCreateInstanceId(productOpt.get().getCreateInstanceId());

        ProductDTO productDTO =ProductMapper.convertToDTO(productRepository.save(product));

        logger.info("product service impl / product-service addEntity method called and found product " );


        return productDTO;

    }

    public static LocalDateTime getTimeStamp(){


        return LocalDateTime.now();

    }

    private void checkProduct(Optional<Product> product, Long id){
        if(!product.isPresent()){
            throw new ProductNotFoundExceptions(id);
        }
    }
}
