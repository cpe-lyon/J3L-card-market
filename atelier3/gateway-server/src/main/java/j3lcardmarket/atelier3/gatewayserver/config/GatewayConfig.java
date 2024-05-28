package j3lcardmarket.atelier3.gatewayserver.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    RouteLocator gateway(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("buy-page", routeSpec -> routeSpec
                        .path("/buy")
                        .uri("forward:/pages/buy.html"))
                .route("sell-page", routeSpec -> routeSpec
                        .path("/sell")
                        .uri("forward:/pages/sell.html"))
                .route("transactions-page", routeSpec -> routeSpec
                        .path("/transactions")
                        .uri("forward:/pages/transactions.html"))
            .build();
    }
}