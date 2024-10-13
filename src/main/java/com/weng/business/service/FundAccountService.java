package com.weng.business.service;

import com.weng.business.ws.account.FundAccountClient;
import com.weng.dto.account.response.FundAccountRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class FundAccountService {

    @Autowired
    private FundAccountClient fundAccountClient;

    public Mono<ResponseEntity<FundAccountRes>> getBalance(Long userId) {
        return fundAccountClient.getFundAccount(String.valueOf(userId));
    }
}
