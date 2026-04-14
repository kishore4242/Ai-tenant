package com.aitenant.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfigProperties {
    private String host;
    private int port;
    private int timeout;
}
