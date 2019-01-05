package com.pcomm.brand.service.Impl;


import com.google.common.collect.Lists;
import com.pcomm.brand.dto.BrandDTO;
import com.pcomm.brand.exceptions.BrandNotFoundExceptions;
import com.pcomm.brand.mapper.BrandMapper;
import com.pcomm.brand.model.Brand;
import com.pcomm.brand.repository.BrandRepository;
import com.pcomm.brand.service.BrandService;
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
public class BrandServiceImpl implements BrandService {

    private static final Logger logger = LoggerFactory.getLogger(BrandService.class);

    @Autowired
    BrandRepository brandRepository;

    Instant now = Instant.now();


    @Override
    public List<BrandDTO> findByBrandNameContaining(String brandName) {

        logger.info("brand service impl / brand-service findBrandNameContaining method will called for productName:{0} " , brandName);

        return brandRepository.findByBrandNameContaining(brandName).parallelStream().map(brand -> BrandMapper.convertToDTO(brand)).collect(Collectors.toList());

    }


    @Override
    public List<BrandDTO> allBrands() {


        logger.info("brand service impl / brand-service allBrands method will called for brandName:{0} " );
        List<Brand> brands = Lists.newArrayList(brandRepository.findAll());
        return brands.parallelStream().map(brand -> BrandMapper.convertToDTO(brand)).collect(Collectors.toList());


    }

    @Override
    public BrandDTO findEntityById(Long id) {
        if(id!=null) {
            logger.info("brand service impl / brand-service findEntityById method will called for id:{0} ", id );
            Optional<Brand> brand = brandRepository.findById(id);

            checkBrand(brand, id);

            BrandDTO brandDTO = BrandMapper.convertToDTO(brandRepository.findById(id).get());

            logger.info("brand service impl / brand-service findEntityById method called and found product ", brandDTO );

            return brandDTO;
        }
        else
        {
            throw new BrandNotFoundExceptions(id);

        }
    }

    @Override
    public void deleteEntity(Long id) {

        if(id!=null) {
            logger.info("brand service impl / brand-service deleteEntity method will called and found product ", id );

            Optional<Brand> brand = brandRepository.findById(id);

            checkBrand(brand, id);

            Brand _brand = brandRepository.findById(id).get();


            brandRepository.delete(_brand);
            logger.info("brand service impl / brand-service deleteEntity method called and deleted ", id );
        }
        else{
            throw new BrandNotFoundExceptions(id);
        }

    }


    @Override
    public BrandDTO addEntity(Brand brand) {

        if((brand.getBrandName()==null) || (brand.getBrandName()==""))
        {
            throw new InvalidParameterException("Brand Name could not be empty!");

        }

        brand.setCreateInstanceId(getTimeStamp());

        brand.setUpdateInstanceId(getTimeStamp());

        logger.info("brand service impl / brand-service addEntity method will called and found product " );

        BrandDTO brandDTO = BrandMapper.convertToDTO(brandRepository.save(brand));

        logger.info("product service impl / product-service addEntity method called and found product " );


        return brandDTO;



    }

    @Override
    public BrandDTO updateEntity(Brand brand) {

        logger.info("brand service impl / brand-service updateEntity method will called and found product " );
        Optional<Brand>  brandOptional = brandRepository.findById(brand.getId());
        checkBrand(brandOptional, brand.getId());

        brand.setUpdateInstanceId(getTimeStamp());
        brand.setCreateInstanceId(brandOptional.get().getCreateInstanceId());

        BrandDTO brandDTO = BrandMapper.convertToDTO(brandRepository.save(brand));

        logger.info("brand service impl / brand-service addEntity method called and found product " );


        return brandDTO;

    }

    public static LocalDateTime getTimeStamp(){


        return LocalDateTime.now();

    }

    private void checkBrand(Optional<Brand> brand, Long brandId){
        if(!brand.isPresent()){
            throw new BrandNotFoundExceptions(brandId);
        }
    }
}
