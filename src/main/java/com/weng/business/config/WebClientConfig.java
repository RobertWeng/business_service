package com.weng.business.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Data
@Configuration
@Slf4j
public class WebClientConfig {
    @Value("${app.eureka.user-service.url}")
    private String userServiceBaseUrl;

    @Value("${app.eureka.account-service.url}")
    private String accountServiceBaseUrl;

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .filter(logRequest()) // Common logging filter
                .filter(logResponse()) // Common response logging
                .defaultHeader("Content-Type", "application/json");
    }

    // Log request details
    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("CLIENT-REQ: {} {}", clientRequest.method(), clientRequest.url());
            return Mono.just(clientRequest);
        });
    }

    // Log response details
    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("CLIENT-RES: {}", clientResponse.statusCode());
            return Mono.just(clientResponse);
        });
    }
}
