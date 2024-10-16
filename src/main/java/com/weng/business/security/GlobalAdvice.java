package com.weng.business.security;

import com.weng.exception.Catch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

@RestControllerAdvice
@Slf4j
public class GlobalAdvice {

    @ExceptionHandler(Catch.class)
    public ResponseEntity<Catch> handleCatch(Catch ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Catch> handleException(Exception e) {
        log.error("Generic Exception Error: {}", getStackTrace(e));
        return handleCatch(Catch.internalServerError());
    }
}
