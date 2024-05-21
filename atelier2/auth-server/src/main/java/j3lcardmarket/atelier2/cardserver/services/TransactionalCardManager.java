package j3lcardmarket.atelier2.cardserver.services;

import j3lcardmarket.atelier2.cardserver.models.Card;
import j3lcardmarket.atelier2.cardserver.repositories.DbCardManager;
import j3lcardmarket.atelier2.cardserver.repositories.DbTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionalCardManager {
    @Autowired
    DbCardManager cardRepo;
    @Autowired
    DbTransactionManager transactionRepo;

    public List<Card> getAll() {
        // TODO implement here
        return null;
    }

    public Card getById(Integer id) {
        // TODO implement here
        return null;
    }

    public Card create() {
        // TODO implement here
        return null;
    }

    public Card buy(Integer id) {
        // TODO implement here
        return null;
    }

    public Card sell(Integer id) {
        // TODO implement here
        return null;
    }
}
