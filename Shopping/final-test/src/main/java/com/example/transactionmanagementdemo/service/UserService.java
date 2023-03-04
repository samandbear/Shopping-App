package com.example.transactionmanagementdemo.service;

import com.example.transactionmanagementdemo.dao.ProductDao;
import com.example.transactionmanagementdemo.dao.UserDao;
import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.domain.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;


@Service
public class UserService {
    private UserDao userDao;

    private ProductDao productDao;

    @Autowired
    public void ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }
    @Autowired
    public void UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public void createUser(LoginRequest user) throws Exception {
        List<User> sameUsername = userDao.getUserByUsername(user.getUsername());
        if (sameUsername != null && sameUsername.size() > 0) {
            throw new Exception("Username existed");
        }
        List<User> sameUserEmail = userDao.getUserByEmail(user.getEmail());
        if (sameUserEmail != null && sameUserEmail.size() > 0) {
            throw new Exception("Email existed");
        }

        userDao.createUser(user.getUsername(), user.getPassword(), user.getEmail());
    }

    @Transactional
    public User getUserByUsername(String username) throws Exception {
        List<User> users = userDao.getUserByUsername(username);
        if (users.size() == 1) {
            return users.get(0);
        } else {
            throw new Exception("We should only have 1 user");
        }
    }

    @Transactional
    public User getUserByUserId(Integer user_id) throws Exception {
        List<User> users = userDao.getUserByUserId(user_id);
        if (users.size() == 1) {
            return users.get(0);
        } else {
            throw new Exception("We should only have 1 user");
        }
    }

    @Transactional
    public void addToWatchList(Integer user_id, Integer product_id) throws Exception {
        User user = userDao.getUserAndProductByUserId(user_id).get(0);
        Product product = productDao.getOrderProductById(product_id);
        userDao.addToWatchList(user, product);
    }


    @Transactional
    public void removeFromWatchList(Integer user_id, Integer product_id) throws Exception {
        User user = userDao.getUserAndProductByUserId(user_id).get(0);
        Product product = productDao.getOrderProductById(product_id);
        userDao.removeFromWatchList(user, product);
    }


    @Transactional
    public Set<Product> viewWatchlist(Integer user_id) throws Exception {
        return userDao.viewWatchlist(user_id);
    }

}
