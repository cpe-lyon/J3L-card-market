package j3lcardmarket.atelier3.usercardserver.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "j3lcardmarket.atelier3.usercardserver.repositories")
@EntityScan(basePackages = "j3lcardmarket.atelier3.commons.models")
class CardJPAConfig {}
