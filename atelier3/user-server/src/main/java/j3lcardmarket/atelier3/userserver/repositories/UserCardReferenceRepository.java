package j3lcardmarket.atelier3.userserver.repositories;

import j3lcardmarket.atelier3.commons.models.UserCard;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserCardReferenceRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public UserCard getReference(Integer id) {
        return entityManager.getReference(UserCard.class, id);
    }
}
