package j3lcardmarket.atelier2.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServerApplication {

	public static void main(String[] args) {
		System.setProperty("spring.profiles.default", "authserver");
		SpringApplication.run(AuthServerApplication.class, args);
	}

}
