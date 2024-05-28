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
            .route("card-server", routeSpec -> routeSpec.path("/cards/").uri("lb://card-service"))
            .route("user-server", routeSpec -> routeSpec.path("/users/").uri("lb://user-service"))
            .build();
    }
}