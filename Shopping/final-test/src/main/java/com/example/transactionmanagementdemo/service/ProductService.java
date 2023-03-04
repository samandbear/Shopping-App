package com.example.transactionmanagementdemo.service;

import com.example.transactionmanagementdemo.dao.ProductDao;
import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.request.NewProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class ProductService {
    private ProductDao productDao;

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public void createNewProduct(NewProductRequest request) {
        productDao.createNewProduct(request.getName(), request.getDescription(), request.getRetail_price(), request.getWholesale_price(), request.getStock_quantity());
    }

    @Transactional
    public List<Product> getAllProduct(Integer product_id) {
        return productDao.getAllProduct(product_id);
    }

    @Transactional
    public List<Product> getAllProductUser() {
        return productDao.getAllProductUser();
    }

    @Transactional
    public List<Product> getProductUserDetail(Integer product_id) {
        return productDao.getProductUserDetail(product_id);
    }

    @Transactional
    public Product getProductById(Integer product_id) throws Exception {
        return productDao.getProductById(product_id);
    }

    @Transactional
    public void updateWholesale_price(Integer product_id, float wholesale_price) {
        productDao.updateWholesale_price(product_id, wholesale_price);
    }

    @Transactional
    public void updateRetail_price(Integer product_id, float retail_price) {
        productDao.updateRetail_price(product_id, retail_price);
    }

    @Transactional
    public void updateDescription(Integer product_id, String description) {
        productDao.updateDescription(product_id, description);
    }
}
