package com.example.transactionmanagementdemo.dao;

import com.example.transactionmanagementdemo.domain.entity.Orders;
import com.example.transactionmanagementdemo.domain.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class OrderDao {
    @Autowired SessionFactory sessionFactory;

    public Orders createNewOrder(User user) {
        Orders orders = new Orders();
        orders.setUser_id(user);
        orders.setDate_placed(new Timestamp(System.currentTimeMillis()));
        orders.setOrder_status("Processing");

        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.save(orders);
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
        return orders;
    }

    public List<Orders> getAllOrder() {
        List<Orders> orders = null;
        Session session = null;

        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Orders> cq = cb.createQuery(Orders.class);
            Root<Orders> root = cq.from(Orders.class);
            cq.select(root);
            orders = session.createQuery(cq).getResultList();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
        return orders;
    }

    public Orders getOrderById(Integer order_id) throws Exception {
        List<Orders> orders = null;
        Session session = null;

        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Orders> cq = cb.createQuery(Orders.class);
            Root<Orders> root = cq.from(Orders.class);
            Predicate predicate = cb.equal(root.get("order_id"), order_id);
            cq.select(root).where(predicate);
            orders = session.createQuery(cq).getResultList();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if (orders!= null && orders.size() != 1) {
            throw new Exception("Something Wrong");
        }

        return orders.get(0);
    }

    public List<Orders> getOrderByUserId(Integer user_id) throws Exception {
        List<Orders> orders = null;
        Session session = null;

        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Orders> cq = cb.createQuery(Orders.class);
            Root<Orders> root = cq.from(Orders.class);
            Predicate predicate = cb.equal(root.get("user_id"), user_id);
            cq.select(root).where(predicate);
            orders = session.createQuery(cq).getResultList();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return orders;
    }


    public void cancelOrder(Orders orders) {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            Orders order = session.get(Orders.class, orders.getOrder_id());
            order.setOrder_status("Canceled");
            session.update(order);
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
    }

    public void completedOrder(Orders orders) {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            Orders order = session.get(Orders.class, orders.getOrder_id());
            order.setOrder_status("Completed");
            session.update(order);
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
    }


    public List<String> getTop3MostSold() {
        Session session = null;
        List<String> result = null;
        try {
            session = sessionFactory.getCurrentSession();

            String qryString = "SELECT p.name\n" +
                    "FROM Orders o\n" +
                    "JOIN o.order_products op \n" +
                    "JOIN op.product_id p \n" +
                    "WHERE o.order_status != 'Canceled' AND o.order_status != 'Processing'\n" +
                    "GROUP BY p.name\n" +
                    "ORDER BY SUM(op.purchased_quantity) DESC\n";
            Query query = session.createQuery(qryString);
            query.setMaxResults(3);
            result = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Long getTotalItemSold() {
        Session session = null;
        List<Long> result = null;
        try {
            session = sessionFactory.getCurrentSession();

            String qryString = "SELECT SUM(op.purchased_quantity)\n" +
                    "FROM Orders o\n" +
                    "JOIN o.order_products op \n" +
                    "WHERE o.order_status != 'Canceled' AND o.order_status != 'Processing'\n";
            Query query = session.createQuery(qryString);
            result = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.get(0);
    }

    public List<String> getTop3User() {
        Session session = null;
        List<String> result = null;
        try {
            session = sessionFactory.getCurrentSession();

            String qryString = "SELECT u.username\n" +
                    "FROM User u\n" +
                    "JOIN u.orders o\n" +
                    "WHERE o.order_status != 'Canceled' AND o.order_status != 'Processing'\n" +
                    "GROUP BY u.username \n" +
                    "ORDER BY COUNT(o.order_id) DESC, u.username ASC\n";
            Query query = session.createQuery(qryString);
            query.setMaxResults(3);
            result = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public String getMostProfit() {
        Session session = null;
        List<String> result = null;
        try {
            session = sessionFactory.getCurrentSession();

            String qryString = "SELECT p.name\n" +
                    "FROM Product p\n" +
                    "ORDER BY p.retail_price - p.wholesale_price DESC\n";
            Query query = session.createQuery(qryString);
            result = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.get(0);
    }

    public List<String> getTop3MostRecent(Integer user_id) {
        Session session = null;
        List<String> result = null;
        try {
            session = sessionFactory.getCurrentSession();

            String qryString = "SELECT p.name\n" +
                    "FROM Orders o\n" +
                    "JOIN o.order_products op \n" +
                    "JOIN op.product_id p \n" +
                    "WHERE o.order_status != 'Canceled' AND o.user_id = " + user_id + "\n" +
                    "ORDER BY o.date_placed\n DESC, p.product_id ASC";
            Query query = session.createQuery(qryString);
            query.setMaxResults(3);
            result = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<String> getTop3MostFreq(Integer user_id) {
        Session session = null;
        List<String> result = null;
        try {
            session = sessionFactory.getCurrentSession();

            String qryString = "SELECT p.name\n" +
                    "FROM Orders o\n" +
                    "JOIN o.order_products op \n" +
                    "JOIN op.product_id p \n" +
                    "WHERE o.order_status != 'Canceled' AND o.user_id = " + user_id + "\n" +
                    "GROUP BY p.name \n" +
                    "ORDER BY count(*) DESC, p.product_id ASC";
            Query query = session.createQuery(qryString);
            query.setMaxResults(3);
            result = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



}
