package com.micropos.posorder.service;

import com.micropos.posorder.model.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Supplier;

@Service
public class OrderServiceImpl implements OrderService{

    private final Queue<Order> orderQueue = new LinkedList<>();

    @Override
    public Boolean sendOrder(Order order) {
        return orderQueue.add(order);
    }

    @Bean
    public Supplier<Order> supplyOrder(){
        return orderQueue::poll;
    }
}
