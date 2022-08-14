package com.microservice.orderservice.service;

import com.microservice.orderservice.dto.OrderDto;
import com.microservice.orderservice.dto.OrderLineItemsDto;
import com.microservice.orderservice.dto.OrderRequest;
import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.model.OrderLineItems;
import com.microservice.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderNumber(order.getOrderNumber());
        List<OrderLineItems> listItems = orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToOrderLineItems).toList();
        order.setOrderLineItemsList(listItems);

        orderRepository.save(order);
    }

    public List<OrderDto> getOrders(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::mapToOrderDto).toList();
    }

    private OrderLineItems mapToOrderLineItems(OrderLineItemsDto orderItemDto) {
        OrderLineItems orderLineItem = new OrderLineItems();
        orderLineItem.setId(orderItemDto.getId());
        orderLineItem.setPrice(orderItemDto.getPrice());
        orderLineItem.setSkuCode(orderItemDto.getSkuCode());
        orderLineItem.setQuantity(orderItemDto.getQuantity());
        return orderLineItem;
    }

    private OrderLineItemsDto mapToOrderLineItemsDto(OrderLineItems orderItem) {
        OrderLineItemsDto orderLineItem = new OrderLineItemsDto();
        orderLineItem.setId(orderItem.getId());
        orderLineItem.setPrice(orderItem.getPrice());
        orderLineItem.setSkuCode(orderItem.getSkuCode());
        orderLineItem.setQuantity(orderItem.getQuantity());
        return orderLineItem;
    }

    private OrderDto mapToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderNumber(order.getOrderNumber());
        orderDto.setOrderLineItemsList(order.getOrderLineItemsList().stream().map(this::mapToOrderLineItemsDto).toList());
        return orderDto;
    }
}
