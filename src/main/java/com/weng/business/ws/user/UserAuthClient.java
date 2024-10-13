package com.weng.business.ws.user;

import com.weng.business.config.WebClientConfig;
import com.weng.business.ws.GenericServiceClient;
import com.weng.dto.user.request.CreateUserReq;
import com.weng.dto.user.request.LoginReq;
import com.weng.dto.user.response.LoginRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserAuthClient {
    private final String baseUrl;
    private final GenericServiceClient serviceClient;

    @Autowired
    public UserAuthClient(WebClientConfig webClientConfig, GenericServiceClient serviceClient) {
        this.baseUrl = webClientConfig.getUserServiceBaseUrl();
        this.serviceClient = serviceClient;
    }

    public Mono<ResponseEntity<LoginRes>> register(CreateUserReq req) {
        return serviceClient.post(baseUrl, "/public/register", req, LoginRes.class);
    }

    public Mono<ResponseEntity<LoginRes>> login(LoginReq req) {
        return serviceClient.post(baseUrl, "/public/login", req, LoginRes.class);
    }
}
