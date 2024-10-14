package com.weng.business.ws;

import java.util.HashMap;
import java.util.Map;

public class ParamsBuilder {
    private final Map<String, String> params = new HashMap<>();

    public ParamsBuilder addParam(String key, String value) {
        params.put(key, value);
        return this;
    }

    public ParamsBuilder addParam(String key, int value) {
        params.put(key, String.valueOf(value));
        return this;
    }

    public Map<String, String> build() {
        return params;
    }
}
