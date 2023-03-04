package com.example.transactionmanagementdemo.controller;

import com.example.transactionmanagementdemo.domain.request.RegRequest;
import com.example.transactionmanagementdemo.domain.response.LoginResponse;
import com.example.transactionmanagementdemo.exception.InvalidCredentialsException;
import com.example.transactionmanagementdemo.security.AuthUserDetail;
import com.example.transactionmanagementdemo.security.JwtProvider;
import com.example.transactionmanagementdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    private UserService userService;

    @Autowired
    public void setLoginService (UserService userService) {
        this.userService = userService;
    }

    private AuthenticationManager authenticationManager;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private JwtProvider jwtProvider;

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/auth/register")
    // a. Before being able to purchase products, a user has to first register and login
    public String register(@RequestBody RegRequest request) throws Exception {
        userService.createUser(request);
        return "succeed";
    }
    // we should have a login right with security.


    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody RegRequest request) throws Exception{

        Authentication authentication;

        //Try to authenticate the user using the username and password
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (AuthenticationException e){
            new InvalidCredentialsException("Incorrect credentials, please try again.");
            return LoginResponse.builder()
                    .message("Incorrect credentials, please try again.")
                    .build();
        }


        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal(); //getPrincipal() returns the user object

        //A token wil be created using the username/email/userId and permission
        String token = jwtProvider.createToken(authUserDetail);

        //Returns the token as a response to the frontend/postman
        return LoginResponse.builder()
                .message("Hello " + authUserDetail.getUsername())
                .token(token)
                .build();

    }
}
