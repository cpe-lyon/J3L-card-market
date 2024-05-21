package j3lcardmarket.atelier2.commons.utils;

import j3lcardmarket.atelier2.authserver.models.BasicAuthInfo;
import j3lcardmarket.atelier2.commons.models.AuthInfo;

public interface LoginChecker <U, T extends AuthInfo>{
    U checkLogin(T info);
    U register(T info);
}
