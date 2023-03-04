package com.example.transactionmanagementdemo.domain.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Buyitem {
    private int product_id;
    private int quantity;
}

