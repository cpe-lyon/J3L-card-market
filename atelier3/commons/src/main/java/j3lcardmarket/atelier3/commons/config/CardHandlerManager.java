package j3lcardmarket.atelier3.commons.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CardHandlerManager implements WebMvcConfigurer {

    @Autowired(required = false)
    AuthHandler authHandler;

    public void addInterceptors(InterceptorRegistry registry) {
        if(authHandler == null) return;
        registry.addInterceptor(authHandler).addPathPatterns("/api/**");
    }
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
