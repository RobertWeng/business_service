package com.weng.business.service;

import com.weng.business.ws.account.AccountTransactionClient;
import com.weng.dto.account.request.TransactionBaseReq;
import com.weng.dto.account.request.TransferReq;
import com.weng.dto.account.response.TransactionRes;
import com.weng.dto.account.response.TransferRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private AccountTransactionClient transactionClient;

    public Mono<ResponseEntity<TransactionRes>> deposit(Long userId, TransactionBaseReq req) {
        return transactionClient.deposit(String.valueOf(userId), req);
    }

    public Mono<ResponseEntity<TransactionRes>> withdraw(Long userId, TransactionBaseReq req) {
        return transactionClient.withdraw(String.valueOf(userId), req);
    }

    public Mono<ResponseEntity<TransferRes>> transfer(Long fromUserId, TransferReq req) {
        return transactionClient.transfer(String.valueOf(fromUserId), req);
    }
}
