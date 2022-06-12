package com.integration.delivery.service;

import com.alibaba.fastjson.JSONObject;

public interface DeliveryService {
    JSONObject checkStatus(String uid);
}
