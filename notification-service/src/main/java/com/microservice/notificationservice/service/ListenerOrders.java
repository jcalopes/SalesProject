package com.microservice.notificationservice.service;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@EnableKafka
public class ListenerOrders {

    @KafkaListener(topics = "order", groupId = "order")
    public void listenGroupFoo(String message) {
        System.out.println("Received Message in group order: " + message);
    }
}
