package com.aitenant.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Data
@Component
@ConfigurationProperties(prefix = "spring-redis")
public class RedisConfigProperties {
    private String host;
    private int port;
    private int timeout;
}
