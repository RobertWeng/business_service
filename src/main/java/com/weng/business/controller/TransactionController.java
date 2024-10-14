package com.weng.business.controller;

import com.weng.business.mapper.TransactionMapper;
import com.weng.business.service.TransactionService;
import com.weng.business.util.HashId;
import com.weng.dto.ResultList;
import com.weng.dto.account.request.PageableReq;
import com.weng.dto.account.request.TransactionBaseReq;
import com.weng.dto.account.request.TransferReq;
import com.weng.dto.account.response.TransactionRes;
import com.weng.dto.account.response.TransferRes;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/transactions/{userId}")
@Validated
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionMapper transactionMapper;

    @Operation(summary = "Deposit Money", description = "This API is used to deposit money to user's fund account")
    @PostMapping("/deposit")
    public Mono<ResponseEntity<TransactionRes>> deposit(@PathVariable HashId userId, @RequestBody @Valid TransactionBaseReq req) {
        return transactionService.deposit(userId.getValue(), req)
                .map(transactionMapper::toTransactionRes);
    }

    @Operation(summary = "Withdraw Money", description = "This API is used to withdraw money from user's fund account")
    @PostMapping("/withdraw")
    public Mono<ResponseEntity<TransactionRes>> withdraw(@PathVariable HashId userId, @RequestBody @Valid TransactionBaseReq req) {
        return transactionService.withdraw(userId.getValue(), req)
                .map(transactionMapper::toTransactionRes);
    }

    @Operation(summary = "Transfer Money", description = "This API is used to transfer money")
    @PostMapping("/transfer")
    public Mono<ResponseEntity<TransferRes>> transfer(@PathVariable(name = "userId") HashId fromUserId, @RequestBody @Valid TransferReq req) {
        return transactionService.transfer(fromUserId.getValue(), req)
                .map(transactionMapper::toTransferRes);
    }

    @Operation(summary = "Get All User's Transaction", description = "This API is used to get all user's transaction")
    @GetMapping("/")
    public Mono<ResponseEntity<ResultList<TransactionRes>>> findAll(@PathVariable HashId userId, PageableReq req) {
        return transactionService.findAll(userId.getValue(), req)
                .map(transactionMapper::toTransactionResList);
    }

    @Operation(summary = "Get Transaction Detail", description = "This API is used to get transaction detail")
    @GetMapping("/{transactionId}")
    public Mono<ResponseEntity<TransactionRes>> findById(@PathVariable(name = "transactionId") HashId transactionId) {
        return transactionService.findById(transactionId.getValue())
                .map(transactionMapper::toTransactionRes);
    }
}
