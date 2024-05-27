package j3lcardmarket.atelier3.commons.utils;

public interface LoginChecker <U, T>{
    U checkLogin(T info);
    U register(T info);
}
