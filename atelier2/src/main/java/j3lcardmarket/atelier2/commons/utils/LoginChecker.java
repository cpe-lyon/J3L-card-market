package j3lcardmarket.atelier2.commons.utils;

public interface LoginChecker <U, T>{
    U checkLogin(T info);
    U register(T info);
}
