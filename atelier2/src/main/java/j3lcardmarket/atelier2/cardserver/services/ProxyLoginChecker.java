package j3lcardmarket.atelier2.cardserver.services;

import j3lcardmarket.atelier2.authserver.models.BasicAuthInfo;
import j3lcardmarket.atelier2.authserver.models.TokenAuthInfo;
import j3lcardmarket.atelier2.commons.models.TimedUserInfo;
import j3lcardmarket.atelier2.commons.models.UserInfo;
import j3lcardmarket.atelier2.commons.utils.InvalidTokenException;
import j3lcardmarket.atelier2.commons.utils.LoginChecker;
import j3lcardmarket.atelier2.commons.utils.SignatureUtils;
import j3lcardmarket.atelier2.commons.utils.UserInfoSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SignatureException;

/**
 * In the proxy, check = register
 */
@Service
public class ProxyLoginChecker implements LoginChecker<TimedUserInfo, String> {

    @Autowired
    SignatureUtils signatureUtils;

    @Autowired
    UserInfoSerializer serializer;

    private class TokenAuthInfoImpl implements TokenAuthInfo{
        private final String token;
        private final TimedUserInfo userInfo;

        TokenAuthInfoImpl(String token) throws SignatureException, InvalidTokenException {
            this.token = token;
            String unsigned = signatureUtils.unsign(token);
            this.userInfo = serializer.fromUnsignedToken(unsigned);
        }
        @Override
        public String getToken() {
            return token;
        }
        @Override
        public TimedUserInfo getUserInfo() {
            return userInfo;
        }
        @Override
        public String serialize() {
            return token;
        }
    }

    @Override
    public TimedUserInfo checkLogin(String token) {
        try {
            return new TokenAuthInfoImpl(token).getUserInfo();
        } catch (SignatureException | InvalidTokenException e) {
            return null;
        }
    }

    @Override
    public TimedUserInfo register(String token) {
        return checkLogin(token);
    }
}
