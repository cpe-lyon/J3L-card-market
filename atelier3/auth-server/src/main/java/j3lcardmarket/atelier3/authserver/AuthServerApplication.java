package j3lcardmarket.atelier3.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"j3lcardmarket.atelier3.authserver","j3lcardmarket.atelier3.commons"})
public class AuthServerApplication {
	public static void main(String[] args) {
		System.setProperty("spring.profiles.default", "authserver");
		SpringApplication.run(AuthServerApplication.class, args);
	}
}
