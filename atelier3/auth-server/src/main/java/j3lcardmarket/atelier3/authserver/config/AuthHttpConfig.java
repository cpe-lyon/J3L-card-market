package j3lcardmarket.atelier3.authserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AuthHttpConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
