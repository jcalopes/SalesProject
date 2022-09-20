package com.microservice.orderservice.queue;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class QueueConfig {

    /*
        Creating exchange type, queues and binding into declarables.
     */
    @Bean
    public Declarables fanoutBindings(){
        Queue fanoutQueue1 = new Queue("fanout.queue1",false);
        Queue fanoutQueue2 = new Queue("fanout.queue2",false);
        FanoutExchange fanoutExchange = new FanoutExchange("fanout.exchange");

        return new Declarables(
            fanoutQueue1,
            fanoutQueue2,
            fanoutExchange,
            BindingBuilder.bind(fanoutQueue1).to(fanoutExchange),
            BindingBuilder.bind(fanoutQueue2).to(fanoutExchange));
    }

    @Bean
    public Declarables topicBindings(){
        Queue topicQueue1 = new Queue("topicQueue1Name", false);
        Queue topicQueue2 = new Queue("topicQueue2Name", false);
        TopicExchange topicExchange = new TopicExchange("topicExchangeName");

        return new Declarables(
                topicQueue1,
                topicQueue2,
                topicExchange,
                BindingBuilder
                        .bind(topicQueue1)
                        .to(topicExchange).with("*.important.*"),
                BindingBuilder
                        .bind(topicQueue2)
                        .to(topicExchange).with("#.error"));
    }
}
