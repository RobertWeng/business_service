package com.weng.business.controller;

import com.weng.business.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/public/{userId}")
@Validated
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get Profile", description = "This API is used to retrieve user information")
    @GetMapping("")
    public ResponseEntity<Object> getProfile(@PathVariable Long userId) {
        userService.getProfile(userId);
        return ResponseEntity.ok().build();
    }
}
