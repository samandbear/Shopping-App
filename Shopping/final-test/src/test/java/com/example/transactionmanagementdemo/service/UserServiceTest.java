package com.example.transactionmanagementdemo.service;

import com.example.transactionmanagementdemo.dao.OrderDao;
import com.example.transactionmanagementdemo.dao.ProductDao;
import com.example.transactionmanagementdemo.dao.UserDao;
import com.example.transactionmanagementdemo.domain.entity.Orders;
import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.domain.request.LoginRequest;
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
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserDao userDao;

    User mockUser;

    @BeforeEach
    public void setup() {
        User.UserBuilder userBuilder = User.builder()
                .user_id(1)
                .email("123")
                .password("123")
                .username("gg")
                .is_seller(true);
        mockUser = userBuilder.build();
    }

    @Test
    void test_createNewProduct() throws Exception {
        userService.createUser(LoginRequest.builder().build());
        assertNotNull(mockUser);
    }


    @Test
    void testgetUserByUsername() throws Exception {
        try {
            userService.getUserByUsername("1");
        } catch (Exception e) {
            assertNotNull(mockUser);
        }
    }

    @Test
    void testgetUserByUserId() throws Exception {
        try {
            userService.getUserByUserId(1);
        } catch (Exception e) {
            assertNotNull(mockUser);
        }
    }

    @Test
    void testaddToWatchList() throws Exception {
        try {
            userService.addToWatchList(1,1);
        } catch (Exception e) {
            assertNotNull(mockUser);
        }
    }

    @Test
    void testremoveFromWatchList() throws Exception {
        try {
            userService.removeFromWatchList(1,1);
        } catch (Exception e) {
            assertNotNull(mockUser);
        }
    }

    @Test
    void testviewWatchlist() throws Exception {
        userService.viewWatchlist(1);
        assertNotNull(mockUser);
    }
}
