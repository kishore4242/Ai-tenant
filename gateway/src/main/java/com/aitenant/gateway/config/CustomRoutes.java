package com.aitenant.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CustomRoutes {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route(locator -> locator.path("/api/auth/**")
                        .uri("lb://auth"))
                .route(locator -> locator.path("/api/ai/**")
                        .uri("lb://web-service"))
                .build();
    }
}
