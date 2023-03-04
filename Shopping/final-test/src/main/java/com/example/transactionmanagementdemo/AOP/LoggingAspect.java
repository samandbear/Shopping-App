package com.example.transactionmanagementdemo.AOP;

import com.example.transactionmanagementdemo.domain.entity.Orders;
import com.example.transactionmanagementdemo.domain.entity.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


@Aspect
@Component
public class LoggingAspect {
    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @After("com.example.transactionmanagementdemo.AOP.PointCuts.createOrder(user)")
    public void logNewOrder(JoinPoint joinPoint, User user){
        logger.info("New Order: {} {}", new Timestamp(System.currentTimeMillis()), joinPoint.getSignature());
    }

    @After("com.example.transactionmanagementdemo.AOP.PointCuts.completeOrder(orders)")
    public void logCompleteOrder(JoinPoint joinPoint, Orders orders){
        logger.info("CompleteOrder: {} {}", new Timestamp(System.currentTimeMillis()), joinPoint.getSignature());
    }

    @After("com.example.transactionmanagementdemo.AOP.PointCuts.cancelOrder(orders)")
    public void logCancelOrder(JoinPoint joinPoint, Orders orders){
        logger.info("CancelOrder: {} {}", new Timestamp(System.currentTimeMillis()), joinPoint.getSignature());
    }
}
