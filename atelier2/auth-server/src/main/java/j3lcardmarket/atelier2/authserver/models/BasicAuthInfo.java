package j3lcardmarket.atelier2.authserver.models;

import j3lcardmarket.atelier2.authserver.repositories.User;
import j3lcardmarket.atelier2.commons.models.AuthInfo;
import j3lcardmarket.atelier2.commons.models.UserInfo;

public interface BasicAuthInfo extends AuthInfo {
    String getUsername();
    String getPassword();
}
