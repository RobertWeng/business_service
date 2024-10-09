package com.weng.business.ws.user;

import com.weng.business.config.WsConfig;
import com.weng.business.ws.BaseServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UserProfileClient implements BaseServiceClient {


    @Autowired
    private WsConfig wsConfig;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public WebClient.Builder getWebClientBuilder() {
        return webClientBuilder;
    }

    @Override
    public WebClient getWebClient(String baseUrl) {
        return BaseServiceClient.super.getWebClient(baseUrl);
    }

    public ResponseEntity<String> getProfile(String userId) {
        WebClient webClient = getWebClient(wsConfig.getUserServiceBaseUrl());

        ResponseEntity<String> responseEntity = webClient
                .get()
                .uri("/users/{userId}", userId)
                .retrieve()
                .toEntity(String.class)
                .block(); // Use non-blocking if required
        return responseEntity;
    }
}
