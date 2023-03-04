package com.example.transactionmanagementdemo.service;


import com.example.transactionmanagementdemo.dao.OrderDao;
import com.example.transactionmanagementdemo.dao.OrderProductDao;
import com.example.transactionmanagementdemo.dao.ProductDao;
import com.example.transactionmanagementdemo.domain.entity.OrderProduct;
import com.example.transactionmanagementdemo.domain.entity.Orders;
import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.request.BuyRequest;
import com.example.transactionmanagementdemo.domain.request.Buyitem;
import com.example.transactionmanagementdemo.domain.response.OrderDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderProductService {

    private OrderProductDao orderProductDao;

    private ProductDao productDao;

    private OrderDao orderDao;

    @Autowired
    public void setOrderDao(OrderDao orderDao) {this.orderDao = orderDao;}

    @Autowired
    public void setOrderProductDao(OrderProductDao orderProductDao) {
        this.orderProductDao = orderProductDao;
    }

    @Autowired
    public void setProductDao(ProductDao productDao) {this.productDao = productDao;}

    @Transactional
    public void createNewOrderProduct(Orders orders, BuyRequest request) throws Exception {
        List<Buyitem> buyitems = request.getBuyitems();

        for (Buyitem item : buyitems) {
            Product product = productDao.getProductById(item.getProduct_id());
            int buy_quantity = item.getQuantity();
            orderProductDao.createNewOrderProduct(orders, product, buy_quantity);
            productDao.updateStock(item.getProduct_id(), product.getStock_quantity() - buy_quantity);
        }
    }

    @Transactional
    public List<OrderDetailsResponse> getOrderProductByOrder(Integer order_id) throws Exception {
        List<OrderDetailsResponse> items = new ArrayList<>();
        Orders order = orderDao.getOrderById(order_id);
        List<OrderProduct> all = orderProductDao.getOrderProductByOrder(order);
        for (OrderProduct test: all) {
            OrderDetailsResponse item = new OrderDetailsResponse();
            item.setProduct_id(test.getOrder_product_id());
            item.setRetail_price(test.getExecution_retail_price());
            item.setQuantity(test.getPurchased_quantity());
            items.add(item);
        }
        return items;
    }
}
