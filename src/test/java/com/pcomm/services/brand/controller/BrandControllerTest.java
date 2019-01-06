package com.pcomm.services.brand.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcomm.brand.controller.BrandController;
import com.pcomm.brand.dto.BrandDTO;
import com.pcomm.brand.model.Brand;
import com.pcomm.brand.service.BrandService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BrandController.class)
public class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrandService brandServiceMock;

    @Autowired
    ObjectMapper objectMapper;

    private static final String url = "/brand";

    Brand brand = new Brand();

    BrandDTO brandDTO = new BrandDTO();

    Instant now = Instant.now();
    LocalDateTime localDateTime = LocalDateTime.now();

    @Before
    public void setup() {

        brandDTO.setId(1l);
        brandDTO.setBrandName("Apple");
        brandDTO.setCreateUserId("testuser");
        brandDTO.setUpdateUserId("testuser");

        brand.setId(1l);
        brand.setBrandName("Apple");
        brand.setCreateUserId("testuser");
        brand.setUpdateUserId("testuser");
    }

    @Test
    public void testAddBrandShouldSuccess() throws Exception {

        when(brandServiceMock.addBrand(refEq(brand))).thenReturn(brandDTO);
        mockMvc.perform(post(url + "/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(brandDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandName", is("Apple")))
                .andExpect(jsonPath("$.createUserId", is("testuser")));

        verify(brandServiceMock, times(1)).addBrand(refEq(brand));


    }

    @Test
    public void testUpdateBrandShouldSuccess() throws Exception {

        when(brandServiceMock.updateBrand(refEq(brand))).thenReturn(brandDTO);
        mockMvc.perform(put(url + "/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(brandDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.createUserId", is("testuser")));
        verify(brandServiceMock, times(1)).updateBrand(refEq(brand));
    }

    @Test
    public void testFindAll() throws Exception {
        List<BrandDTO> brandDTOS = Arrays.asList(brandDTO);

        given(brandServiceMock.allBrands()).willReturn(brandDTOS);

        mockMvc.perform(get(url + "/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].brandName", is(brand.getBrandName())));
    }


    @Test
    public void testFindAllBrandsByBrandName() throws Exception {
        List<BrandDTO> brandDTOS = Arrays.asList(brandDTO);

        given(brandServiceMock.findByBrandNameContaining(anyString())).willReturn(brandDTOS);

        mockMvc.perform(get(url + "/brandName/{brandName}", "Apple")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].brandName", is(brand.getBrandName())));
    }

    @Test
    public void testFindBrandsByBrandId() throws Exception {
        List<BrandDTO> brandDTOS = Arrays.asList(brandDTO);

        given(brandServiceMock.findBrandById(anyLong())).willReturn(brandDTO);

        mockMvc.perform(get(url + "/read/{brandId}", 1l)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandName", is("Apple")));
    }



    @Test
    public void testDeleteBrandShouldSuccess() throws Exception {

        doNothing().when(brandServiceMock).deleteBrand(Long.valueOf(1));

        mockMvc.perform(delete(url + "/delete/{brandId}", Long.valueOf(1l))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(brandServiceMock, times(1)).deleteBrand(Long.valueOf(1l));

    }
}
