package j3lcardmarket.atelier2.authserver.models;

import j3lcardmarket.atelier2.commons.models.AuthInfo;
import j3lcardmarket.atelier2.commons.models.UserInfo;

public interface TokenAuthInfo extends AuthInfo {
    String getToken();
    UserInfo getUserInfo();
}