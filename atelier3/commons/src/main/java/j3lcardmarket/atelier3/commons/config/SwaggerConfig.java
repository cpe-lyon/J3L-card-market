package j3lcardmarket.atelier3.commons.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("cardauth",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer"))
                        .addSecuritySchemes("basicauth",
                            new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"))
                );
    }
}
