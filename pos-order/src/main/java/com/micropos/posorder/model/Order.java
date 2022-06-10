package com.micropos.posorder.model;

import com.micropos.poscounter.model.User;

import java.io.Serializable;
import java.util.List;

public class Order<T> implements Serializable {
    private User user;
    private List<T> cart;

    public Order(User user, List<T> cart) {
        this.user = user;
        this.cart = cart;
    }

    public Order() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<T> getCart() {
        return cart;
    }

    public void setCart(List<T> cart) {
        this.cart = cart;
    }
}
