package j3lcardmarket.atelier2.cardserver.repositories;

import j3lcardmarket.atelier2.cardserver.models.UserIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIdentifierRepository extends JpaRepository<UserIdentifier, String> {
    boolean existsById (String surname);
}