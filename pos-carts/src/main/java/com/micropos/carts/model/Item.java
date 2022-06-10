package com.micropos.carts.model;

import com.micropos.products.model.Product;

import java.io.Serializable;

public class Item implements Serializable {
    private Product product;
    private int quantity;

    public Item(){}
    public Item(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
