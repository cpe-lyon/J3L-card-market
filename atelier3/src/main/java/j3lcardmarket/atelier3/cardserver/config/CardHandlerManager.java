package j3lcardmarket.atelier3.cardserver.config;

import j3lcardmarket.atelier3.commons.utils.UserUtils;
import j3lcardmarket.atelier3.userserver.services.UserService;
import j3lcardmarket.atelier3.cardserver.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CardHandlerManager implements WebMvcConfigurer {
    @Autowired
    UserUtils service;

    @Autowired
    CardService manager;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthHandler(service, manager)).addPathPatterns("/api/**");
    }
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
