package j3lcardmarket.atelier3.authserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "j3lcardmarket.atelier3.authserver.repositories")
class AuthJPAConfig {}
