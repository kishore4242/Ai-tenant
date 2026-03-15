package com.aitenant.auth.filters;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;

@Component
public class JWTFilter {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(String username, Long tenant) {
        HashMap<String, Long> detailsList = new HashMap<>();
        detailsList.put("tenant_id",tenant);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .claim("tenant_id",tenant)
                .setExpiration(
                        new Date(System.currentTimeMillis() + expiration)
                )
                .signWith(
                        Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

}
