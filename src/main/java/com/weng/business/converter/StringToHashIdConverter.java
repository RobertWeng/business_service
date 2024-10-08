package com.weng.business.converter;

import com.weng.business.util.HashId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToHashIdConverter implements Converter<String, HashId> {

    @Override
    public HashId convert(String source) {
        Long decodedId = HashId.decode(source);
        if (decodedId != null) {
            return new HashId(decodedId);
        }
        throw new IllegalArgumentException("Invalid HashId format");
    }
}