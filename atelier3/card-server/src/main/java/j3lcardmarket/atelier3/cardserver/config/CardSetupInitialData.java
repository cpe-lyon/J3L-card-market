package j3lcardmarket.atelier3.cardserver.config;
import j3lcardmarket.atelier3.cardserver.services.CardService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardSetupInitialData {

    private final CardService manager;

    @Autowired
    public CardSetupInitialData(CardService manager) {
        this.manager = manager;
    }

    @PostConstruct
    public void initialize() {
        if (manager.getAll().isEmpty()) {
            manager.createCard("30,000-Year White Turtle", "https://images.ygoprodeck.com/images/cards/11714098.jpg", 5, 2100, 1250, "WATER");
            manager.createCard("4-Starred Ladybug of Doom", "https://images.ygoprodeck.com/images/cards/83994646.jpg", 3, 1200, 800, "WIND");
            manager.createCard("7", "https://images.ygoprodeck.com/images/cards/67048711.jpg", 0, 0, 0, "SPELL");
            manager.createCard("7 Colored Fish", "https://images.ygoprodeck.com/images/cards/23771716.jpg", 4, 800, 1800, "WATER");
            manager.createCard("7 Completed", "https://images.ygoprodeck.com/images/cards/86198326.jpg", 0, 0, 0, "SPELL");
            manager.createCard("8-Claws Scorpion", "https://images.ygoprodeck.com/images/cards/14261867.jpg", 2, 200, 300, "DARK");
            manager.createCard("A Cat of Ill Omen", "https://images.ygoprodeck.com/images/cards/24140059.jpg", 2, 300, 500, "DARK");
            manager.createCard("A Deal with Dark Ruler", "https://images.ygoprodeck.com/images/cards/6850209.jpg", 0, 0, 0, "SPELL");
            manager.createCard("A Feather of the Phoenix", "https://images.ygoprodeck.com/images/cards/49140998.jpg", 0, 0, 0, "SPELL");
            manager.createCard("A Feint Plan", "https://images.ygoprodeck.com/images/cards/68170903.jpg", 0, 0, 0, "TRAP");
        }
    }
}
