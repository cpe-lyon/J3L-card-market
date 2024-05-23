package j3lcardmarket.atelier2.cardserver.config;

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
        logService.newCardUser("xavier", userIdentifier -> {} );
        manager.createCard("30,000-Year White Turtle", "xavier", "https://images.ygoprodeck.com/images/cards/11714098.jpg");
        manager.createCard("4-Starred Ladybug of Doom", "xavier", "https://images.ygoprodeck.com/images/cards/83994646.jpg");
        manager.createCard("7", "xavier", "https://images.ygoprodeck.com/images/cards/67048711.jpg");
        manager.createCard("7 Colored Fish", "xavier", "https://images.ygoprodeck.com/images/cards/23771716.jpg");
        manager.createCard("7 Completed", "xavier", "https://images.ygoprodeck.com/images/cards/86198326.jpg");
        manager.createCard("8-Claws Scorpion", "xavier", "https://images.ygoprodeck.com/images/cards/14261867.jpg");
        manager.createCard("A Cat of Ill Omen", "xavier", "https://images.ygoprodeck.com/images/cards/24140059.jpg");
        manager.createCard("A Deal with Dark Ruler", "xavier", "https://images.ygoprodeck.com/images/cards/6850209.jpg");
        manager.createCard("A Feather of the Phoenix", "xavier", "https://images.ygoprodeck.com/images/cards/49140998.jpg");
        manager.createCard("A Feint Plan", "xavier", "https://images.ygoprodeck.com/images/cards/68170903.jpg");

        if (urepo.existsById("jorge")) return;
        logService.newCardUser("jorge", manager::giveFiveRandomCards);
    }

}
