package j3lcardmarket.atelier2.cardserver.repositories;

import j3lcardmarket.atelier2.cardserver.database.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIdentifierRepository extends JpaRepository<UserEntity, String> {
    boolean existsById (String surname);
}