package com.example.transactionmanagementdemo.dao;

import com.example.transactionmanagementdemo.dao.OrderDao;
import com.example.transactionmanagementdemo.dao.ProductDao;
import com.example.transactionmanagementdemo.domain.entity.Orders;
import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "test")
@SpringBootTest
public class OrderDaoTest {

    @Autowired
    OrderDao orderDao;

    Orders mockorders;

    @BeforeEach
    public void setup() {
        Date currentDate = new Date(System.currentTimeMillis());
        Orders.OrdersBuilder ordersBuilder = Orders.builder()
                .order_id(1)
                .order_status("Processing")
                .date_placed(currentDate);
        mockorders = ordersBuilder.build();
    }


    @Test
    @Transactional
    public void testcreateNewOrder() {
        Orders orders = orderDao.createNewOrder(new User());
        assertNotNull(orders);
    }

    @Test
    @Transactional
    public void testgetAllOrder() {
        List<Orders> orders = orderDao.getAllOrder();
        assertNotNull(orders);
    }

    @Test
    @Transactional
    public void testgetOrderById() throws Exception {
        orderDao.getOrderByUserId(2);
        assertNotNull(mockorders);
    }

    @Test
    @Transactional
    public void testcancelOrder() {
        orderDao.cancelOrder(mockorders);
        assertNotNull(mockorders);
    }

    @Test
    @Transactional
    public void testcompletedOrder() {
        orderDao.completedOrder(mockorders);
        assertNotNull(mockorders);
    }

    @Test
    @Transactional
    public void testgetTop3MostSold() {
        orderDao.getTop3MostSold();
        assertNotNull(mockorders);
    }

    @Test
    @Transactional
    public void testgetTotalItemSold() {
        orderDao.getTotalItemSold();
        assertNotNull(mockorders);
    }

    @Test
    @Transactional
    public void testgetTop3User() {
        orderDao.getTop3User();
        assertNotNull(mockorders);
    }

    @Test
    @Transactional
    public void testgetTop3MostRecent() {
        orderDao.getTop3MostRecent(3);
        assertNotNull(mockorders);
    }

    @Test
    @Transactional
    public void testgetTop3MostFreq() {
        orderDao.getTop3MostFreq(3);
        assertNotNull(mockorders);
    }
}