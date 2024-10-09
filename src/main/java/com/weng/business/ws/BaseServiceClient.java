package com.weng.business.ws;

import org.springframework.web.reactive.function.client.WebClient;

public interface BaseServiceClient {
    WebClient.Builder getWebClientBuilder();

    default WebClient getWebClient(String baseUrl) {
        return getWebClientBuilder()
                .baseUrl(baseUrl)
                .build();
    }
}
