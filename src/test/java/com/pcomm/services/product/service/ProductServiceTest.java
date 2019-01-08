package com.pcomm.services.product.service;


import com.pcomm.product.exceptions.ProductNotFoundExceptions;
import com.pcomm.product.mapper.ProductMapper;
import com.pcomm.product.model.Product;
import com.pcomm.product.repository.ProductRepository;
import com.pcomm.product.service.Impl.ProductServiceImpl;
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
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productServiceMock;

    @Mock
    private ProductRepository productRepositoryMock;

    Product product = new Product();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        product.setProductName("6s");
        product.setId(1l);

        List<Product> products = new ArrayList<>();
        products.add(product);

        when(productRepositoryMock.save(any(Product.class))).thenReturn(product);
        when(productRepositoryMock.findByProductNameContaining(any(String.class))).thenReturn(products);
    }

    @Test
    public void testProductAddShouldSuccess() throws Exception {

        Product resultProduct = ProductMapper.convertToEntity(productServiceMock.addProduct(product));

        assertThat(resultProduct.getProductName(), is(equalTo(product.getProductName())));
        verify(productRepositoryMock, times(1)).save(product);
    }

    @Test
    public void testProductUpdateShouldSuccess() throws Exception {

        when(productRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(product));
        Product resultProduct = ProductMapper.convertToEntity(productServiceMock.updateProduct(product));

        assertThat(resultProduct.getProductName(), is(equalTo(product.getProductName())));
        verify(productRepositoryMock, times(1)).save(product);
    }

    @Test(expected = AssertionError.class)
    public void testProductDeleteShouldSuccess() throws Exception {
        String errorMessage = "No product found by product id: " + 1l;
        when(productRepositoryMock.findById(1l)).thenReturn(Optional.of(product));

        assertThatThrownBy(() -> productServiceMock.deleteProduct(1l))
                .isInstanceOf(ProductNotFoundExceptions.class)
                .hasMessage(errorMessage);
    }

    @Test
    public void testProductNotFoundException() throws Exception {

        String errorMessage = "No product found by product id: " + 1l;
        when(productRepositoryMock.findById(1l)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productServiceMock.updateProduct(product))
                .isInstanceOf(ProductNotFoundExceptions.class)
                .hasMessage(errorMessage);

    }


    @Test
    public void testProductNameNotFoundException() throws Exception {

        List<Product> products = new ArrayList();
        String errorMessage = "No product found by product id: " + 1l;
        when(productRepositoryMock.findByProductNameContaining("6s")).thenReturn(products);

         productServiceMock.findByProductNameContainingIgnoreCase("6s");

    }

    @Test
    public void testFindProductByProductId() throws Exception {

        List<Product> products = new ArrayList();
        String errorMessage = "No product found by product id: " + 1l;
        when(productRepositoryMock.findById(1l)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productServiceMock.findProductById(1l))
                .isInstanceOf(ProductNotFoundExceptions.class)
                .hasMessage(errorMessage);

    }


    @Test
    public void testFindAllProducts() throws Exception {

        List<Product> products = new ArrayList();
        String errorMessage = "No product found by product id: " + 1l;
        when(productRepositoryMock.findAll()).thenReturn(products);

        productServiceMock.allProducts();


    }


}
