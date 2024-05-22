package j3lcardmarket.atelier2.cardserver.services;

import j3lcardmarket.atelier2.cardserver.models.Card;
import j3lcardmarket.atelier2.cardserver.models.Transaction;
import j3lcardmarket.atelier2.cardserver.models.UserIdentifier;
import j3lcardmarket.atelier2.cardserver.models.UserCard;
import j3lcardmarket.atelier2.cardserver.repositories.CardRepository;
import j3lcardmarket.atelier2.cardserver.repositories.TransactionRepository;
import j3lcardmarket.atelier2.cardserver.repositories.UserCardRepository;
import j3lcardmarket.atelier2.cardserver.repositories.UserIdentifierRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        return userCardRepo.findAllByOwnerSurnameAndPriceIsNull(ownerSurname);
    }

    public List<UserCard> getAllOnSale() {
        return userCardRepo.findAllByPriceIsNotNull();
    }

    @Transactional
    public UserCard create(String cardName, String creatorSurname) {
        UserIdentifier creator = new UserIdentifier();
        creator.setSurname(creatorSurname);

        Card newCard = new Card();
        newCard.setName(cardName);
        newCard.setCreator(creator);
        Card createdCard = cardRepo.save(newCard);

        UserCard userCard = new UserCard();
        userCard.setCard(createdCard);
        userCard.setOwner(creator);
        return userCardRepo.save(userCard);
    }

    @Transactional
    public UserCard buy(Integer userCardId, String buyerSurname) {
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

        UserIdentifier buyer = new UserIdentifier();
        buyer.setSurname(buyerSurname);
        transaction.setBuyer(buyer);

        userCard.setOwner(buyer);
        userCard.setPrice(null);

        transactionRepo.save(transaction);
        return userCardRepo.save(userCard);
    }

    @Transactional
    public UserCard sell(Integer userCardId, Integer price) {
        Optional<UserCard> userCardOpt = userCardRepo.findById(userCardId);
        if (userCardOpt.isEmpty()) {
            throw new IllegalArgumentException("User card not found");
        }

        UserCard userCard = userCardOpt.get();
        userCard.setPrice(price);

        return userCardRepo.save(userCard);
    }
}