package com.example.mala_prodavnica.repository;

import com.example.mala_prodavnica.beans.UpdateProductBean;
import com.example.mala_prodavnica.database.Database;
import com.example.mala_prodavnica.interfaces.IProductRepository;
import com.example.mala_prodavnica.model.Product;
import com.example.mala_prodavnica.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class ProductRepository implements IProductRepository {
    private EntityManager entityManager;
    public ProductRepository()
    {
        entityManager = Database.getEntityManager();
    }

    @Override
    public Optional<Product> getProductById(int id) {
        Product p = entityManager.find(Product.class,id);
        return Optional.ofNullable(p);
    }

    @Override
    public List<Product> getAllProducts() {
        return entityManager.createQuery("select p from Product p",Product.class).getResultList();
    }

    @Override
    public Product updateProduct(UpdateProductBean pr) {
        Optional<Product> pg = getProductById(pr.getProduct_id());
        if(pg.isPresent()) {
            Product p = pg.get();
            p.setPrice(pr.getPrice());
            p.setName(pr.getName());

            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(p);
            transaction.commit();

            return p;
        }
        return null;
    }

    @Override
    public void deleteProduct(Product p) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(p);
        transaction.commit();

    }

    @Override
    public Product addNewProduct(Product p) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(p);
        transaction.commit();
        return p;
    }
}
