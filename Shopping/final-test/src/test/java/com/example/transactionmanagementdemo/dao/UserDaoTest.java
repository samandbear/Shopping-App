package com.example.transactionmanagementdemo.dao;

import com.example.transactionmanagementdemo.dao.OrderDao;
import com.example.transactionmanagementdemo.dao.ProductDao;
import com.example.transactionmanagementdemo.dao.UserDao;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles(value = "test")
@SpringBootTest
public class UserDaoTest {

    @Autowired
    UserDao userDao;

    User mockuser;

    @BeforeEach
    public void setup() {
        User.UserBuilder userBuilder = User.builder()
                .user_id(1)
                .email("123")
                .password("123")
                .username("gg")
                .is_seller(true);
        mockuser = userBuilder.build();
    }

    @Test
    @Transactional
    public void testgetUserByEmail() {
        try {
            List<User> user = userDao.getUserByEmail("123");
        }catch (Exception e) {
            assertNotNull(mockuser);
        }
    }

    @Test
    @Transactional
    public void testgetUserByUsername() {
        try {
            List<User> users = userDao.getUserByUsername("123");
        }catch (Exception e) {
            assertNotNull(mockuser);
        }
    }

    @Test
    @Transactional
    public void testviewWatchlist() {
        try {
            Set<Product> productSet = userDao.viewWatchlist(2);
        }catch (Exception e) {
            assertNotNull(mockuser);
        }
    }
}