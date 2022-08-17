package com.microservice.orderservice.config;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;


@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced //Client Side Load Balancer
    public WebClient.Builder webClientBuilder(){
        var httpClient = HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient));
    }
}
