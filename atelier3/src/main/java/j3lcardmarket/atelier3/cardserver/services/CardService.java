package j3lcardmarket.atelier3.cardserver.services;

import j3lcardmarket.atelier3.cardserver.models.Card;
import j3lcardmarket.atelier3.cardserver.repositories.CardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepo;

    public List<Card> getAll() {
        return cardRepo.findAll();
    }

    public Card getById(int id) {
        return cardRepo.findById(id);
    }

    @Transactional
    public Card createCard(String cardName, String imageUrl, int level, int defense, int attack, String attribute) {

        Card newCard = new Card();
        newCard.setName(cardName);
        newCard.setImageUrl(imageUrl);
        newCard.setAttack(attack);
        newCard.setLevel(level);
        newCard.setDefense(defense);
        newCard.setAttribute(attribute);
        return cardRepo.save(newCard);
    }
}
