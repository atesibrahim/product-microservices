package com.pcomm.brand.service;



import com.pcomm.brand.dto.BrandDTO;
import com.pcomm.brand.model.Brand;

import java.util.List;

public interface BrandService {


    public List<BrandDTO> findByBrandNameContaining(String brandName);

    public BrandDTO findEntityById(Long id);

    public List<BrandDTO> allBrands();

    public void deleteEntity(Long id);

    public BrandDTO addEntity(Brand brand);

    public BrandDTO updateEntity(Brand brand);



}
