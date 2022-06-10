package com.micropos.posorder.rest;

import com.micropos.posorder.api.OrderApi;
import com.micropos.posorder.dto.OrderDto;
import com.micropos.posorder.mapper.OrderMapper;
import com.micropos.posorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class OrderController implements OrderApi {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public ResponseEntity<Boolean> sendOrder(OrderDto orderDto) {
        return new ResponseEntity<>(orderService.sendOrder(orderMapper.toOrder(orderDto)), HttpStatus.OK);
    }
}
