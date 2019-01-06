package com.pcomm.brand.service;



import com.pcomm.brand.dto.BrandDTO;
import com.pcomm.brand.model.Brand;

import java.util.List;

public interface BrandService {


    public List<BrandDTO> findByBrandNameContaining(String brandName);

    public BrandDTO findBrandById(Long id);

    public List<BrandDTO> allBrands();

    public void deleteBrand(Long id);

    public BrandDTO addBrand(Brand brand);

    public BrandDTO updateBrand(Brand brand);



}
