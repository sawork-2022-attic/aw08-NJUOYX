package com.micropos.carts.repository;

import com.micropos.carts.model.Item;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MapRepository implements CartRepository{
    private Map<String, Item> repository = new HashMap<>();

    @Override
    public List<Item> getItems() {
        return List.of(repository.values().toArray(new Item[0]));
    }

    @Override
    public boolean addItem(Item item) {
        String id = item.getProduct().getId();
        Item get = repository.get(id);
        if(get == null){
            repository.put(id, item);
        }else{
            get.setQuantity(get.getQuantity()+1);
            repository.put(id, get);
        }
        return true;
    }

    @Override
    public double getTotal() {
        double total = 0.0;
        for(Item item : repository.values()){
            total += item.getQuantity() * item.getProduct().getPrice();
        }
        return total;
    }
}
