package j3lcardmarket.atelier2.authserver.services;

import j3lcardmarket.atelier2.authserver.models.BasicAuthInfo;
import j3lcardmarket.atelier2.authserver.models.TokenAuthInfo;
import j3lcardmarket.atelier2.authserver.models.TokenAuthInfoFactory;
import j3lcardmarket.atelier2.authserver.repositories.DbLoginChecker;
import j3lcardmarket.atelier2.commons.models.UserInfo;
import j3lcardmarket.atelier2.commons.utils.LoginChecker;
import j3lcardmarket.atelier2.commons.utils.UserInfoSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenLoginChecker implements LoginChecker<TokenAuthInfo, BasicAuthInfo> {
    @Autowired
    DbLoginChecker repo;

    @Autowired
    TokenAuthInfoFactory authInfoFactory;

    @Override
    public TokenAuthInfo checkLogin(BasicAuthInfo info) {
        UserInfo uInfo = repo.checkLogin(info);
        return authInfoFactory.createTokenAuthInfo(uInfo);
    }

    @Override
    public TokenAuthInfo register(BasicAuthInfo info) {
        UserInfo uInfo =  repo.register(info);
        return authInfoFactory.createTokenAuthInfo(uInfo);
    }
}
