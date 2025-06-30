package com.ecommerce.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // User service
                .route("user", r -> r.path("/api/users/**")
                        .uri("lb://USER"))

                // Product service
                .route("product", r -> r.path("/api/products/**")
                        .uri("lb://PRODUCT"))

                // Order service
                .route("order", r -> r.path("/api/orders/**", "/api/cart/**")
                        .uri("lb://ORDER"))


                // Route to expose Eureka dashboard ("/eureka/main" → "/")
                .route("eureka-server", r -> r.path("/eureka/main", "/eureka/**")
                        .filters(f -> f.setPath("/"))
                        .uri("http://localhost:4005"))

//                // Static Eureka dashboard assets ("/eureka/**")
//                .route("eureka-server-static", r -> r.path("/eureka/**")
//                        .uri("http://localhost:4005"))

                .build();
    }
}
