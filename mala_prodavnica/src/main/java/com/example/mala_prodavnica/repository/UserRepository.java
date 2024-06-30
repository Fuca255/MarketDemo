package com.example.mala_prodavnica.repository;

import com.example.mala_prodavnica.database.Database;
import com.example.mala_prodavnica.interfaces.IUserRepository;
import com.example.mala_prodavnica.model.User;
import jakarta.persistence.EntityManager;

import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

public class UserRepository implements IUserRepository {

    private EntityManager entityManager = Database.getEntityManager();

    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }
    @Override
    public Optional<User> login(String username, String password) {
        entityManager.getEntityManagerFactory().getCache().evictAll();
        List<User> users = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password=:password", User.class)
                .setParameter("username", username)
                .setParameter("password",password)
                .getResultList();
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User update(User user) {
        EntityTransaction trs= entityManager.getTransaction();
        trs.begin();
        User u = entityManager.merge(user);
        trs.commit();
        return u;
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        List<User> users = entityManager.createQuery("select u from User u where u.username=:username",User.class)
                .setParameter("username",username).getResultList();
        if(users.isEmpty())
            return Optional.empty();
        return Optional.of(users.get(0));
    }

    @Override
    public void register(User u) {
        EntityTransaction trs= entityManager.getTransaction();
        trs.begin();
        entityManager.persist(u);
        trs.commit();
    }
}
