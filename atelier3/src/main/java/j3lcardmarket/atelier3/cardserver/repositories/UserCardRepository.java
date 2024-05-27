package j3lcardmarket.atelier3.cardserver.repositories;

import j3lcardmarket.atelier3.commons.models.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCardRepository extends JpaRepository<UserCard, Integer> {
    List<UserCard> findAllByOwnerSurname(String ownerSurname);
    List<UserCard> findAllByPriceIsNotNullAndOwnerSurnameIsNot(String ownerSurname);
}