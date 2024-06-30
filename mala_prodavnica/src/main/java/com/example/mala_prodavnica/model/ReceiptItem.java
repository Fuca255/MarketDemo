package com.example.mala_prodavnica.model;


import jakarta.persistence.*;

@Entity
@Table(name = "receiptitem")
public class ReceiptItem {
    @Id
    @ManyToOne
    @JoinColumn(name = "receipt_id", nullable = false)
    private Receipt receipt;
    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
