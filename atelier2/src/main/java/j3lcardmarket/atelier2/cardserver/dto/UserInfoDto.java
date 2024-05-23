package j3lcardmarket.atelier2.cardserver.dto;

import j3lcardmarket.atelier2.commons.models.UserInfo;
import lombok.Getter;

@Getter
public class UserInfoDto {

    private final String username;
    private final String surname;
    private final String avatarUrl;
    private final boolean isAdmin;

    public UserInfoDto(UserInfo userInfo, String adminName){
        this.username = userInfo.userName();
        this.surname = userInfo.surname();
        this.avatarUrl = userInfo.avatarUrl();
        this.isAdmin = userInfo.userName().equals(adminName);
    }
}
