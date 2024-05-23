package j3lcardmarket.atelier2.cardserver.services;

import j3lcardmarket.atelier2.cardserver.models.Card;
import j3lcardmarket.atelier2.cardserver.models.UserIdentifier;
import j3lcardmarket.atelier2.cardserver.models.UserCard;
import j3lcardmarket.atelier2.cardserver.repositories.CardRepository;
import j3lcardmarket.atelier2.cardserver.repositories.UserCardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepo;

    @Autowired
    private UserCardRepository userCardRepo;

    public List<Card> getAll() {
        return cardRepo.findAll();
    }

    public List<UserCard> getAllByOwner(String ownerSurname) {
        return userCardRepo.findAllByOwnerSurname(ownerSurname);
    }

    public List<UserCard> getPurchasableByOwner(String ownerSurname) {
        return userCardRepo.findAllByPriceIsNotNullAndOwnerSurnameIsNot(ownerSurname);
    }

    @Transactional
    public Card createCard(String cardName, String creatorSurname, String imageUrl) {
        UserIdentifier creator = new UserIdentifier();
        creator.setSurname(creatorSurname);

        Card newCard = new Card();
        newCard.setName(cardName);
        newCard.setCreator(creator);
        newCard.setImageUrl(imageUrl);
        return cardRepo.save(newCard);
    }

    @Transactional
    public UserCard createUserCard(int cardId, String creatorSurname) {
        UserCard card = new UserCard();
        card.setCard(cardRepo.getReferenceById(cardId));
        card.setOwner(new UserIdentifier(creatorSurname));
        card.setPrice(0);
        return userCardRepo.save(card);
    }

    @Transactional
    public void giveFiveRandomCards(UserIdentifier userId) {
        List<Integer> ids = cardRepo.allIds();
        Collections.shuffle(ids);
        ids = ids.subList(0, 5);
        for (Integer id : ids) {
            UserCard userCard = new UserCard();
            userCard.setCard(cardRepo.getReferenceById(id));
            userCard.setOwner(userId);
            userCardRepo.save(userCard);
        }
    }
}