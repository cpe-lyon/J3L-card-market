package j3lcardmarket.atelier3.authserver.services;

import j3lcardmarket.atelier3.authserver.models.User;
import j3lcardmarket.atelier3.authserver.repositories.UserRepository;
import j3lcardmarket.atelier3.commons.models.UserInfo;
import j3lcardmarket.atelier3.commons.utils.LoginChecker;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.security.spec.KeySpec;
import java.util.Collections;
import java.util.Map;

@Service
public class DbLoginChecker implements LoginChecker<UserInfo, User> {

    @Autowired
    private UserRepository repo;

    @Value("${auth.gateway-url}")
    String gatewayUrl;

    @Value("${auth.token}")
    String authToken;

    @SneakyThrows
    @Override
    public UserInfo checkLogin(User info) {
        String password = info.getPassword();
        KeySpec spec = new PBEKeySpec(password.toCharArray(), info.getUserName().getBytes(), 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
       return repo.login(info.userName(), new String(hash, "UTF-8"));
    }

    @Override
    public UserInfo register(User info){
        return register(info, true);
    }

    @SneakyThrows
    public UserInfo register(User info, boolean initCards){
        String password = info.getPassword();
        KeySpec spec = new PBEKeySpec(password.toCharArray(), info.getUserName().getBytes(), 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        info.setPassword(new String(hash, "UTF-8"));
        if (repo.existsById(info.getUserName())) return null;
        User saved = repo.save(info);
        if(initCards) {
            try {

                String uri = UriComponentsBuilder
                        .fromHttpUrl(gatewayUrl)
                        .queryParam("user", saved.getUserName())
                        .path("/api/orchestrate/register")
                        .toUriString();

                HttpEntity<String> request = new HttpEntity<>(
                        null,
                        new MultiValueMapAdapter<>(
                                Map.of("Authorization", Collections.singletonList("Bearer " + authToken))
                        ));
                String res = new RestTemplate().postForObject(uri, request, String.class);
                if(!res.equals("OK")) throw new IOException("Wrong http response body");
            } catch (Exception e) {
                repo.delete(saved);
                throw new IOException(e);
            }
        }
        return saved;
    }
}
