package j3lcardmarket.atelier3.authserver.utils;

import j3lcardmarket.atelier3.commons.models.TokenAuthInfo;
import j3lcardmarket.atelier3.commons.models.UserInfo;
import j3lcardmarket.atelier3.commons.utils.SignatureUtils;
import j3lcardmarket.atelier3.commons.utils.UserInfoSerializer;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthInfoFactory{

    @Autowired
    UserInfoSerializer serializer;
    @Autowired
    SignatureUtils signatureUtils;

    private class TokenAuthInfoAdapter implements TokenAuthInfo {
        private final UserInfo src;
        @Getter
        private final String token;

        public TokenAuthInfoAdapter(UserInfo src){
            this.src = src;
            String unsignedToken = serializer.toUnsignedToken(src);
            token = signatureUtils.sign(unsignedToken);
        }

        public UserInfo getUserInfo() {
            return src ;
        }

        public String serialize() {
            return token;
        }
    }

    public TokenAuthInfo createTokenAuthInfo(UserInfo userInfo){
        if (userInfo == null) return null;
        return new TokenAuthInfoAdapter(userInfo);
    }

}
