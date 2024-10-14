package com.weng.business.ws.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weng.business.config.WebClientConfig;
import com.weng.business.ws.GenericServiceClient;
import com.weng.business.ws.ParamsBuilder;
import com.weng.dto.ResultList;
import com.weng.dto.account.request.PageableReq;
import com.weng.dto.account.request.TransactionBaseReq;
import com.weng.dto.account.request.TransferReq;
import com.weng.dto.account.response.TransactionRes;
import com.weng.dto.account.response.TransferRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class TransactionClient {
    private final String baseUrl;
    private final GenericServiceClient serviceClient;
    private final ObjectMapper om;

    @Autowired
    public TransactionClient(WebClientConfig webClientConfig, GenericServiceClient serviceClient, ObjectMapper om) {
        this.baseUrl = webClientConfig.getAccountServiceBaseUrl();
        this.serviceClient = serviceClient;
        this.om = om;
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

    public Mono<ResponseEntity<ResultList<TransactionRes>>> findAll(String fromUserId, PageableReq req) {
        Map<String, String> queryParams = new ParamsBuilder()
                .addParam("page", req.getPage())
                .addParam("size", req.getSize())
                .build();

        return serviceClient.get(baseUrl, "/transactions/{userId}", String.class, queryParams, fromUserId).flatMap(responseEntity -> {
            String responseBody = responseEntity.getBody();
            try {
                // Deserialize
                ResultList<TransactionRes> resultList = om.readValue(responseBody, new TypeReference<>() {
                });

                return Mono.just(ResponseEntity.status(responseEntity.getStatusCode()).body(resultList));
            } catch (JsonProcessingException e) {
                return Mono.error(new RuntimeException("Failed to parse response"));
            }
        });
    }

    public Mono<ResponseEntity<TransactionRes>> findById(String transactionId) {
        String userId = "placeholder";
        return serviceClient.get(baseUrl, "/transactions/{userId}/{transactionId}", TransactionRes.class, null, userId, transactionId);
    }
}
