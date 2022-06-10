package com.micropos.carts.repository;

import com.micropos.carts.model.Item;

import java.util.List;

public interface CartRepository {
    List<Item> getItems();
    boolean addItem(Item item);
    double getTotal();
}
