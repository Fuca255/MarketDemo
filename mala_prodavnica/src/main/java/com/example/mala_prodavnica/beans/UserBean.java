package com.example.mala_prodavnica.beans;

import java.io.Serializable;

public class UserBean implements Serializable {
    private int id;
    private String username;
    private int money;
    private boolean isAdmin;

    public UserBean(int id, String username, int money, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.money = money;
        this.isAdmin = isAdmin;
    }

    public UserBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
