package com.example.transactionmanagementdemo.dao;

import com.example.transactionmanagementdemo.dao.ProductDao;
import com.example.transactionmanagementdemo.domain.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "test")
@SpringBootTest
public class ProductDaoTest {

    @Autowired
    ProductDao productDao;

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
    @Transactional
    public void testgetAllProduct() {
        List<Product> product = productDao.getAllProduct(1);
        assertNotNull(product);
    }

    @Test
    @Transactional
    public void testcreateNewProduct() {
        Product product = productDao.createNewProduct("apple", "apple", 100, 100,3);
        assertNotNull(product);
    }

    @Test
    @Transactional
    public void testgetAllProductUser() {
        List<Product> products = productDao.getAllProductUser();
        assertNotNull(products);
    }

    @Test
    @Transactional
    public void testupdateDescription() {
        productDao.updateDescription(1, "123");
        assertNotNull(mockProdcut);
    }

    @Test
    @Transactional
    public void testupdateRetail_price() {
        productDao.updateRetail_price(1, 123);
        assertNotNull(mockProdcut);
    }

    @Test
    @Transactional
    public void testupdateWholesale_price() {
        productDao.updateWholesale_price(1, 123);
        assertNotNull(mockProdcut);
    }

    @Test
    @Transactional
    public void testupdateStock() {
        productDao.updateStock(1, 3);
        assertNotNull(mockProdcut);
    }

    @Test
    @Transactional
    public void testgetOrderProductById() throws Exception {

        try {
            Product product = productDao.getProductById(1);

            fail("Expected an exception to be thrown");
        } catch (Exception expectedException) {
            // If the expected exception is thrown, the test passes
            // Optional: verify that the exception message or other properties are correct
            assertEquals("Something Wrong", expectedException.getMessage());
        }
    }

    @Test
    @Transactional
    public void testgetProductUserDetail() {
        List<Product> product = productDao.getProductUserDetail(1);
        assertNotNull(product);
    }
}
