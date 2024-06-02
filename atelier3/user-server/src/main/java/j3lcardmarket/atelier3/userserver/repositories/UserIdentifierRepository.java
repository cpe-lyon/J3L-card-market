package j3lcardmarket.atelier3.userserver.repositories;

import j3lcardmarket.atelier3.commons.models.UserIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIdentifierRepository extends JpaRepository<UserIdentifier, String> {
    boolean existsBySurname (String surname);
}