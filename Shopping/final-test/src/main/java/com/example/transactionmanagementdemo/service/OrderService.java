package com.example.transactionmanagementdemo.service;

import com.example.transactionmanagementdemo.dao.OrderDao;
import com.example.transactionmanagementdemo.dao.OrderProductDao;
import com.example.transactionmanagementdemo.dao.ProductDao;
import com.example.transactionmanagementdemo.dao.UserDao;
import com.example.transactionmanagementdemo.domain.entity.OrderProduct;
import com.example.transactionmanagementdemo.domain.entity.Orders;
import com.example.transactionmanagementdemo.exception.NoRightException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.transactionmanagementdemo.domain.entity.User;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class OrderService {
    private OrderDao orderDao;

    private UserService userService;

    private OrderProductDao orderProductDao;

    private ProductDao productDao;

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {this.userDao = userDao;}

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Autowired
    public void setOrderProductDao(OrderProductDao orderProductDao) {this.orderProductDao = orderProductDao;}


    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public Orders createNewOrder(Integer user_id) throws Exception {
//        String username1 = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
//        User user1 = userDao.getUserByUsername(username1).get(0);
//        if (user1.getUser_id() != user_id) {
//            throw new NoRightException("you are not allowed");
//        }
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userDao.getUserByUsername(username).get(0);
        if (user.getUser_id() != user_id) {
            throw new Exception("you are not allowed");
        }
        User user2 = userService.getUserByUserId(user_id);
        return orderDao.createNewOrder(user2);
    }

    @Transactional
    public List<Orders> getAllOrder() throws Exception {
        return orderDao.getAllOrder();
    }

    @Transactional
    public List<Orders> AllUserOrders() throws Exception {
        return orderDao.getAllOrder();
    }

    @Transactional
    public Orders getOrderById(Integer order_id) throws Exception {
        return orderDao.getOrderById(order_id);
    }

    @Transactional
    public List<Orders> getOrderByUser(Integer user_id) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userDao.getUserByUsername(username).get(0);
        if (user.getUser_id() != user_id) {
            throw new NoRightException("you are not allowed");
        }
        return orderDao.getOrderByUserId(user_id);
    }

    @Transactional
    public void cancelOrder(Integer order_id, Integer user_id) throws NoRightException,Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user1 = userDao.getUserByUsername(username).get(0);
        if (user1.getUser_id() != user_id) {
            throw new NoRightException("you are not allowed");
        }
        Orders order = orderDao.getOrderById(order_id);
        int order_userId = order.getUser_id().getUser_id();
        User user = userService.getUserByUserId(user_id);
        if (user.is_seller() || user_id == order_userId) {
            if (order.getOrder_status().equals("Processing")) {
                List<OrderProduct> orderProducts = order.getOrder_products();
                for (OrderProduct orderProduct: orderProducts) {
                    int product_id = orderProduct.getProduct_id().getProduct_id();
                    int updated_stock = orderProduct.getPurchased_quantity() + productDao.getProductById(product_id).getStock_quantity();
                    productDao.updateStock(product_id, updated_stock);
                }

                orderDao.cancelOrder(order);
            } else {
                throw new Exception("Order is not Processing");
            }
        } else {
            throw new Exception("You don't have right permission");
        }
    }

    @Transactional
    public void completedOrder(Integer order_id, Integer user_id) throws  Exception{
        Orders order = orderDao.getOrderById(order_id);
        User user = userService.getUserByUserId(user_id);
        if (user.is_seller()) {
            if (order.getOrder_status().equals("Processing")) {
                orderDao.completedOrder(order);
            } else {
                throw new Exception("Order is not Processing");
            }
        } else {
            throw new Exception("You don't have right permission");
        }
    }

    @Transactional
    public List<String> getTop3MostSold() {
        return orderDao.getTop3MostSold();
    }

    @Transactional
    public List<String> getTop3MostRecent(Integer user_id) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userDao.getUserByUsername(username).get(0);
        if (user.getUser_id() != user_id) {
            throw new NoRightException("you are not allowed");
        }
        return orderDao.getTop3MostRecent(user_id);
    }

    @Transactional
    public List<String> getTop3MostFreq(Integer user_id) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userDao.getUserByUsername(username).get(0);
        if (user.getUser_id() != user_id) {
            throw new NoRightException("you are not allowed");
        }
        return orderDao.getTop3MostFreq(user_id);
    }

    @Transactional
    public Long getTotalItemSold() {
        return orderDao.getTotalItemSold();
    }

    @Transactional
    public List<String> getTop3User() {
        return orderDao.getTop3User();
    }


    @Transactional
    public String getMostProfit() {
        return orderDao.getMostProfit();
    }
}
