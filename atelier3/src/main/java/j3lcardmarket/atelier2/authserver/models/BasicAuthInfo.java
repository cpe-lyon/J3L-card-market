package j3lcardmarket.atelier3.authserver.models;

import j3lcardmarket.atelier3.commons.models.AuthInfo;
import j3lcardmarket.atelier3.commons.models.UserInfo;

public interface BasicAuthInfo extends AuthInfo {
    String getUsername();
    String getPassword();
}
