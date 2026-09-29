package com.aitenant.gateway.aspect;

import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class LoggerFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        String traceId = UUID.randomUUID().toString();

        return Mono.defer(() -> {
            MDC.put("traceId", traceId);

            try {
                return chain.filter(exchange);
            } finally {
                MDC.clear();
            }
        });
    }
}