package com.weng.business.ws.account;

import com.weng.business.config.WebClientConfig;
import com.weng.business.ws.GenericServiceClient;
import com.weng.dto.account.request.CreateAccountReq;
import com.weng.dto.account.response.FundAccountRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class FundAccountClient {
    private final String baseUrl;
    private final GenericServiceClient serviceClient;

    @Autowired
    public FundAccountClient(WebClientConfig webClientConfig, GenericServiceClient serviceClient) {
        this.baseUrl = webClientConfig.getAccountServiceBaseUrl();
        this.serviceClient = serviceClient;
    }

    public Mono<ResponseEntity<FundAccountRes>> createFundAccount(String userId, CreateAccountReq req) {
        return serviceClient.post(baseUrl, "/accounts/{userId}", req, FundAccountRes.class, userId);
    }

    public Mono<ResponseEntity<FundAccountRes>> getFundAccount(String userId) {
        return serviceClient.get(baseUrl, "/accounts/{userId}", FundAccountRes.class, null, userId);
    }
}
