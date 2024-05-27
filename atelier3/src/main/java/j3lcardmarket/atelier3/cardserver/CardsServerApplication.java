package j3lcardmarket.atelier3.cardserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("j3lcardmarket.atelier3.commons")
public class CardsServerApplication {

	public static void main(String[] args) {
		System.setProperty("spring.profiles.default", "cardserver");
		SpringApplication.run(CardsServerApplication.class, args);
	}

}
