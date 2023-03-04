package com.example.transactionmanagementdemo.dao;

import com.example.transactionmanagementdemo.domain.entity.Author;
import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.exception.AuthorSaveFailedException;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    public Optional<User> loadUserByUsername(String username){
        Session session = null;
        List<User> users = null;
        Optional<User> ans = null;
        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            Predicate predicate = cb.equal(root.get("username"), username);
            cq.select(root).where(predicate);
            users = session.createQuery(cq).getResultList();
            if (users.size() > 0) {
                ans = Optional.of(users.get(0));
            } else {
                ans = Optional.empty();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }
}
