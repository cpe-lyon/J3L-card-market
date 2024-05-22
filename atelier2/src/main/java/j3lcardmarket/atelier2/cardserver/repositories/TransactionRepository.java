package j3lcardmarket.atelier2.cardserver.repositories;

import j3lcardmarket.atelier2.cardserver.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {}