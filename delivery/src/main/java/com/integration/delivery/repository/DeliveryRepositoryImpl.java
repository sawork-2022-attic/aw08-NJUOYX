package com.integration.delivery.repository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DeliveryRepositoryImpl implements DeliveryRepository{

    private final Map<String, JSONObject> repository = new HashMap<>();

    @Override
    public JSONObject query(String uid) {
        return repository.get(uid);
    }

    @Override
    public void insert(JSONObject json) {
        if(json.isNull("user")||json.isNull("cart")){
            return;
        }
        String uid = json.getString("user");
        JSONArray cart = json.getJSONArray("cart");
        JSONObject get = repository.get(uid);
        if(get == null){
            repository.put(uid, json);
        }else{
            get.getJSONArray("cart").put(cart.toList());
        }

    }
}
