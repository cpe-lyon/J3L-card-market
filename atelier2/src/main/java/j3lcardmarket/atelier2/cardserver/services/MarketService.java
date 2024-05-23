package j3lcardmarket.atelier2.cardserver.services;

import j3lcardmarket.atelier2.cardserver.models.Transaction;
import j3lcardmarket.atelier2.cardserver.models.UserCard;
import j3lcardmarket.atelier2.cardserver.models.UserIdentifier;
import j3lcardmarket.atelier2.cardserver.repositories.TransactionRepository;
import j3lcardmarket.atelier2.cardserver.repositories.UserCardRepository;
import j3lcardmarket.atelier2.cardserver.repositories.UserIdentifierRepository;
import j3lcardmarket.atelier2.commons.utils.ForbiddenException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MarketService {

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private UserCardRepository userCardRepo;

    @Autowired
    private UserIdentifierRepository userRepo;

    @Transactional
    public Transaction buy(Integer userCardId, String buyerSurname) {
        Optional<UserCard> userCardOpt = userCardRepo.findById(userCardId);
        UserCard userCard = userCardOpt.orElseThrow(() -> new IllegalArgumentException("User card not found"));

        if (userCard.getPrice() == null) {
            throw new IllegalArgumentException("User card not for sale");
        }

        if (userCard.getOwner().getSurname().equals(buyerSurname)) {
            throw new IllegalArgumentException("User card already owned by buyer");
        }

        Optional<UserIdentifier> buyerOpt = userRepo.findById(buyerSurname);
        UserIdentifier buyer = buyerOpt.orElseThrow(() -> new IllegalArgumentException("Buyer not found"));

        if (userCard.getPrice() > buyer.getBalance()) {
            throw new IllegalArgumentException("Buyer does not have enough balance");
        }

        Transaction transaction = new Transaction();
        transaction.setUserCard(userCard);
        transaction.setSeller(userCard.getOwner());
        transaction.setPrice(userCard.getPrice());
        transaction.setSoldOn(LocalDate.now());
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
    
    public List<Transaction> getTransactions() {
        return transactionRepo.findAll();
    }
}