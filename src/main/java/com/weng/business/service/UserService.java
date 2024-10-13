package com.weng.business.service;

import com.weng.business.ws.account.FundAccountClient;
import com.weng.business.ws.user.UserAuthClient;
import com.weng.business.ws.user.UserProfileClient;
import com.weng.dto.account.request.CreateAccountReq;
import com.weng.dto.user.request.CreateUserReq;
import com.weng.dto.user.request.LoginReq;
import com.weng.dto.user.request.UpdateUserReq;
import com.weng.dto.user.response.LoginRes;
import com.weng.dto.user.response.UserRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserProfileClient userProfileClient;

    @Autowired
    private UserAuthClient userAuthClient;

    @Autowired
    private FundAccountClient fundAccountClient;

    public Mono<ResponseEntity<LoginRes>> register(CreateUserReq req) {
        CreateAccountReq createAccountReq = new CreateAccountReq();
        createAccountReq.setCurrency("MYR");
        // Step 1: Register the user
        return userAuthClient.register(req)
                .flatMap(userResponse -> {
                    // Step 2: If user creation is successful, create the fund account
                    String userId = userResponse.getBody().getId(); // Assuming userId is in LoginRes
                    return fundAccountClient.createFundAccount(userId, createAccountReq)
                            .then(Mono.just(userResponse)) // Return user response on success
                            .onErrorResume(accountError -> {
                                // Step 3: If account creation fails, roll back the user creation
                                return userProfileClient.deleteProfile(userId)
                                        .then(Mono.error(new RuntimeException("Fund account creation failed. User rolled back.")));
                            });
                })
                .onErrorResume(userError -> {
                    // If user creation fails, return the error
                    return Mono.error(new RuntimeException("User registration failed: " + userError.getMessage()));
                });
    }

    public Mono<ResponseEntity<LoginRes>> login(LoginReq req) {
        return userAuthClient.login(req);
    }

    public Mono<ResponseEntity<UserRes>> getProfile(Long id) {
        return userProfileClient.getProfile(String.valueOf(id));
    }

    public Mono<ResponseEntity<UserRes>> updateProfile(Long id, UpdateUserReq req) {
        return userProfileClient.updateProfile(String.valueOf(id), req);
    }

    public Mono<ResponseEntity<Void>> deleteProfile(Long id) {
        return userProfileClient.deleteProfile(String.valueOf(id));
    }

    public Mono<ResponseEntity<UserRes>> findByMobileNo(String mobileNo) {
        return userProfileClient.findByMobileNo(mobileNo);
    }
}
