package com.example.transactionmanagementdemo.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="Order_Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id", unique = true, nullable = false)
    private Integer order_product_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Orders orders_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product_id;

    @Column(name = "purchased_quantity", nullable = false)
    private int purchased_quantity;

    @Column(name = "execution_retail_price")
    private float execution_retail_price;

    @Column(name = "execution_wholesale_price")
    private float execution_wholesale_price;
}
