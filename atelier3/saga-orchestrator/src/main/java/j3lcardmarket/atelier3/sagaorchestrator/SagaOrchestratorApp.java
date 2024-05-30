package j3lcardmarket.atelier3.sagaorchestrator;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@Deployment(resources = "classpath*:/models/*.*")
@EnableDiscoveryClient
@ComponentScan({"j3lcardmarket.atelier3.sagaorchestrator","j3lcardmarket.atelier3.commons"})
public class SagaOrchestratorApp {
    public static void main(String... args) {
        SpringApplication.run(SagaOrchestratorApp.class, args);
    }
}
