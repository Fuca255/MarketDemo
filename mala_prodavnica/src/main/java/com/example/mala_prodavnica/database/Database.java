package com.example.mala_prodavnica.database;



import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Database {
    private static final String PERSISTENCE_UNIT_NAME = "mala_prodavnica";
    private static EntityManagerFactory factory;

    static {
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            // Ensuring that all tables are created
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            em.createNativeQuery("SELECT 1").getSingleResult(); // Execute a simple query to ensure initialization
            em.getTransaction().commit();
            closeEntityManager(em);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    public static void closeEntityManager(EntityManager em) {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    public static void closeEntityManagerFactory() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
}