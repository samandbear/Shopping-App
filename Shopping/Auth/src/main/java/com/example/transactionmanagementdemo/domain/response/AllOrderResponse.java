package com.example.transactionmanagementdemo.domain.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllOrderResponse {
    List<com.example.transactionmanagementdemo.domain.entity.Orders> Orders;
}
