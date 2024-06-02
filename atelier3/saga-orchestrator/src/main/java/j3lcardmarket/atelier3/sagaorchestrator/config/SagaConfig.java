package j3lcardmarket.atelier3.sagaorchestrator.config;

import io.camunda.zeebe.client.ZeebeClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
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
