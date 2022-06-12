package com.micropos.posorder.service;

import com.micropos.posorder.dto.OrderDto;
import com.micropos.posorder.mapper.OrderMapper;
import com.micropos.posorder.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Supplier;

@Service
public class OrderServiceImpl implements OrderService{

    private final Queue<OrderDto> orderQueue = new LinkedList<>();
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Boolean sendOrder(Order order) {
        return orderQueue.add(orderMapper.toOrderDto(order));
    }

    @Bean
    public Supplier<OrderDto> supplyOrder(){
        return () -> {
            OrderDto message = orderQueue.poll();
            System.out.println(System.nanoTime() + ": supplier: " + ((message == null)?"null":message.getUser().getUid()));
            return message;
        };
    }
}
