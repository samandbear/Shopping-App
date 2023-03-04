package com.example.transactionmanagementdemo.domain.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsResponse {
    int product_id;
    float retail_price;
    Integer quantity;
}
