package com.weng.business.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import static io.micrometer.common.util.StringUtils.isBlank;
import static org.springframework.util.StringUtils.hasText;

@Service
@Slf4j
public class JwtService {

    private final static String ISSUER = "WENG_USER_SERVICE";
    private final static String CLAIM_USAGE = "usage";
    private final static String USAGE_ACCESS_TOKEN = "access_token";

    @Value("${jwt.token.secret}")
    private String secret;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Use to verify access token by passing in token
    public Long verifyAccessToken(String token) {
        if (isBlank(token))
            return null;
        try {
            if (hasText(token)) {
                String userId = Jwts.parser()
                        .requireIssuer(ISSUER)
                        .require(CLAIM_USAGE, USAGE_ACCESS_TOKEN)
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .getSubject();

                // TODO: userId Validation

                return Long.parseLong(userId);
            }
        } catch (ExpiredJwtException e) {
            log.info("Token expired: {}", e.getMessage());
        } catch (Exception e) {
            log.info("Invalid token: {}", e.getMessage());
        }
        return null;
    }
}
