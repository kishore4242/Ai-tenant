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
                        .uri("http://localhost:8082"))
                .route(locator -> locator.path("/api/ai/**")
                        .uri("http://localhost:8083"))
                .build();
    }
}
