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
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class BrandServiceImpl implements BrandService {

    private Logger logger = LoggerFactory.getLogger(BrandService.class);

    @Autowired
    BrandRepository brandRepository;

    Instant now = Instant.now();


    @Override
    public List<BrandDTO> findByBrandNameContaining(String brandName) {


      return brandRepository
              .findByBrandNameContaining(brandName)
              .parallelStream()
              .map(brand -> BrandMapper.convertToDTO(brand))
              .collect(Collectors.toList());



    }


    @Override
    public List<BrandDTO> allBrands() {

        return Lists.newArrayList(brandRepository.findAll()).parallelStream().map(brand -> BrandMapper.convertToDTO(brand)).collect(Collectors.toList());
    }

    @Override
    public BrandDTO findBrandById(Long id) {

        BrandDTO brandDTO;

        Optional<Brand> brandOut = brandRepository.findById(id);


        if (brandOut.isPresent()) {

            brandDTO = BrandMapper.convertToDTO(brandOut.get());
        }
        else brandDTO=null;

        return brandDTO;
    }

    @Override
    public void deleteBrand(Long id) {


        Optional<Brand> brandO = brandRepository.findById(id);

        if(brandO.isPresent()){
            brandRepository.delete(brandO.get());
        }

    }


    @Override
    public BrandDTO addBrand(Brand brand) {

        brand.setCreateInstanceId(LocalDateTime.now());

        brand.setUpdateInstanceId(LocalDateTime.now());

        logger.info("brand service impl / brand-service addEntity method will called and found product " );

        return BrandMapper.convertToDTO(brandRepository.save(brand));

    }

    @Override
    public BrandDTO updateBrand(Brand brand) {

        logger.info("brand service impl / brand-service updateEntity method will called and found product " );
        Optional<Brand>  brandOptional = brandRepository.findById(brand.getId());
        checkBrand(brandOptional, brand.getId());

        brand.setUpdateInstanceId(LocalDateTime.now());
        brand.setCreateInstanceId(brandOptional.get().getCreateInstanceId());

        return BrandMapper.convertToDTO(brandRepository.save(brand));

    }


    private void checkBrand(Optional<Brand> brand, Long brandId){
        if(!brand.isPresent()){
            throw new BrandNotFoundExceptions(brandId);
        }
    }
}
