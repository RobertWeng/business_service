package com.weng.business.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

@Service
@Slf4j
public class JsonService {

    @Autowired
    private ObjectMapper om;

    public <T> T readValue(String value, Class<T> clazz) {
        try {
            return om.readValue(value, clazz);
        } catch (JsonProcessingException e) {
            log.info(getStackTrace(e));
            return null;
        }
    }

    public String writeValue(Object value) {
        try {
            return om.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.info(getStackTrace(e));
            return null;
        }
    }
}
