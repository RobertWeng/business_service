package com.weng.business.converter;

import com.weng.business.util.HashId;
import com.weng.exception.Catch;
import com.weng.exception.Error;
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
        throw Catch.invalidRequest(Error.Msg.INVALID_HASH_ID, source);
    }
}