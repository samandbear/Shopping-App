package com.example.transactionmanagementdemo.controller;

import com.example.transactionmanagementdemo.domain.request.LoginRequest;
import com.example.transactionmanagementdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    private UserService userService;

    @Autowired
    public void setLoginService (UserService userService) {
        this.userService = userService;
    }
}
