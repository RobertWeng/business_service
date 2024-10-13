package com.weng.business.ws.account;

import com.weng.business.config.WebClientConfig;
import com.weng.business.ws.GenericServiceClient;
import com.weng.dto.account.request.TransactionBaseReq;
import com.weng.dto.account.request.TransferReq;
import com.weng.dto.account.response.TransactionRes;
import com.weng.dto.account.response.TransferRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AccountTransactionClient {
    private final String baseUrl;
    private final GenericServiceClient serviceClient;

    @Autowired
    public AccountTransactionClient(WebClientConfig webClientConfig, GenericServiceClient serviceClient) {
        this.baseUrl = webClientConfig.getAccountServiceBaseUrl();
        this.serviceClient = serviceClient;
    }

    public Mono<ResponseEntity<TransactionRes>> deposit(String userId, TransactionBaseReq req) {
        return serviceClient.post(baseUrl, "/transactions/{userId}/deposit", req, TransactionRes.class, userId);
    }

    public Mono<ResponseEntity<TransactionRes>> withdraw(String userId, TransactionBaseReq req) {
        return serviceClient.post(baseUrl, "/transactions/{userId}/withdraw", req, TransactionRes.class, userId);
    }

    public Mono<ResponseEntity<TransferRes>> transfer(String fromUserId, TransferReq req) {
        return serviceClient.post(baseUrl, "/transactions/{userId}/transfer", req, TransferRes.class, fromUserId);
    }
}
