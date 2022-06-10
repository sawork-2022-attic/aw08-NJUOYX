package com.integration.delivery.service;

import org.json.JSONObject;

public interface DeliveryService {
    JSONObject checkStatus(String uid);
}
