package j3lcardmarket.atelier2.commons.models;

import java.util.Date;

public class TimedUserInfo implements UserInfo{
    private final UserInfo info;
    private final Date date;

    public TimedUserInfo(UserInfo info, Long timestamp){
        this.info = info;
        this.date = new Date(timestamp);
    }

    @Override
    public String userName() {
        return info.userName();
    }

    @Override
    public String surname() {
        return info.surname();
    }

    @Override
    public String avatarUrl() {
        return info.avatarUrl();
    }

    public Date getExpireDate() {
        return date;
    }
}
