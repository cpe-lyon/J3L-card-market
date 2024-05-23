package j3lcardmarket.atelier2.cardserver.services;

import j3lcardmarket.atelier2.cardserver.models.Transaction;
import j3lcardmarket.atelier2.cardserver.models.UserCard;
import j3lcardmarket.atelier2.cardserver.models.UserIdentifier;
import j3lcardmarket.atelier2.commons.utils.ForbiddenException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MarketService {

    private final CardService cardService = new CardService();
    @Transactional
    public Transaction buy(Integer userCardId, String buyerSurname) {
        UserCard userCard = cardService.getUserCardById(userCardId);

        if (userCard.getPrice() == null) {
            throw new IllegalArgumentException("User card not for sale");
        }

        if (userCard.getOwner().getSurname().equals(buyerSurname)) {
            throw new IllegalArgumentException("User card already owned by buyer");
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

        cardService.saveUserCard(userCard);
        return transaction;
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

    public List<Transaction> getTransactions(){
        return userCardRepo.findAllTransactions();
    }
}