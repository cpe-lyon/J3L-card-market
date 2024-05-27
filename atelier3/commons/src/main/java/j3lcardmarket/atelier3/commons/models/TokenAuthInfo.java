package j3lcardmarket.atelier3.commons.models;

public interface TokenAuthInfo extends AuthInfo {
    String getToken();
    UserInfo getUserInfo();
}