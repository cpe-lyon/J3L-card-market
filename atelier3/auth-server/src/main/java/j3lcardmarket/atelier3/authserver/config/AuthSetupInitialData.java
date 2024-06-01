package j3lcardmarket.atelier3.authserver.config;

import j3lcardmarket.atelier3.authserver.models.BasicAuthInfo;
import j3lcardmarket.atelier3.authserver.models.User;
import j3lcardmarket.atelier3.authserver.repositories.UserRepository;
import j3lcardmarket.atelier3.authserver.services.DbLoginChecker;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthSetupInitialData {

    private final DbLoginChecker registerService;
    private final UserRepository uRepo;

    @Autowired
    public AuthSetupInitialData(DbLoginChecker registerService, UserRepository uRepo) {
        this.registerService = registerService;
        this.uRepo = uRepo;
    }

    @PostConstruct
    public void initialize() {
        if (uRepo.existsById("xavier")) return;
        registerService.register(new User(new BasicAuthInfo() {
            @Override
            public String getUsername() {
                return "xavier";
            }

            @Override
            public String getPassword() {
                return "xavier";
            }

            @Override
            public String serialize() {
                return "xavier:xavier";
            }
        }, "Xavier", "https://varnam.my/wp-content/uploads/2021/01/FB_IMG_1605666747087-2.jpg"),
                false);

    }

}
