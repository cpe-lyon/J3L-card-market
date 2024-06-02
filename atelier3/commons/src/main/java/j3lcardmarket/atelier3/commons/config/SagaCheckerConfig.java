package j3lcardmarket.atelier3.commons.config;

import j3lcardmarket.atelier3.commons.utils.ForbiddenException;
import j3lcardmarket.atelier3.commons.utils.SagaChecker;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class SagaCheckerConfig {

    @Value("${orchestrator.token:none}")
    String orchestratorToken;

    private String orchestratorHeader(){
        return String.format("Bearer %s", orchestratorToken);
    }
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public SagaChecker sagaChecker() {
        HttpServletRequest curRequest =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                        .getRequest();
        return () -> {
            String sagaHeader = curRequest.getHeader("Authorization");
            if (sagaHeader == null || !sagaHeader.trim().equals(orchestratorHeader().trim()))
                throw new ForbiddenException();
        };
    }
}
