package j3lcardmarket.atelier3.gameserver.repositories;

import j3lcardmarket.atelier3.commons.models.PlayerCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerCardRepository extends JpaRepository<PlayerCardEntity, Integer> {}
