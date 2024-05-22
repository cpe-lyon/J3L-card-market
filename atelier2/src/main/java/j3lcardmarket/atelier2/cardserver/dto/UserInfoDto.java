package j3lcardmarket.atelier2.cardserver.dto;

import j3lcardmarket.atelier2.commons.models.UserInfo;
import lombok.Getter;

@Getter
public class UserInfoDto {

    private final String username;
    private final String surname;
    private final String avatarUrl;

    public UserInfoDto(UserInfo userInfo){
        this.username = userInfo.userName();
        this.surname = userInfo.surname();
        this.avatarUrl = userInfo.avatarUrl();
    }
}
