package com.example.transactionmanagementdemo.domain.request;

import lombok.*;

@Getter
@Setter
@Builder
public class NewProductRequest {
    private String name;
    private String description;
    private float retail_price;
    private float wholesale_price;
    private int stock_quantity;
}
