package com.weng.business.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.Base64;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Data
@Slf4j
public class HashId {

    private static final long SECRET_KEY = 987654321L;

    private long value;

    public HashId(long value) {
        this.value = value;
    }

    public static String encode(long id) {
        long transformedId = id ^ SECRET_KEY;
        return Base64
                .getUrlEncoder()
                .withoutPadding()
                .encodeToString(ByteBuffer.allocate(Long.BYTES).putLong(transformedId).array());
    }

    public static Long decode(String hashId) {
        try {

            if (isNotBlank(hashId)) {
                // Decode Base64
                byte[] decodedBytes = Base64.getUrlDecoder().decode(hashId);
                // Convert back to long
                long transformedId = ByteBuffer.wrap(decodedBytes).getLong();
                return transformedId ^ SECRET_KEY;
            }
        } catch (Exception e) {
            log.error("Invalid Base64 input: {}", hashId, e);
            return null;
        }

        return null;
    }
}
