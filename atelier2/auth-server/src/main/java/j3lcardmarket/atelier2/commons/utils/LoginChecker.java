package j3lcardmarket.atelier2.commons.utils;

import j3lcardmarket.atelier2.commons.models.AuthInfo;

public interface LoginChecker <U, T>{
    U checkLogin(T info);
    U register(T info);
}
