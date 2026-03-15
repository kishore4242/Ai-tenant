package com.aitenant.gateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateLimiterService {
    private final RateLimiterTokenBasedBucketService rateLimiterTokenBasedBucketService;

    public boolean isAllowedRequest(String clientId) throws Exception {
        return rateLimiterTokenBasedBucketService.isAllowed(clientId);
    }
}
