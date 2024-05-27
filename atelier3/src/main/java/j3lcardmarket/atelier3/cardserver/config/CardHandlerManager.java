package j3lcardmarket.atelier3.cardserver.config;

import j3lcardmarket.atelier3.cardserver.services.CardService;
import j3lcardmarket.atelier3.commons.utils.UserUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CardHandlerManager implements WebMvcConfigurer {
    UserUtils service;

    CardService manager;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthHandler(service, manager)).addPathPatterns("/api/**");
    }
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
