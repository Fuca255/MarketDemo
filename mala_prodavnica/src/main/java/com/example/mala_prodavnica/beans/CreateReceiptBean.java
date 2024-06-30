package com.example.mala_prodavnica.beans;

import com.example.mala_prodavnica.model.ReceiptItem;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CreateReceiptBean implements Serializable {
    private int userId;
    private int total;
    private Date purchaseDate;
    private List<ReceiptItemBean> items;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<ReceiptItemBean> getItems() {
        return items;
    }

    public void setItems(List<ReceiptItemBean> items) {
        this.items = items;
    }
}
