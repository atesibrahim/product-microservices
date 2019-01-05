package com.pcomm.brand.repository;

import com.pcomm.brand.model.Brand;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BrandRepository extends CrudRepository<Brand, Long> {

    public List<Brand> findByBrandNameContaining(String brandName);

    public List<Brand> findByBrandNameEquals(String brandName);

}
