package j3lcardmarket.atelier3.commons.utils;

import j3lcardmarket.atelier3.commons.models.TimedUserInfo;
import j3lcardmarket.atelier3.commons.models.TokenAuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SignatureException;

@Component
public class UserUtils implements LoginChecker<TimedUserInfo, String>{

    @Autowired
    SignatureUtils signatureUtils;

    @Autowired
    UserInfoSerializer serializer;

    private class TokenAuthInfoImpl implements TokenAuthInfo {
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
