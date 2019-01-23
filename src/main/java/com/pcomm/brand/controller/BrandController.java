package com.pcomm.brand.controller;

import com.google.common.collect.Lists;
import com.pcomm.brand.dto.BrandDTO;
import com.pcomm.brand.mapper.BrandMapper;
import com.pcomm.brand.model.Brand;
import com.pcomm.brand.service.BrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {
    private static final Logger logger = LoggerFactory.getLogger(BrandController.class);

    @Autowired
    BrandService brandService;


    @GetMapping("list")
    public ResponseEntity<List<BrandDTO>> listAll() {

        logger.info("brand controller / brand-service list method will invoked:{0}" );
        Iterable<BrandDTO> brandDTOS = brandService.allBrands();

        logger.info("brand controller / brand-service list method returned:{0}" );

        return new ResponseEntity<>(Lists.newArrayList(brandDTOS), HttpStatus.OK);


    }

    @GetMapping("brandName/{brandName}")
    public ResponseEntity<List<BrandDTO>> listByBrandName(@PathVariable("brandName") String brandName) {

        logger.info("brand controller / product-service list by brandName method will  invoked:{0}" + brandName);
        List<BrandDTO> brandDTOS = brandService.findByBrandNameContaining(brandName);
        logger.info("brand controller / brand-service list by brandName found and returned list size:{0} " +brandDTOS.size());

        return new ResponseEntity<>(brandDTOS, HttpStatus.OK);


    }

    @GetMapping("read/{brandId}")
    public ResponseEntity<BrandDTO> findBrandById(@PathVariable("brandId") Long id){
            logger.info("brand controller / brand-service findBrand read will called for this brandId  ={0} "+ id);
        BrandDTO brandDTO = brandService.findBrandById(id);
        logger.info("brand controller / brand-service read called  for this brandId ={0} "+ id);

        return new ResponseEntity<>(brandDTO, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<BrandDTO> add(@Valid @RequestBody BrandDTO brandDTO){
        logger.info("brand controller / brand-service add method will called={0} "+brandDTO);
        Brand brandEntity = BrandMapper.convertToEntity(brandDTO);
        BrandDTO brandDTO_Out = brandService.addBrand(brandEntity);
        logger.info("brand controller / brand-service add method called and returned -> = {0}"+ brandDTO_Out);

        return new ResponseEntity<>(brandDTO_Out, HttpStatus.OK);
    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<BrandDTO> update(@Valid @RequestBody BrandDTO brandDTO){
        logger.info("brand controller / brand-service update method will called={0}"+brandDTO);
        Brand brandEntity = BrandMapper.convertToEntity(brandDTO);
        brandEntity.setId(brandDTO.getId());
        BrandDTO brandDTO_Out = brandService.updateBrand(brandEntity);
        logger.info("brand controller / brand-service add method called={0} "+ brandDTO_Out);

        return new ResponseEntity<>(brandDTO_Out, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{brandId}")
    public ResponseEntity<BrandDTO> deleteById(@PathVariable("brandId") Long id){
        logger.info("brand controller / brand-service delete method will called for brandId:{0} " +id);
        brandService.deleteBrand(id);
        logger.info("brand controller / product-service delete method called for brandId:{0} " + id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
