package j3lcardmarket.atelier3.usercardserver.repositories;

import j3lcardmarket.atelier3.commons.models.Card;
import j3lcardmarket.atelier3.commons.models.UserCard;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CardReferenceRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Card getReference(Integer id) {
        return entityManager.getReference(Card.class, id);
    }
}