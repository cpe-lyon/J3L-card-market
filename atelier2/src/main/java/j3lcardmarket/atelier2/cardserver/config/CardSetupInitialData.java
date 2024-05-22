package j3lcardmarket.atelier2.cardserver.config;

import j3lcardmarket.atelier2.authserver.models.BasicAuthInfo;
import j3lcardmarket.atelier2.authserver.models.User;
import j3lcardmarket.atelier2.authserver.services.DbLoginChecker;
import j3lcardmarket.atelier2.cardserver.repositories.CardRepository;
import j3lcardmarket.atelier2.cardserver.repositories.UserIdentifierRepository;
import j3lcardmarket.atelier2.cardserver.services.ProxyLoginChecker;
import j3lcardmarket.atelier2.cardserver.services.TransactionalCardManager;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardSetupInitialData {

    private final UserIdentifierRepository urepo;
    private final ProxyLoginChecker logService;
    private final TransactionalCardManager manager;

    @Autowired
    public CardSetupInitialData(UserIdentifierRepository urepo,  ProxyLoginChecker logService, TransactionalCardManager manager) {
        this.urepo = urepo;
        this.logService = logService;
        this.manager = manager;
    }

    @PostConstruct
    public void initialize() {
        if (urepo.existsById("xavier")) return;
        logService.saveUser("xavier");
        manager.create("Blue-Eyes White Dragon", "xavier");
    }

}
