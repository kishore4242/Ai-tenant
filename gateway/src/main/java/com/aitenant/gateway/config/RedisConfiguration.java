package com.aitenant.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {
    private final RedisConfigProperties configProperties;
    @Bean
    public JedisPool getJedisPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(50);
        config.setMaxIdle(10);
        config.setMinIdle(5);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);

        return new JedisPool(
                config,
                configProperties.getHost(),
                configProperties.getPort(),
                configProperties.getTimeout()
        );
    }
}
