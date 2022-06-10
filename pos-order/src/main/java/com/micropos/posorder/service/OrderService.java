package com.micropos.posorder.service;


import com.micropos.posorder.model.Order;

public interface OrderService {
    Boolean sendOrder(Order order);
}
