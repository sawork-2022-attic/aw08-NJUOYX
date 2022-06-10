package com.integration.delivery.repository;

import org.json.JSONObject;

public interface DeliveryRepository {
    JSONObject query(String uid);
    void insert(JSONObject json);
}
