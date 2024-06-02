package j3lcardmarket.atelier3.gameserver.repositories;

import j3lcardmarket.atelier3.commons.models.GameRoomEntity;
import j3lcardmarket.atelier3.commons.models.GameRoomState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRoomRepository extends JpaRepository<GameRoomEntity, Integer> {
    List<GameRoomEntity> findAllByStateIsNotOrStateIsNot(GameRoomState cancel, GameRoomState completed);
}
