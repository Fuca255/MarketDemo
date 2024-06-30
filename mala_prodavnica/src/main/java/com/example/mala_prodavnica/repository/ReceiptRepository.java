package com.example.mala_prodavnica.repository;

import com.example.mala_prodavnica.database.Database;
import com.example.mala_prodavnica.interfaces.IReceiptRepository;
import com.example.mala_prodavnica.model.Product;
import com.example.mala_prodavnica.model.Receipt;
import com.example.mala_prodavnica.model.ReceiptItem;
import com.example.mala_prodavnica.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ReceiptRepository implements IReceiptRepository {
    private EntityManager entityManager = Database.getEntityManager();
    @Override
    public List<Receipt> getAllUserReceipts(int user_id) {
        return entityManager.createQuery(
                "SELECT r FROM Receipt r WHERE r.user.id = :userId",
                Receipt.class)
                .setParameter("userId",user_id)
                .getResultList();
    }

    @Override
    public Receipt createReceipt(User user, int total, Date purchaseDate, List<ReceiptItem> items) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Receipt receipt = new Receipt();
            receipt.setUser(user);
            receipt.setTotal(total);

            receipt.setPurchaseDate(new Date());
            entityManager.persist(receipt);
            transaction.commit();

            for (ReceiptItem item : items) {
                item.setReceipt(receipt);
            }

            transaction.begin();
            receipt.setReceiptItems(items);
            entityManager.merge(receipt);
            transaction.commit();

            return receipt;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Receipt> getReceiptById(int id) {
        Receipt r = entityManager.find(Receipt.class,id);
        return Optional.ofNullable(r);
    }

    @Override
    public void deleteReceipt(Receipt rec) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            entityManager.remove(rec);

            transaction.commit();

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
