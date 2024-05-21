package j3lcardmarket.atelier2.authserver.models;

import j3lcardmarket.atelier2.commons.models.AuthInfo;

public interface BasicAuthInfo extends AuthInfo {
    String getUsername();
    String getPassword();
}
