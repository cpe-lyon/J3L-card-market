package j3lcardmarket.atelier3.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayServerApplication {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.default", "gateway-server");
        SpringApplication.run(GatewayServerApplication.class, args);
    }
}