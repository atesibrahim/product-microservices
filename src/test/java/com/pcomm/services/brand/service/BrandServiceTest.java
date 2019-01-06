package com.pcomm.services.brand.service;
import com.pcomm.brand.exceptions.BrandNotFoundExceptions;
import com.pcomm.brand.mapper.BrandMapper;
import com.pcomm.brand.model.Brand;
import com.pcomm.brand.repository.BrandRepository;
import com.pcomm.brand.service.Impl.BrandServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class BrandServiceTest {

    @InjectMocks
    private BrandServiceImpl brandServiceMock;

    @Mock
    private BrandRepository brandRepositoryMock;

    Brand brand = new Brand();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        brand.setBrandName("apple");
        brand.setId(1l);

        List<Brand> brands = new ArrayList<>();
        brands.add(brand);

        when(brandRepositoryMock.save(any(Brand.class))).thenReturn(brand);
        when(brandRepositoryMock.findByBrandNameContaining(any(String.class))).thenReturn(brands);
    }

    @Test
    public void testBrandAddShouldSuccess() throws Exception {

        Brand resultBrand = BrandMapper.convertToEntity(brandServiceMock.addBrand(brand));

        assertThat(resultBrand.getBrandName(), is(equalTo(brand.getBrandName())));
        verify(brandRepositoryMock, times(1)).save(brand);
    }

    @Test
    public void testBrandUpdateShouldSuccess() throws Exception {

        when(brandRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(brand));
        Brand resultBrand = BrandMapper.convertToEntity(brandServiceMock.updateBrand(brand));

        assertThat(resultBrand.getBrandName(), is(equalTo(brand.getBrandName())));
        verify(brandRepositoryMock, times(1)).save(brand);
    }

    @Test(expected = AssertionError.class)
    public void testBrandDeleteShouldSuccess() throws Exception {
        String errorMessage = "No brand found by brand id: " + 1l;
        when(brandRepositoryMock.findById(1l)).thenReturn(Optional.of(brand));

        assertThatThrownBy(() -> brandServiceMock.deleteBrand(1l))
                .isInstanceOf(BrandNotFoundExceptions.class)
                .hasMessage(errorMessage);
    }

    @Test
    public void testBrandNotFoundException() throws Exception {

        String errorMessage = "No brand found by brand id: " + 1l;
        when(brandRepositoryMock.findById(1l)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> brandServiceMock.updateBrand(brand))
                .isInstanceOf(BrandNotFoundExceptions.class)
                .hasMessage(errorMessage);

    }

    @Test
    public void testBrandNameNotFoundException() throws Exception {

        List<Brand> brands = new ArrayList();
        String errorMessage = "No brand found by brand id: " + 1l;
        when(brandRepositoryMock.findByBrandNameContaining("apple")).thenReturn(brands);

        brandServiceMock.findByBrandNameContaining("apple");


    }

    @Test
    public void testFindBrandByBrandId() throws Exception {

        List<Brand> brands = new ArrayList();
        when(brandRepositoryMock.findById(1l)).thenReturn(Optional.empty());

        brandServiceMock.findBrandById(1l);

    }


    @Test(expected = AssertionError.class)
    public void testFindAllBrands() throws Exception {

        List<Brand> brands = new ArrayList();
        String errorMessage = "No brand found by brand id: " + 1l;
        when(brandRepositoryMock.findAll()).thenReturn(brands);

        assertThatThrownBy(() -> brandServiceMock.allBrands())
                .isInstanceOf(BrandNotFoundExceptions.class)
                .hasMessage(errorMessage);


    }

}
