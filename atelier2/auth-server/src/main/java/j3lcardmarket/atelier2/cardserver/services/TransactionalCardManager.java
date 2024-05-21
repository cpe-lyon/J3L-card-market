package j3lcardmarket.atelier2.cardserver.services;

import j3lcardmarket.atelier2.cardserver.repositories.DbCardManager;
import j3lcardmarket.atelier2.cardserver.repositories.DbTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionalCardManager {
    @Autowired
    DbCardManager cardRepo;
    @Autowired
    DbTransactionManager TransactionRepo;
}
