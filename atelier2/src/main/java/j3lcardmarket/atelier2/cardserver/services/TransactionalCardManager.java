package j3lcardmarket.atelier2.cardserver.services;

import j3lcardmarket.atelier2.authserver.models.User;
import j3lcardmarket.atelier2.cardserver.models.Card;
import j3lcardmarket.atelier2.cardserver.models.Transaction;
import j3lcardmarket.atelier2.cardserver.models.UserIdentifier;
import j3lcardmarket.atelier2.cardserver.models.UserCard;
import j3lcardmarket.atelier2.cardserver.repositories.CardRepository;
import j3lcardmarket.atelier2.cardserver.repositories.TransactionRepository;
import j3lcardmarket.atelier2.cardserver.repositories.UserCardRepository;
import j3lcardmarket.atelier2.cardserver.repositories.UserIdentifierRepository;
import j3lcardmarket.atelier2.commons.utils.ForbiddenException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionalCardManager {

    @Autowired
    private CardRepository cardRepo;

    @Autowired
    private UserCardRepository userCardRepo;

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private UserIdentifierRepository userRepo;

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
        UserIdentifier creator = userRepo.getReferenceById(creatorSurname);

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
        card.setOwner(userRepo.getReferenceById(creatorSurname));
        card.setPrice(0);
        return userCardRepo.save(card);
    }

    @Transactional
    public Transaction buy(Integer userCardId, String buyerSurname) {
        Optional<UserCard> userCardOpt = userCardRepo.findById(userCardId);
        if (userCardOpt.isEmpty()) {
            throw new IllegalArgumentException("User card not found");
        }

        UserCard userCard = userCardOpt.get();

        if (userCard.getPrice() == null) {
            throw new IllegalArgumentException("User card not for sale");
        }

        if (!userRepo.existsById(buyerSurname)) {
            throw new IllegalArgumentException("Buyer not found");
        }

        Transaction transaction = new Transaction();
        transaction.setUserCard(userCard);
        transaction.setSeller(userCard.getOwner());
        transaction.setPrice(userCard.getPrice());
        transaction.setSoldOn(LocalDate.now());

        UserIdentifier buyer = userRepo.getReferenceById(buyerSurname);
        buyer.setSurname(buyerSurname);
        transaction.setBuyer(buyer);

        userCard.setOwner(buyer);
        userCard.setPrice(null);

        userCardRepo.save(userCard);
        return transactionRepo.save(transaction);
    }

    @Transactional
    public UserCard sell(Integer userCardId, Integer price, String seller) {
        Optional<UserCard> userCardOpt = userCardRepo.findById(userCardId);
        if (userCardOpt.isEmpty()) {
            throw new IllegalArgumentException("User card not found");
        }

        UserCard userCard = userCardOpt.get();
        if(!userCard.getOwner().getSurname().equals(seller))
            throw new ForbiddenException();
        userCard.setPrice(price);

        return userCardRepo.save(userCard);
    }

    public void giveFiveRandomCards(UserIdentifier userId){
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

    public List<Transaction> getTransactions(){
        return transactionRepo.findAll();
    }
}