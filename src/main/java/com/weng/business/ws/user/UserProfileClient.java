package com.weng.business.ws.user;

import com.weng.business.config.WebClientConfig;
import com.weng.business.ws.GenericServiceClient;
import com.weng.dto.user.request.UpdateUserReq;
import com.weng.dto.user.response.UserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class UserProfileClient {
    private final String baseUrl;
    private final GenericServiceClient serviceClient;

    @Autowired
    public UserProfileClient(WebClientConfig webClientConfig, GenericServiceClient serviceClient) {
        this.baseUrl = webClientConfig.getUserServiceBaseUrl();
        this.serviceClient = serviceClient;
    }

    public Mono<ResponseEntity<UserRes>> getProfile(String userId) {
        return serviceClient.get(baseUrl, "/users/{userId}", UserRes.class, null, userId);
    }

    public Mono<ResponseEntity<UserRes>> updateProfile(String userId, UpdateUserReq req) {
        return serviceClient.put(baseUrl, "/users/{userId}", req, UserRes.class, userId);
    }

    public Mono<ResponseEntity<Void>> deleteProfile(String userId) {
        return serviceClient.delete(baseUrl, "/users/{userId}", userId);
    }

    public Mono<ResponseEntity<UserRes>> findByMobileNo(String mobileNo) {
        Map<String, String> params = Map.of("mobileNo", mobileNo);
        return serviceClient.get(baseUrl, "/public/users", UserRes.class, params);
    }
}
