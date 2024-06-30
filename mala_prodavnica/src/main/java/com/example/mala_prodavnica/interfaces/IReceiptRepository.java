package com.example.mala_prodavnica.interfaces;

import com.example.mala_prodavnica.model.Receipt;
import com.example.mala_prodavnica.model.ReceiptItem;
import com.example.mala_prodavnica.model.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IReceiptRepository {


    List<Receipt> getAllUserReceipts(int user_id);
    Receipt createReceipt(User user, int total, Date purchaseDate, List<ReceiptItem> items);

    Optional<Receipt> getReceiptById(int id);
    void deleteReceipt(Receipt rec);

}
