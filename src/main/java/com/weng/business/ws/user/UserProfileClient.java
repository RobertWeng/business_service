package com.weng.business.ws.user;

import com.weng.business.config.WebClientConfig;
import com.weng.business.ws.GenericServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserProfileClient {
    @Autowired
    private WebClientConfig webClientConfig;
    @Autowired
    private GenericServiceClient serviceClient;

    public ResponseEntity<String> getProfile(String userId) {
        String baseUrl = webClientConfig.getUserServiceBaseUrl();
        Mono<ResponseEntity<String>> mono = serviceClient.get(baseUrl, "/users/{userId}", String.class, userId);
        ResponseEntity<String> responseEntity = mono.block();
        return responseEntity;
    }
}
