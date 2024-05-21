package j3lcardmarket.atelier2.cardserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class CardsServerApplication {

	public static void main(String[] args) {
		System.setProperty("spring.profiles.default", "cardserver");
		SpringApplication.run(CardsServerApplication.class, args);
	}

}
