package j3lcardmarket.atelier2.cardserver.services;

import j3lcardmarket.atelier2.authserver.models.BasicAuthInfo;
import j3lcardmarket.atelier2.authserver.models.TokenAuthInfo;
import j3lcardmarket.atelier2.commons.utils.LoginChecker;
import org.springframework.stereotype.Service;

/**
 * In the proxy, check = register
 */
@Service
public class ProxyLoginChecker implements LoginChecker<TokenAuthInfo, BasicAuthInfo> {
    @Override
    public TokenAuthInfo checkLogin(BasicAuthInfo info) {
        return null;
    }

    @Override
    public TokenAuthInfo register(BasicAuthInfo info) {
        return checkLogin(info);
    }
}
