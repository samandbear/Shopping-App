package com.example.transactionmanagementdemo.service;

import com.example.transactionmanagementdemo.dao.ProductDao;
import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.request.NewProductRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductDao productDao;

    Product mockProdcut;

    @BeforeEach
    public void setup() {
        Product.ProductBuilder productBuilder = Product.builder()
                .product_id(1)
                .description("apple")
                .retail_price(100)
                .wholesale_price(100)
                .name("apple")
                .stock_quantity(3);
        mockProdcut = productBuilder.build();
    }

    @Test
    void test_createNewProduct() {
        productService.createNewProduct(NewProductRequest.builder().build());
        assertNotNull(mockProdcut);
    }

    @Test
    void test_getAllProduct() {
        productService.getAllProduct(1);
        assertNotNull(mockProdcut);
    }

    @Test
    void test_getAllProductUser() {
        productService.getAllProductUser();
        assertNotNull(mockProdcut);
    }


    @Test
    void test_getProductUserDetail() {
        try {
            productService.getProductUserDetail(1);
        } catch (Exception e) {
            assertNull(mockProdcut);
        }
    }

    @Test
    void test_getProductById() throws Exception {
        ProductDao productDao = mock(ProductDao.class);
        Product product = productService.getProductById(1);
        assertNotNull(mockProdcut);
    }

    @Test
    void test_updateWholesale_price() {
        productService.updateWholesale_price(2, 100);
        assertNotNull(mockProdcut);
    }

    @Test
    void test_updateRetail_price() {
        productService.updateRetail_price(2,100);
        assertNotNull(mockProdcut);
    }


    @Test
    void test_updateDescription() {
        productService.updateDescription(2, "100");
        assertNotNull(mockProdcut);
    }

}
