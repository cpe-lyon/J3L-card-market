package j3lcardmarket.atelier3.userserver.services;

import j3lcardmarket.atelier3.userserver.models.Transaction;
import j3lcardmarket.atelier3.cardserver.models.UserCard;
import j3lcardmarket.atelier3.commons.models.UserIdentifier;
import j3lcardmarket.atelier3.userserver.repositories.TransactionRepository;
import j3lcardmarket.atelier3.cardserver.repositories.UserCardRepository;
import j3lcardmarket.atelier3.userserver.repositories.UserCardReferenceRepository;
import j3lcardmarket.atelier3.userserver.repositories.UserIdentifierRepository;
import j3lcardmarket.atelier3.commons.utils.ForbiddenException;
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
    private UserCardReferenceRepository userCardRepo;

    @Autowired
    private UserIdentifierRepository userRepo;

    @Transactional
    public Transaction buy(Integer userCardId, String buyerSurname, String ownerSurname, Integer price) {
        UserCard userCard = userCardRepo.getReference(userCardId);

        if (price == null) {
            throw new IllegalArgumentException("User card not for sale");
        }

        Optional<UserIdentifier> buyerOpt = userRepo.findById(buyerSurname);
        UserIdentifier buyer = buyerOpt.orElseThrow(() -> new IllegalArgumentException("Buyer not found"));

        Optional<UserIdentifier> ownerOpt = userRepo.findById(ownerSurname);
        UserIdentifier owner = ownerOpt.orElseThrow(() -> new IllegalArgumentException("Owner not found"));

        if (price > buyer.getBalance()) {
            throw new IllegalArgumentException("Buyer does not have enough balance");
        }

        Transaction transaction = new Transaction();
        transaction.setUserCard(userCard);
        transaction.setSeller(owner);
        transaction.setPrice(price);
        transaction.setSoldOn(LocalDate.now());
        transaction.setBuyer(buyer);

        buyer.setBalance(buyer.getBalance() - price);
        owner.setBalance(owner.getBalance() + price);

        return transactionRepo.save(transaction);
    }

    public List<Transaction> getTransactions(){
        return transactionRepo.findAll();
    }
}