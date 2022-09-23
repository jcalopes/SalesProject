package com.microservice.notificationservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {

    @RabbitListener(queues = {"fanout.queue1"})
    public void receiveMessageFromFanout1(String message) {
        log.info("Received fanout 1 message: " + message);
    }

    @RabbitListener(queues = {"fanout.queue2"})
    public void receiveMessageFromFanout2(String message) {
        log.info("Received fanout 2 message: " + message);
    }

    @RabbitListener(queues = {"topicQueue1Name"})
    public void receiveMessageFromTopic1(String message) {
        log.info("Received topic 1 () message: " + message);
    }

    @RabbitListener(queues = {"topicQueue1Name"})
    public void receiveMessageFromTopic2(String message) {
        log.info("Received topic 2 () message: " + message);
    }
}
