package j3lcardmarket.atelier3.gameserver.repositories;

import j3lcardmarket.atelier3.gameserver.entities.GameRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRoomRepository extends JpaRepository<GameRoomEntity, Integer> {}
