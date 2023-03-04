package com.example.transactionmanagementdemo.dao;

import com.example.transactionmanagementdemo.domain.entity.Orders;
import com.example.transactionmanagementdemo.domain.entity.Product;
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

@Repository
public class ProductDao {

    @Autowired SessionFactory sessionFactory;

    public Product createNewProduct(String name, String description, float retail_price, float wholesale_price, int stock_quantity) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setRetail_price(retail_price);
        product.setWholesale_price(wholesale_price);
        product.setStock_quantity(stock_quantity);

        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.save(product);
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
        return product;
    }

    public List<Product> getAllProduct(Integer product_id) {
        List<Product> products = null;
        Session session = null;

        try {
            session = sessionFactory.getCurrentSession();
            String query = "FROM Product WHERE product_id = " + product_id;
            Query query1 = session.createQuery(query);
            products = query1.getResultList();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
        return products;
    }

    public List<Product> getAllProductUser() {
        List<Product> products = null;
        Session session = null;

        try {
            session = sessionFactory.getCurrentSession();
            String query = "SELECT product_id, name FROM Product WHERE stock_quantity > 0";
            Query query1 = session.createQuery(query);
            products = query1.getResultList();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
        return products;
    }

    public List<Product> getProductUserDetail(Integer product_id) {
        List<Product> products = null;
        Session session = null;

        try {
            session = sessionFactory.getCurrentSession();
            String qryString = "select name, description, retail_price from Product where id = :Id";
            Query query = session.createQuery(qryString);
            query.setParameter("Id", product_id);
            products = query.getResultList();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
        return products;
    }

    public Product getProductById(Integer product_id) throws Exception {
        List<Product> product = null;
        Session session = null;

        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            Root<Product> root = cq.from(Product.class);
            Predicate predicate = cb.equal(root.get("product_id"), product_id);
            cq.select(root).where(predicate);
            product = session.createQuery(cq).getResultList();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if (product!= null && product.size() != 1) {
            throw new Exception("Something Wrong");
        }

        return product.get(0);
    }


    public Product getOrderProductById(Integer product_id) throws Exception {
        List<Product> product = null;
        Session session = null;

        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            Root<Product> root = cq.from(Product.class);
            Predicate predicate = cb.equal(root.get("product_id"), product_id);
            cq.select(root).where(predicate);
            product = session.createQuery(cq).getResultList();
            for (Product product1: product) {
                product1.getUsers().isEmpty();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if (product!= null && product.size() != 1) {
            throw new Exception("Something Wrong");
        }

        return product.get(0);
    }

    public void updateStock(Integer product_id, Integer stock) {
        Session session = null;

        try {
            session = sessionFactory.getCurrentSession();
            String qryString = "UPDATE Product p SET p.stock_quantity = :Stock WHERE p.product_id = :Id";
            Query query = session.createQuery(qryString);
            query.setParameter("Stock", stock);
            query.setParameter("Id", product_id);
            query.executeUpdate();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
    }

    public void updateWholesale_price(Integer product_id, float wholesale_price) {
        Session session = null;

        try {
            session = sessionFactory.getCurrentSession();
            Product product = session.get(Product.class, product_id);
            product.setWholesale_price(wholesale_price);
            session.update(product);
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
    }

    public void updateRetail_price(Integer product_id, float retail_price) {
        Session session = null;

        try {
            session = sessionFactory.getCurrentSession();
            Product product = session.get(Product.class, product_id);
            product.setRetail_price(retail_price);
            session.update(product);
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
    }

    public void updateDescription(Integer product_id, String description) {
        Session session = null;

        try {
            session = sessionFactory.getCurrentSession();
            Product product = session.get(Product.class, product_id);
            product.setDescription(description);
            session.update(product);
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
    }
}
