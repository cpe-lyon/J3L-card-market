package j3lcardmarket.atelier2.authserver.repositories;

import j3lcardmarket.atelier2.authserver.models.BasicAuthInfo;
import j3lcardmarket.atelier2.commons.models.UserInfo;
import j3lcardmarket.atelier2.commons.utils.LoginChecker;
import org.springframework.stereotype.Repository;

@Repository
public class DbLoginChecker implements LoginChecker<UserInfo, BasicAuthInfo> {
    @Override
    public UserInfo checkLogin(BasicAuthInfo info) {
        return null;
    }

    @Override
    public UserInfo register(BasicAuthInfo info) {
        return null;
    }
}
