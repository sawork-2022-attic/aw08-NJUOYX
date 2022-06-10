package com.micropos.posorder.mapper;

import com.micropos.posorder.dto.OrderDto;
import com.micropos.posorder.model.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    Order toOrder(OrderDto orderDto);
    OrderDto toOrderDto(Order order);
    List<Order> toOrderList(List<OrderDto> orderDtoList);
    List<OrderDto> toOrderDtoList(List<Order> orderList);
}
