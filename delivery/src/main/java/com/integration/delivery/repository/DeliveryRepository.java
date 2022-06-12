package com.integration.delivery.repository;

import com.alibaba.fastjson.JSONObject;

public interface DeliveryRepository {
    JSONObject query(String uid);
    void insert(String jsonString);
}
