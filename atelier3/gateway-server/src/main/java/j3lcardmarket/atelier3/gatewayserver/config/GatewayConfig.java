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
                .route("login-page", routeSpec -> routeSpec
                        .path("/login")
                        .uri("forward:/login.html"))
                .route("buy-page", routeSpec -> routeSpec
                        .path("/buy")
                        .uri("forward:/pages/buy.html"))
                .route("sell-page", routeSpec -> routeSpec
                        .path("/sell")
                        .uri("forward:/pages/sell.html"))
                .route("transactions-page", routeSpec -> routeSpec
                        .path("/transactions")
                        .uri("forward:/pages/transactions.html"))
                .route("game-page", routeSpec -> routeSpec
                        .path("/game")
                        .uri("forward:/pages/game.html"))
                .route("game-room-page", routeSpec -> routeSpec
                        .path("/game-room/{id}")
                        .uri("forward:/pages/game-room.html"))
                .route("api-auth", routeSpec -> routeSpec
                        .path("/api/auth/**")
                        .uri("lb://auth-server"))
                .route("api-cards", routeSpec -> routeSpec
                        .path("/api/cards/**")
                        .uri("lb://card-server"))
                .route("api-users", routeSpec -> routeSpec
                        .path("/api/users/**")
                        .uri("lb://user-server"))
                .route("api-usercards", routeSpec -> routeSpec
                        .path("/api/usercards/**")
                        .uri("lb://usercard-server"))
                .route("api-transactions", routeSpec -> routeSpec
                        .path("/api/transactions/**")
                        .uri("lb://user-server"))
                .route("api-game", routeSpec -> routeSpec
                        .path("/api/game-rooms/**")
                        .uri("lb://game-server"))
                .route("api-orchestrate", routeSpec -> routeSpec
                        .path("/api/orchestrate/**")
                        .uri("lb://saga-orchestrator"))
            .build();
    }
}