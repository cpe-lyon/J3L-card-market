package j3lcardmarket.atelier3.cardserver.repositories;

import j3lcardmarket.atelier3.cardserver.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {}