package j3lcardmarket.atelier3.userserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("j3lcardmarket.atelier3.commons")
public class UsersServerApplication {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.default", "userserver");
        SpringApplication.run(UsersServerApplication.class, args);
    }
}
