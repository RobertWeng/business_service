package com.weng.business.controller;

import com.weng.business.util.HashId;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/test/{id}")
public class TestController {
    @Operation(summary = "Get Hash Id", description = "This API is used to get encoded Id")
    @GetMapping("/encode")
    public ResponseEntity<String> encode(@PathVariable("id") Long id) {
        return ResponseEntity.ok(HashId.encode(id));
    }

    @Operation(summary = "Get Id", description = "This API is used to get decoded HashId")
    @GetMapping("/decode")
    public ResponseEntity<Long> decode(@PathVariable("id") HashId hashId) {
        return ResponseEntity.ok(hashId.getValue());
    }
}
