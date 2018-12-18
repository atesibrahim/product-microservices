package com.pcomm.services.product.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcomm.product.controller.ProductController;
import com.pcomm.product.dto.ProductDTO;
import com.pcomm.product.model.Product;
import com.pcomm.product.service.ProductService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productServiceMock;

    @Autowired
    ObjectMapper objectMapper;

    private static final String url = "/product";

    Product product = new Product();

    ProductDTO productDTO = new ProductDTO();

    Instant now = Instant.now();
    LocalDateTime localDateTime = LocalDateTime.now();

    @Before
    public void setup() {

        productDTO.setBrandId(1l);
        productDTO.setCategoryId(2l);
        productDTO.setCreateUserId("testuser");
        productDTO.setProductName("6s");
        productDTO.setProductType("phone");
        productDTO.setUpdateUserId("testuser");

        product.setBrandId(1l);
        product.setCategoryId(2l);
        product.setCreateUserId("testuser");
        product.setProductName("6s");
        product.setProductType("phone");
        product.setUpdateUserId("testuser");
    }

    @Test
    public void testAddProductShouldSuccess() throws Exception {

        when(productServiceMock.addEntity(refEq(product))).thenReturn(productDTO);
        mockMvc.perform(post(url + "/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.createUserId", is("testuser")));

        verify(productServiceMock, times(1)).addEntity(refEq(product));


    }

    @Test
    public void testUpdateProductShouldSuccess() throws Exception {

        when(productServiceMock.updateEntity(refEq(product))).thenReturn(productDTO);
        mockMvc.perform(put(url + "/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.createUserId", is("testuser")));
        verify(productServiceMock, times(1)).updateEntity(refEq(product));
    }

    @Test
    public void testFindAllProductsByProductName() throws Exception {
        List<ProductDTO> products = Arrays.asList(productDTO);

        given(productServiceMock.findByProductNameContainingIgnoreCase(anyString())).willReturn(products);

        mockMvc.perform(get(url + "/productName/{productName}", "6s")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].productName", is(product.getProductName())));
    }
    @Test
    public void testFindAllProductsByProductType() throws Exception {
        List<ProductDTO> products = Arrays.asList(productDTO);

        given(productServiceMock.findByProductTypeContainingIgnoreCase(anyString())).willReturn(products);

        mockMvc.perform(get(url + "/productType/{productType}", "phone")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].productType", is(product.getProductType())));
    }


    @Test
    public void testDeleteProductShouldSuccess() throws Exception {

        doNothing().when(productServiceMock).deleteEntity(Long.valueOf(1));

        mockMvc.perform(delete(url + "/delete/{productId}", Long.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productServiceMock, times(1)).deleteEntity(Long.valueOf(1));

    }
}
