package com.example.transactionmanagementdemo.dao;

import com.example.transactionmanagementdemo.domain.entity.OrderProduct;
import com.example.transactionmanagementdemo.domain.entity.Orders;
import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserDao {
    @Autowired SessionFactory sessionFactory;

    public void createUser(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.set_seller(false);

        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.save(user);
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
    }

    public List<User> getUserByUserId(Integer user_id){
        Session session;
        List<User> users = null;
        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            Predicate predicate = cb.equal(root.get("user_id"), user_id);
            cq.select(root).where(predicate);
            users = session.createQuery(cq).getResultList();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    public List<User> getUserAndProductByUserId(Integer user_id){
        Session session;
        List<User> users = null;
        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            Predicate predicate = cb.equal(root.get("user_id"), user_id);
            cq.select(root).where(predicate);
            users = session.createQuery(cq).getResultList();
            for (User user:users) {
                user.getProducts().isEmpty();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    public List<User> getUserByUsername(String username){
        Session session;
        List<User> users = null;
        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            Predicate predicate = cb.equal(root.get("username"), username);
            cq.select(root).where(predicate);
            users = session.createQuery(cq).getResultList();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }


    public List<User> getUserByEmail(String email){
        Session session;
        List<User> users = null;
        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            Predicate predicate = cb.equal(root.get("email"), email);
            cq.select(root).where(predicate);
            users = session.createQuery(cq).getResultList();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    public void addToWatchList(User user, Product product) {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            user.getProducts().add(product);
            product.getUsers().add(user);
            session.save(user);
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
    }

    public void removeFromWatchList(User user, Product product) {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            user.getProducts().remove(product);
            product.getUsers().remove(user);
            session.update(user);
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
    }


    public Set<Product> viewWatchlist(Integer user_id) {
        Set<Product> products = null;
        Session session = null;
        List<Product> products1 = null;

        try {
            session = sessionFactory.getCurrentSession();
            String query = "SELECT u.username, p.name FROM User u LEFT JOIN u.products p WHERE p.stock_quantity > 0 AND u.user_id = " + user_id;
            Query query1 = session.createQuery(query);
            products1 = query1.getResultList();
            products = products1.stream().collect(Collectors.toSet());
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
        return products;
    }


}
