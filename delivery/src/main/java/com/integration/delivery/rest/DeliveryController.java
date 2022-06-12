package com.integration.delivery.rest;

import com.alibaba.fastjson.JSONObject;
import com.integration.delivery.service.DeliveryService;
import com.micropos.posdelivery.api.DeliveryApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class DeliveryController implements DeliveryApi {

    @Autowired
    private DeliveryService deliveryService;

    @Override
    public ResponseEntity<Object> checkDelivery(String uid) {
        JSONObject res = deliveryService.checkStatus(uid);
        if(res == null || res.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }
}
