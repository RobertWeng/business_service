package com.weng.business.controller;

import com.weng.business.mapper.UserMapper;
import com.weng.business.service.UserService;
import com.weng.dto.user.request.CreateUserReq;
import com.weng.dto.user.request.LoginReq;
import com.weng.dto.user.response.LoginRes;
import com.weng.dto.user.response.UserRes;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/public")
@Validated
@Slf4j
public class PublicController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Operation(summary = "Register User Account", description = "This API is used to register user account")
    @PostMapping("/register")
    public Mono<ResponseEntity<LoginRes>> register(@Valid @RequestBody CreateUserReq req) {
        return userService.register(req)
                .map(userMapper::toLoginRes);
    }

    @Operation(summary = "Login", description = "This API is used to login user account")
    @PostMapping("/login")
    public Mono<ResponseEntity<LoginRes>> login(@Valid @RequestBody LoginReq req) {
        return userService.login(req)
                .map(userMapper::toLoginRes);
    }

    @Operation(summary = "Find User By Mobile", description = "This API is used to retrieve user information based on mobile")
    @GetMapping("/users")
    public Mono<ResponseEntity<UserRes>> findByMobileNo(@RequestParam String mobileNo) {
        return userService.findByMobileNo(mobileNo)
                .map(userMapper::toUserRes);
    }
}
