package j3lcardmarket.atelier2.authserver.services;

import j3lcardmarket.atelier2.authserver.models.User;
import j3lcardmarket.atelier2.authserver.repositories.UserRepository;
import j3lcardmarket.atelier2.commons.models.UserInfo;
import j3lcardmarket.atelier2.commons.utils.LoginChecker;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;

@Service
public class DbLoginChecker implements LoginChecker<UserInfo, User> {

    @Autowired
    private UserRepository repo;

    @SneakyThrows
    @Override
    public UserInfo checkLogin(User info) {
        String password = info.getPassword();
        KeySpec spec = new PBEKeySpec(password.toCharArray(), info.getUserName().getBytes(), 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
       return repo.login(info.userName(), new String(hash, "UTF-8"));
    }

    @SneakyThrows
    @Override
    public UserInfo register(User info){
        String password = info.getPassword();
        KeySpec spec = new PBEKeySpec(password.toCharArray(), info.getUserName().getBytes(), 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        info.setPassword(new String(hash, "UTF-8"));
        if (repo.existsById(info.getUserName())) return null;
        return repo.save(info);
    }
}
