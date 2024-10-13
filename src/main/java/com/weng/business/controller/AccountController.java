package com.weng.business.controller;

import com.weng.business.mapper.FundAccountMapper;
import com.weng.business.service.FundAccountService;
import com.weng.dto.account.response.FundAccountRes;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/accounts/{userId}")
@Validated
@Slf4j
public class AccountController {

    @Autowired
    private FundAccountService fundAccountService;
    @Autowired
    private FundAccountMapper fundAccountMapper;

    @Operation(summary = "Check Balance", description = "This API is used to retrieve user's fund account detail (Check Balance)")
    @GetMapping("")
    public Mono<ResponseEntity<FundAccountRes>> checkBalance(@PathVariable Long userId) {
        return fundAccountService.getBalance(userId)
                .map(fundAccountMapper::toFundAccountRes);
    }
}
