package j3lcardmarket.atelier2.cardserver.config;

import j3lcardmarket.atelier2.cardserver.repositories.UserCardRepository;
import j3lcardmarket.atelier2.cardserver.services.ProxyLoginChecker;
import j3lcardmarket.atelier2.cardserver.services.TransactionalCardManager;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CardHandlerManager implements WebMvcConfigurer {
    @Autowired
    ProxyLoginChecker service;

    @Autowired
    TransactionalCardManager manager;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthHandler(service, manager)).addPathPatterns("/api/**");
    }
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
