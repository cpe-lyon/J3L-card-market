package j3lcardmarket.atelier2.authserver.repositories;

import j3lcardmarket.atelier2.authserver.models.BasicAuthInfo;
import j3lcardmarket.atelier2.authserver.models.DbUserInfo;
import j3lcardmarket.atelier2.commons.models.UserInfo;
import j3lcardmarket.atelier2.commons.utils.LoginChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DbLoginChecker implements LoginChecker<UserInfo, DbUserInfo> {

    @Autowired
    private User user;

    @Override
    public UserInfo checkLogin(DbUserInfo info) {
       if(user.login(info.userName(), info.password())){
           return info;
       }else{
           return null;
       }
    }

    @Override
    public UserInfo register(DbUserInfo info){
        user.insert(info.surname(), info.password(), info.avatarUrl(), info.userName());
        return info;
    }
}
