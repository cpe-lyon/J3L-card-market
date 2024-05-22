package j3lcardmarket.atelier2.cardserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "j3lcardmarket.atelier2.cardserver.repositories")
class CardJPAConfig {}
