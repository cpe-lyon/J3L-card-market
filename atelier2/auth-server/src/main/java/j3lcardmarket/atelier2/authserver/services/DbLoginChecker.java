package j3lcardmarket.atelier2.authserver.services;

import j3lcardmarket.atelier2.authserver.models.User;
import j3lcardmarket.atelier2.authserver.repositories.UserRepository;
import j3lcardmarket.atelier2.commons.models.UserInfo;
import j3lcardmarket.atelier2.commons.utils.LoginChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class DbLoginChecker implements LoginChecker<UserInfo, User> {

    @Autowired
    private UserRepository repo;

    @Override
    public UserInfo checkLogin(User info) {
        //TODO: password encryption
       return repo.login(info.userName(), info.getPassword());
    }

    @Override
    public UserInfo register(User info){
        //TODO: password encryption
        if (repo.existsById(info.getUserName())) return null;
        return repo.save(info);
    }
}
