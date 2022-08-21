package com.microservice.orderservice.controller;

import com.microservice.orderservice.dto.OrderDto;
import com.microservice.orderservice.dto.OrderRequest;
import com.microservice.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        log.info("POST: Called Order Service.");
        return orderService.placeOrder(orderRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getAllOrderss(){
        log.info("GET: Retrieved all orders.");
        return orderService.getOrders();
    }

    public String fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
        return "Something went error placing your order. Try again later.";
    }
}
