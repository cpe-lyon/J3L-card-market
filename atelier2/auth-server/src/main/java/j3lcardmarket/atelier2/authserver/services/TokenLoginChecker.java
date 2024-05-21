package j3lcardmarket.atelier2.authserver.services;

import j3lcardmarket.atelier2.authserver.models.BasicAuthInfo;
import j3lcardmarket.atelier2.authserver.models.TokenAuthInfo;
import j3lcardmarket.atelier2.commons.utils.LoginChecker;
import org.springframework.stereotype.Service;

@Service
public class TokenLoginChecker implements LoginChecker<TokenAuthInfo, BasicAuthInfo> {
    @Override
    public TokenAuthInfo checkLogin(BasicAuthInfo info) {
        return null;
    }

    @Override
    public TokenAuthInfo register(BasicAuthInfo info) {
        return null;
    }
}
