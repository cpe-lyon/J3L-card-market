package j3lcardmarket.atelier2.cardserver.services;

import j3lcardmarket.atelier2.cardserver.models.Card;
import j3lcardmarket.atelier2.cardserver.models.Transaction;
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
    CardRepository cardRepo;
    @Autowired
    UserCardRepository userCardRepo;
    @Autowired
    TransactionRepository transactionRepo;
    @Autowired
    UserIdentifierRepository userRepo;

    public List<Card> getAll() {
        return cardRepo.findAll();
    }

    public Card getById(Integer id) {
        Optional<Card> card = cardRepo.findById(id);
        return card.orElse(null);
    }

    @Transactional
    public Card create(String cardName) {
        Card newCard = new Card();
        newCard.setName(cardName);
        return cardRepo.save(newCard);
    }

    @Transactional
    public UserCard buy(Integer userCardId, String buyerSurname) {
        UserCard userCard = userCardRepo.findById(userCardId);
        if (userCard == null) throw new IllegalArgumentException("User card not found");
        if (userCard.getPrice() == null) throw new IllegalArgumentException("User card not for sale");
        if (userRepo.exists(buyerSurname)) throw new IllegalArgumentException("Buyer not found");

        Transaction transaction = new Transaction();
        transaction.setUserCard(userCard);
        transaction.setBuyer(buyerSurname);
        transaction.setSeller(userCard.getOwner());
        transaction.setPrice(userCard.getPrice());
        transaction.setSoldOn(LocalDate.now());
        transactionRepo.save(transaction);

        userCard.setOwner(buyerSurname);
        userCard.setPrice(null);
        return userCardRepo.save(userCard);
    }

    @Transactional
    public UserCard sell(Integer userCardId, Integer price) {
        UserCard userCard = userCardRepo.findById(userCardId);
        if (userCard == null) throw new IllegalArgumentException("User card not found");

        userCard.setPrice(price);

        return userCardRepo.save(userCard);
    }
}
