package com.example.transactionmanagementdemo.domain.request;


import lombok.*;

import java.util.List;

@Getter
@Setter
public class BuyRequest {
    List<Buyitem> buyitems;
}
