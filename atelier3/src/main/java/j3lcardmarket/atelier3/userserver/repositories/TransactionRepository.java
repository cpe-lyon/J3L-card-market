package j3lcardmarket.atelier3.userserver.repositories;

import j3lcardmarket.atelier3.commons.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {}