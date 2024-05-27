package j3lcardmarket.atelier3.cardserver.repositories;

import j3lcardmarket.atelier3.commons.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
}