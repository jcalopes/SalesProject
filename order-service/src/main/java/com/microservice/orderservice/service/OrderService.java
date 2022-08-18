package com.microservice.orderservice.service;

import com.microservice.orderservice.dto.*;
import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.model.OrderLineItems;
import com.microservice.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderNumber(order.getOrderNumber());

        List<OrderLineItems> listItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToOrderLineItems)
                .toList();
        order.setOrderLineItemsList(listItems);

        List<String> skuCodes = order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        //Call inventory service and place order if product is in stock
        String uri = "http://localhost:8082/api/inventory";
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("skuCode", skuCodes).build();

        ResponseEntity<InventoryResponse[]> inventoryResponse = restTemplate
                .getForEntity(builder.toUriString(), InventoryResponse[].class);

        boolean allProductsInStock = Arrays.stream(inventoryResponse.getBody())
                .allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later!");
        }
    }

    public List<OrderDto> getOrders() {
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
