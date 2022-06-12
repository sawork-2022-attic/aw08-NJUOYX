package com.integration.delivery.repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
    public void insert(String jsonString) {
        JSONObject json = JSONObject.parseObject(jsonString);
        if(json.get("user")==null||json.get("cart")==null){
            System.err.println("Illegal message");
            return;
        }
        String uid = json.getJSONObject("user").getString("uid");
        JSONArray cart = json.getJSONArray("cart");
        JSONObject get = repository.get(uid);
        if(get == null){
            repository.put(uid, json);
        }else{
            get.getJSONArray("cart").addAll(cart);
        }
    }
}
