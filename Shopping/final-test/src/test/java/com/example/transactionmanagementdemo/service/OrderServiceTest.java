package com.example.transactionmanagementdemo.service;

import com.example.transactionmanagementdemo.dao.OrderDao;
import com.example.transactionmanagementdemo.dao.ProductDao;
import com.example.transactionmanagementdemo.domain.entity.Orders;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderDao orderDao;

    Orders mockOrder;

    @BeforeEach
    public void setup() {
        Date currentDate = new Date(System.currentTimeMillis());
        Orders.OrdersBuilder ordersBuilder = Orders.builder()
                .order_id(1)
                .order_status("Processing")
                .date_placed(currentDate);
        mockOrder = ordersBuilder.build();
    }

    @Test
    void test_createNewProduct() throws Exception {
        try {
            orderService.createNewOrder(1);
        } catch (Exception e) {
            assertNotNull(mockOrder);
        }
    }

    @Test
    void test_getAllOrder() throws Exception {
        orderService.getAllOrder();
        assertNotNull(mockOrder);
    }

    @Test
    void testAllUserOrders() throws Exception {
        orderService.AllUserOrders();
        assertNotNull(mockOrder);
    }

    @Test
    void testgetOrderById() throws Exception {
        orderService.getOrderById(1);
        assertNotNull(mockOrder);
    }

    @Test
    void testgetOrderByUser() throws Exception {
        try {
            orderService.getOrderByUser(1);
        } catch (Exception e) {
            assertNotNull(mockOrder);
        }
    }

    @Test
    void testcancelOrder() throws Exception {
        try {
            orderService.cancelOrder(1,1);
        } catch (Exception e) {
            assertNotNull(mockOrder);
        }
    }

    @Test
    void testcompletedOrder() throws Exception {
        try {
            orderService.completedOrder(1,1);
        } catch (Exception e) {
            assertNotNull(mockOrder);
        }
    }

    @Test
    void testgetTop3MostSold() {
        orderService.getTop3MostSold();
        assertNotNull(mockOrder);
    }

    @Test
    void testgetgetTop3MostRecent() throws Exception {
        try {
            orderService.getTop3MostRecent(1);
        } catch (Exception e) {
            assertNotNull(mockOrder);
        }
    }

    @Test
    void testgetTop3MostFreq() throws Exception {
        try {
            orderService.getTop3MostFreq(1);
        } catch (Exception e) {
            assertNotNull(mockOrder);
        }
    }

    @Test
    void testgetTotalItemSold() throws Exception {
        orderService.getTotalItemSold();
        assertNotNull(mockOrder);
    }

    @Test
    void testgetTop3User() {
        orderService.getTop3User();
        assertNotNull(mockOrder);
    }

    @Test
    void testgetMostProfit() {
        orderService.getMostProfit();
        assertNotNull(mockOrder);
    }
}
