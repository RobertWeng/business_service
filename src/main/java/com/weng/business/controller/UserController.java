package com.weng.business.controller;

import com.weng.business.mapper.UserMapper;
import com.weng.business.service.UserService;
import com.weng.business.util.HashId;
import com.weng.dto.user.request.UpdateUserReq;
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
@RequestMapping("/v1/users/{userId}")
@Validated
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Operation(summary = "Get Profile", description = "This API is used to retrieve user information")
    @GetMapping("")
    public Mono<ResponseEntity<UserRes>> getProfile(@PathVariable HashId userId) {
        return userService.getProfile(userId.getValue())
                .map(userMapper::toUserRes);
    }

    @Operation(summary = "Update Profile", description = "This API is used to update user account")
    @PutMapping("")
    public Mono<ResponseEntity<UserRes>> updateProfile(@PathVariable HashId userId, @Valid @RequestBody UpdateUserReq req) {
        return userService.updateProfile(userId.getValue(), req)
                .map(userMapper::toUserRes);
    }

    @Operation(summary = "Delete Profile", description = "This API is used to delete user account")
    @DeleteMapping("")
    public Mono<ResponseEntity<Void>> deleteProfile(@PathVariable HashId userId) {
        return userService.deleteProfile(userId.getValue());
    }
}
