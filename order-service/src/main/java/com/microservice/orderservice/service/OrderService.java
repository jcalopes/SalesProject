package com.microservice.orderservice.service;

import com.microservice.orderservice.dto.*;
import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.model.OrderLineItems;
import com.microservice.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderNumber(order.getOrderNumber());

        List<OrderLineItems> listItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToOrderLineItems)
                .collect(Collectors.toList());
        order.setOrderLineItemsList(listItems);

        List<String> skuCodes = order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .collect(Collectors.toList());

        //Call inventory service and place order if product is in stock
        String uri = "http://INVENTORY-SERVICE/api/inventory";
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("skuCode", skuCodes).build();

        ResponseEntity<InventoryResponse[]> inventoryResponse = restTemplate
                .getForEntity(builder.toUriString(), InventoryResponse[].class);

        boolean allProductsInStock = Arrays.stream(inventoryResponse.getBody())
                .allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);
            sendKafkaMessage("order", "Order Placed " + order.getId() + " successfully.");
            return "Order ID:" + order.getOrderNumber() + " placed successfully.";
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later!");
        }
    }

    public List<OrderDto> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::mapToOrderDto).collect(Collectors.toList());
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
        orderDto.setOrderLineItemsList(order.getOrderLineItemsList().stream().map(this::mapToOrderLineItemsDto)
                .collect(Collectors.toList()));
        return orderDto;
    }

    private void sendKafkaMessage(String topic, String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message=[{}] due to : {}", message, ex.getMessage());
            }
        });
    }
}
