package com.pcomm.services.brand.service;
import com.pcomm.brand.dto.BrandDTO;
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

import javax.security.auth.login.AccountNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
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

        Brand resultBrand = BrandMapper.convertToEntity(brandServiceMock.addEntity(brand));

        assertThat(resultBrand.getBrandName(), is(equalTo(brand.getBrandName())));
        verify(brandRepositoryMock, times(1)).save(brand);
    }

    @Test
    public void testBrandUpdateShouldSuccess() throws Exception {

        when(brandRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(brand));
        Brand resultBrand = BrandMapper.convertToEntity(brandServiceMock.updateEntity(brand));

        assertThat(resultBrand.getBrandName(), is(equalTo(brand.getBrandName())));
        verify(brandRepositoryMock, times(1)).save(brand);
    }

    @Test
    public void testBrandDeleteShouldSuccess() throws Exception {

    }

    @Test
    public void testBrandNotFoundException() throws Exception {

        String errorMessage = "No brand found by brand id: " + 1l;
        when(brandRepositoryMock.findById(1l)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> brandServiceMock.updateEntity(brand))
                .isInstanceOf(BrandNotFoundExceptions.class)
                .hasMessage(errorMessage);

    }


}
