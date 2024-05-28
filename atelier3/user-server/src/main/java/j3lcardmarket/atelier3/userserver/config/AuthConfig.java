package j3lcardmarket.atelier3.userserver.config;

import j3lcardmarket.atelier3.commons.config.AuthHandler;
import j3lcardmarket.atelier3.commons.models.TimedUserInfo;
import j3lcardmarket.atelier3.userserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

    @Autowired
    UserService service;

    @Bean
    public AuthHandler getHandler(){
        return new AuthHandler(service) {
            @Override
            protected void onLog(TimedUserInfo userInfo) {
                ((UserService)service).newCardUser(userInfo.userName(), (userId) -> {

                });
            }
        };
    }

}
