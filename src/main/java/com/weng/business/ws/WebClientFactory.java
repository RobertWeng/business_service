package com.weng.business.ws;

import org.springframework.web.reactive.function.client.WebClient;

public interface WebClientFactory {
    WebClient.Builder createWebClientBuilder();

    default WebClient createWebClient(String baseUrl) {
        return createWebClientBuilder()
                .baseUrl(baseUrl)
                .build();
    }
}
