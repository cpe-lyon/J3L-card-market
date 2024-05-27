package j3lcardmarket.atelier3.userserver.services;

import j3lcardmarket.atelier3.commons.models.TimedUserInfo;
import j3lcardmarket.atelier3.commons.models.UserIdentifier;
import j3lcardmarket.atelier3.commons.utils.SignatureUtils;
import j3lcardmarket.atelier3.commons.utils.UserInfoSerializer;
import j3lcardmarket.atelier3.commons.utils.UserUtils;
import j3lcardmarket.atelier3.userserver.repositories.UserIdentifierRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Integer getBalance(String username) {
        Optional<UserIdentifier> user = repo.findById(username);
        if (user.isEmpty()) return -1;
        return user.get().getBalance();
    }

    @Cacheable(value="savedname", key="#username")
    public String newCardUser(String username, Consumer<UserIdentifier> onUserCreation){
        //If not new, do nothing
        if(repo.existsBySurname(username)) return username;
        UserIdentifier savedUser = repo.save(new UserIdentifier(username));
        onUserCreation.accept(savedUser);
        return savedUser.getSurname();
    }

    @Override
    public TimedUserInfo register(String token) {
        return checkLogin(token);
    }
}
