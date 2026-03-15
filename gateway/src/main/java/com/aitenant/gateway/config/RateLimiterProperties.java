package com.aitenant.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="rate-limiter")
public class RateLimiterProperties {
    private int capacity;
    private int timeout;
    private int refill;
}
