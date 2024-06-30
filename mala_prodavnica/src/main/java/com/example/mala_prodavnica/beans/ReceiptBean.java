package com.example.mala_prodavnica.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ReceiptBean implements Serializable {
    private List<ReceiptItemBean> items;
    private Date purchaseDate;
    private int id;
    private int total;

    public List<ReceiptItemBean> getItems() {
        return items;
    }

    public void setItems(List<ReceiptItemBean> items) {
        this.items = items;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
