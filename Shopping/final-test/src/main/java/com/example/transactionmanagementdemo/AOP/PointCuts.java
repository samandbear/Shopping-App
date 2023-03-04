package com.example.transactionmanagementdemo.AOP;

import com.example.transactionmanagementdemo.domain.entity.Orders;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import com.example.transactionmanagementdemo.domain.entity.User;

@Aspect
public class PointCuts {
    @Pointcut("execution(* com.example.transactionmanagementdemo.dao.OrderDao.createNewOrder(..)) && args(user)")
    public void createOrder(User user){}

    @Pointcut("execution(* com.example.transactionmanagementdemo.dao.OrderDao.completedOrder(..)) && args(orders)")
    public void completeOrder(Orders orders){}

    @Pointcut("execution(* com.example.transactionmanagementdemo.dao.OrderDao.cancelOrder(..)) && args(orders)")
    public void cancelOrder(Orders orders){}

}
