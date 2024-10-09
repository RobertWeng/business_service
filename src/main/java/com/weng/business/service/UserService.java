package com.weng.business.service;

import com.weng.business.ws.user.UserProfileClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserProfileClient userProfileClient;

    public void getProfile(Long id) {
        ResponseEntity<String> responseEntity = userProfileClient.getProfile(String.valueOf(id));
        System.out.println(responseEntity);
        System.out.println("test");
    }
}
