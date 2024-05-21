package j3lcardmarket.atelier2.authserver.models;

import j3lcardmarket.atelier2.commons.models.UserInfo;

public interface TokenAuthInfo {
    String getToken();
    UserInfo getUserInfo();
}