package com.aitenant.auth.service;

import com.aitenant.auth.dto.RefreshTokenRequest;
import com.aitenant.auth.filters.JWTFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final JWTFilterService jwtFilterService;

    public ResponseEntity<?> logoutUser(String accessToken, RefreshTokenRequest token) {
        if(token != null && token.getRefreshToken() != null){
            String userName = jwtFilterService.extractUsername(token.getRefreshToken());
            redisTemplate.opsForValue().set("blocked-token:"+userName, accessToken, 900_000, TimeUnit.SECONDS );
            redisTemplate.delete("refresh-token" +userName);
            return ResponseEntity.status(200).body("logout success");
        }
        return ResponseEntity.status(402).body("Invalid token");
    }
}
