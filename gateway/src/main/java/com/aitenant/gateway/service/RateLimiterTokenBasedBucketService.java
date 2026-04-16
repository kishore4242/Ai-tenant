package com.aitenant.gateway.service;

import com.aitenant.gateway.config.RateLimiterProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Slf4j
@RequiredArgsConstructor
@Service
public class RateLimiterTokenBasedBucketService {
    private final JedisPool jedisPool;
    private final RateLimiterProperties rateLimiterProperties;
    private final String TOKEN_KEY_PREFIX = "rate_limiter:tokens:";
    private final String LAST_REFILL_PREFIX_KEY = "rate_limiter:last_refill:";

    public boolean isAllowed(String clientId) throws Exception {
        // Check if the user can allowed the request ot not
        String token = TOKEN_KEY_PREFIX+clientId;

        try(Jedis jedis = jedisPool.getResource()){
            log.info("Connection established to jedis");
            fillCurrentEntryToken(clientId, jedis);
            String currentToken = jedis.get(token);
            long tokenValue = currentToken != null ? Long.parseLong(currentToken) : rateLimiterProperties.getCapacity();

            if(tokenValue <= 0){
                return false;
            }
            long decrement = jedis.decr(token);
            return decrement >= 0;
        }
        catch (Exception e){
            System.out.println("Some exception during the jedis connect");
            log.error(e.getMessage());
            return true;
        }

    }

    private void fillCurrentEntryToken(String clientId, Jedis jedis) {
        // Fill and refill the token based on the time
        String tokenKey = TOKEN_KEY_PREFIX+clientId;
        String lastRefilled = LAST_REFILL_PREFIX_KEY+clientId;

        long currentTime = System.currentTimeMillis();
        String lastRefilledTime = jedis.get(lastRefilled);

        if(lastRefilledTime == null){
            jedis.set(tokenKey, String.valueOf(rateLimiterProperties.getCapacity()));
            jedis.set(lastRefilled, String.valueOf(currentTime));
            return;
        }
        long refillTime = Long.parseLong(lastRefilledTime);
        long remainingTime = currentTime-refillTime;

        if(remainingTime <= 0){
            return;
        }
        long tokenAdd = (remainingTime*rateLimiterProperties.getRefill())/1000;
        if(tokenAdd <= 0){
            return;
        }
        String tokenVal = jedis.get(tokenKey);
        long currentToken = tokenVal != null ? Long.parseLong(tokenVal) : rateLimiterProperties.getCapacity();
        long newToken = Math.min(currentToken+tokenAdd, rateLimiterProperties.getCapacity());
        jedis.set(tokenKey, String.valueOf(newToken));
        jedis.set(lastRefilled, String.valueOf(currentTime));
    }
}
