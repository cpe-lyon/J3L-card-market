package j3lcardmarket.atelier3.gameserver.mapper;

import j3lcardmarket.atelier3.gameserver.domains.GameRoom;
import j3lcardmarket.atelier3.gameserver.domains.InGameCard;
import j3lcardmarket.atelier3.gameserver.domains.Player;
import j3lcardmarket.atelier3.gameserver.dto.GameRoomDto;
import j3lcardmarket.atelier3.gameserver.entities.GameRoomEntity;
import j3lcardmarket.atelier3.gameserver.entities.PlayerCardEntity;
import org.springframework.stereotype.Component;

@Component
public class GameRoomMapper {

    public GameRoomEntity toEntity(GameRoom gameRoom, Integer id) {
        GameRoomEntity gameRoomEntity = new GameRoomEntity();

        if (id != null) {
            gameRoomEntity.setId(id);
        }

        gameRoomEntity.setName(gameRoom.getName());
        gameRoomEntity.setState(gameRoom.getState());
        gameRoomEntity.setCreator(gameRoom.getCreator().getSurname());
        gameRoomEntity.setOpponent(gameRoom.getOpponent().getSurname());

        PlayerCardEntity creatorCardEntity = new PlayerCardEntity();
        creatorCardEntity.setEnergy(gameRoom.getCreator().getSelectedCard().getEnergy());
        creatorCardEntity.setUserCardId(gameRoom.getCreator().getSelectedCard().getId());
        creatorCardEntity.setUserSurname(gameRoom.getCreator().getSurname());
        creatorCardEntity.setCardName(gameRoom.getCreator().getSelectedCard().getName());

        PlayerCardEntity opponentCardEntity = new PlayerCardEntity();
        opponentCardEntity.setEnergy(gameRoom.getOpponent().getSelectedCard().getEnergy());
        opponentCardEntity.setUserCardId(gameRoom.getOpponent().getSelectedCard().getId());
        opponentCardEntity.setUserSurname(gameRoom.getOpponent().getSurname());
        opponentCardEntity.setCardName(gameRoom.getOpponent().getSelectedCard().getName());

        gameRoomEntity.setCreatorCard(creatorCardEntity);
        gameRoomEntity.setOpponentCard(opponentCardEntity);

        return gameRoomEntity;
    }

    public GameRoomDto fromEntity(GameRoomEntity gameRoomEntity) {
        GameRoomDto gameRoom = new GameRoomDto();
        gameRoom.setId(gameRoomEntity.getId());
        gameRoom.setName(gameRoomEntity.getName());
        gameRoom.setState(gameRoomEntity.getState());

        Player creator = new Player(gameRoomEntity.getCreator());
        if (gameRoomEntity.getCreatorCard() != null) {
            InGameCard creatorCard = new InGameCard(gameRoomEntity.getCreatorCard().getUserCardId(), gameRoomEntity.getCreatorCard().getCardName());
            creator.setSelectedCard(creatorCard);
        }
        gameRoom.setCreator(creator);

        if (gameRoomEntity.getOpponent() != null) {
            Player opponent = new Player(gameRoomEntity.getOpponent());
            if (gameRoomEntity.getOpponentCard() != null) {
                InGameCard opponentCard = new InGameCard(gameRoomEntity.getOpponentCard().getUserCardId(), gameRoomEntity.getOpponentCard().getCardName());
                opponent.setSelectedCard(opponentCard);
            }
            gameRoom.setOpponent(opponent);
        }

        return gameRoom;
    }
}
