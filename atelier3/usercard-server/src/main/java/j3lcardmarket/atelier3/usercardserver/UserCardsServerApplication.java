package j3lcardmarket.atelier3.usercardserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"j3lcardmarket.atelier3.commons", "j3lcardmarket.atelier3.usercardserver"})
public class UserCardsServerApplication {

	public static void main(String[] args) {
		System.setProperty("spring.profiles.default", "usercardserver");
		SpringApplication.run(UserCardsServerApplication.class, args);
	}

}
