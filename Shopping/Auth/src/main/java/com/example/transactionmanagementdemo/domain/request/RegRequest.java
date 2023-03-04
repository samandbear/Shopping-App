package com.example.transactionmanagementdemo.domain.request;


import lombok.*;

@Getter
@Setter
@Builder
public class RegRequest {
    private String username;
    private String password;
    private String email;
}
