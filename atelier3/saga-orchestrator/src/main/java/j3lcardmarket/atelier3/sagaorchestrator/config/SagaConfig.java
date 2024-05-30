package j3lcardmarket.atelier3.sagaorchestrator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SagaConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
