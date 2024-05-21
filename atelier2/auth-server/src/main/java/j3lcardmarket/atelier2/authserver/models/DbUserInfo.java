package j3lcardmarket.atelier2.authserver.models;

import j3lcardmarket.atelier2.commons.models.AuthInfo;
import j3lcardmarket.atelier2.commons.models.UserInfo;

public record DbUserInfo(String userName, String password, String surname, String avatarUrl) implements UserInfo, AuthInfo {
    @Override
    public String serialize() {
        return userName + "," + password + "," + surname + "," + avatarUrl;
    }
}
