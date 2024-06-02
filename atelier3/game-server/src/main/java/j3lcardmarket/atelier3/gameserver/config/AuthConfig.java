package j3lcardmarket.atelier3.gameserver.config;

import j3lcardmarket.atelier3.commons.config.AuthHandler;
import j3lcardmarket.atelier3.commons.models.TimedUserInfo;
import j3lcardmarket.atelier3.commons.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

    @Autowired
    UserUtils utils;

    @Bean
    public AuthHandler getHandler(){
        return new AuthHandler(utils) {
            @Override
            protected void onLog(TimedUserInfo userInfo) {}
        };
    }

}
