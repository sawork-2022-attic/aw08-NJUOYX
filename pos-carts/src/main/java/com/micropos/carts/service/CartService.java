package com.micropos.carts.service;

import com.micropos.carts.model.Item;

import java.util.List;

public interface CartService {
    List<Item> getItems();
    Boolean addItem(String productId);
    Double getTotal();
}
