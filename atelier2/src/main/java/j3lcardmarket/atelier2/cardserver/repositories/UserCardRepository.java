package j3lcardmarket.atelier2.cardserver.repositories;

import j3lcardmarket.atelier2.cardserver.models.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCardRepository extends JpaRepository<UserCard, Integer> {
    List<UserCard> findAllByOwnerSurnameAndPriceIsNull(String ownerSurname);
    List<UserCard> findAllByPriceIsNotNull();
}