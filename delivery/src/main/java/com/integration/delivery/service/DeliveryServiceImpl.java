package com.integration.delivery.service;

import com.integration.delivery.repository.DeliveryRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class DeliveryServiceImpl implements DeliveryService{

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Override
    public JSONObject checkStatus(String uid) {
        return deliveryRepository.query(uid);
    }

    @Bean
    public Consumer<JSONObject> getMessage(){return jsonObject -> deliveryRepository.insert(jsonObject);}
}
