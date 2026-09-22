package com.aitenant.gateway.config;

import com.aitenant.gateway.service.RateLimiterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenBucketRateLimitingConfiguration implements GlobalFilter, Ordered {
    private final RateLimiterService rateLimiterService;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var remoteAddress = exchange.getRequest().getRemoteAddress();
        String clientIp = remoteAddress != null
                ? remoteAddress.getAddress().getHostAddress()
                : "unknown";
        try {
            if(!rateLimiterService.isAllowedRequest(clientIp)){
                exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                log.error("Too many request: {}", clientIp);
                return exchange.getResponse().setComplete();
            }
        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error(e.getMessage());
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}

