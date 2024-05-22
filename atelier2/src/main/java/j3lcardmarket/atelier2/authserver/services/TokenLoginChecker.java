package j3lcardmarket.atelier2.authserver.services;

import j3lcardmarket.atelier2.authserver.models.User;
import j3lcardmarket.atelier2.authserver.models.TokenAuthInfo;
import j3lcardmarket.atelier2.authserver.utils.TokenAuthInfoFactory;
import j3lcardmarket.atelier2.commons.models.UserInfo;
import j3lcardmarket.atelier2.commons.utils.LoginChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenLoginChecker implements LoginChecker<TokenAuthInfo, User> {
    @Autowired
    DbLoginChecker dbService;

    @Autowired
    TokenAuthInfoFactory authInfoFactory;

    @Override
    public TokenAuthInfo checkLogin(User info) {
        UserInfo uInfo = dbService.checkLogin(info);
        return authInfoFactory.createTokenAuthInfo(uInfo);
    }

    @Override
    public TokenAuthInfo register(User info) {
        UserInfo uInfo =  dbService.register(info);
        return authInfoFactory.createTokenAuthInfo(uInfo);
    }
}
