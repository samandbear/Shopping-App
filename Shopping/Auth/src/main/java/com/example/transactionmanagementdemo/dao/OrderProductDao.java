package com.example.transactionmanagementdemo.dao;


import com.example.transactionmanagementdemo.domain.entity.Orders;
import com.example.transactionmanagementdemo.domain.entity.OrderProduct;
import com.example.transactionmanagementdemo.domain.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class OrderProductDao {
    @Autowired SessionFactory sessionFactory;

    public void createNewOrderProduct(Orders orders, Product product, int purchased_quantity) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrders_id(orders);
        orderProduct.setProduct_id(product);
        orderProduct.setPurchased_quantity(purchased_quantity);
        orderProduct.setExecution_retail_price(product.getRetail_price());
        orderProduct.setExecution_wholesale_price(product.getWholesale_price());

        Session session = null;

        try {
            session = sessionFactory.getCurrentSession();
            session.save(orderProduct);
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
    }

    public List<OrderProduct> getOrderProductByOrder(Orders orders) {
        List<OrderProduct> orderProduct = null;
        Session session = null;
        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<OrderProduct> cq = cb.createQuery(OrderProduct.class);
            Root<OrderProduct> root = cq.from(OrderProduct.class);
            Predicate predicate = cb.equal(root.get("orders_id"), orders);
            cq.select(root).where(predicate);
            orderProduct = session.createQuery(cq).getResultList();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  orderProduct;
    }
}
