package com.pcomm.product.service.Impl;


import com.google.common.collect.Lists;
import com.pcomm.exceptions.ProductNotFoundExceptions;
import com.pcomm.product.dto.ProductDTO;
import com.pcomm.product.mapper.ProductMapper;
import com.pcomm.product.model.Product;
import com.pcomm.product.repository.ProductRepository;
import com.pcomm.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.InvalidParameterException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    ProductRepository productRepository;

    Instant now = Instant.now();

    @Override
    public List<ProductDTO> findByProductTypeContainingIgnoreCase(String productType) {
        List<Product> products = productRepository.findByProductTypeContaining(productType);

        List<ProductDTO> productDTOS = new ArrayList<>();

        for(Product p:products)
        {
            productDTOS.add(ProductMapper.convertToDTO(p));
        }

        return productDTOS;
    }

    @Override
    public List<ProductDTO> findByProductNameContainingIgnoreCase(String productName) {

        List<Product> products = productRepository.findByProductNameContaining(productName);

        List<ProductDTO> productDTOS = new ArrayList<>();

        for(Product p:products)
        {
            productDTOS.add(ProductMapper.convertToDTO(p));
        }

        return productDTOS;
    }


    @Override
    public List<ProductDTO> allProducts() {


        List<Product> products = Lists.newArrayList(productRepository.findAll());

        List<ProductDTO> productDTOS = new ArrayList<>();

        for(Product p:products)
        {
            productDTOS.add(ProductMapper.convertToDTO(p));
        }

        return productDTOS;
    }

    @Override
    public ProductDTO findEntityById(Long id) {
        if(id!=null) {
            Optional<Product> pduct = productRepository.findById(null);

            checkProduct(pduct);

            ProductDTO productDTO =ProductMapper.convertToDTO(productRepository.findById(id).get());


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

            Optional<Product> pduct = productRepository.findById(id);

            checkProduct(pduct);

            Product product = productRepository.findById(id).get();
            productRepository.delete(product);
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
        else if ((product.getProductType()==null) || (product.getProductType()==""))
        {
            throw new InvalidParameterException("Product type could not be empty");
        }

        product.setCreateInstanceId(getTimeStamp());

        product.setUpdateInstanceId(getTimeStamp());


        ProductDTO productDTO =ProductMapper.convertToDTO(productRepository.save(product));


        return productDTO;



    }

    @Override
    public ProductDTO updateEntity(Product product) {

        Optional<Product>  productOpt = productRepository.findById(product.getId());
        checkProduct(productOpt);

        product.setUpdateInstanceId(getTimeStamp());
        product.setCreateInstanceId(productOpt.get().getCreateInstanceId());

        ProductDTO productDTO =ProductMapper.convertToDTO(productRepository.save(product));


        return productDTO;

    }

    public static LocalDateTime getTimeStamp(){


        return LocalDateTime.now();

    }

    private void checkProduct(Optional<Product> product){
        if(!product.isPresent()){
            throw new ProductNotFoundExceptions(product.get().getId());
        }
    }
}
