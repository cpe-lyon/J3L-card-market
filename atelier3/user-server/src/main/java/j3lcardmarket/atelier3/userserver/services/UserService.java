package j3lcardmarket.atelier3.userserver.services;

import j3lcardmarket.atelier3.commons.models.TimedUserInfo;
import j3lcardmarket.atelier3.commons.models.UserIdentifier;
import j3lcardmarket.atelier3.commons.models.UserInfo;
import j3lcardmarket.atelier3.commons.utils.*;
import j3lcardmarket.atelier3.userserver.repositories.UserIdentifierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * In the proxy, check = register
 */
@Service
public class UserService extends UserUtils {

    @Autowired
    SignatureUtils signatureUtils;

    @Autowired
    UserInfoSerializer serializer;

    @Autowired
    UserIdentifierRepository repo;

    @Autowired
    private HttpUtils httpUtils;

    public Integer getBalance(String username) {
        Optional<UserIdentifier> user = repo.findById(username);
        if (user.isEmpty()) return -1;
        return user.get().getBalance();
    }

    @Value("${userservice.usercardserviceurl}")
    String usercardServiceUrl;
    public String newCardUser(String username){
        //If not new, do nothing
        if(repo.existsBySurname(username)) return username;
        UserIdentifier user = new UserIdentifier(username);
        UserIdentifier savedUser = repo.save(user);
        return savedUser.getSurname();
    }

    @Override
    public TimedUserInfo register(String token) {
        return checkLogin(token);
    }

    public void removeUser(String user) {
        repo.delete(repo.getReferenceById(user));
    }
}
