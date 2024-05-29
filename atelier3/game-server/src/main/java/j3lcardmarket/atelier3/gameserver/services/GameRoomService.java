package j3lcardmarket.atelier3.gameserver.services;

import j3lcardmarket.atelier3.commons.models.GameRoomEntity;
import j3lcardmarket.atelier3.gameserver.domains.GameRoom;
import j3lcardmarket.atelier3.gameserver.domains.UserCard;
import j3lcardmarket.atelier3.gameserver.dto.GameRoomDto;
import j3lcardmarket.atelier3.gameserver.dto.RoomSummaryDto;
import j3lcardmarket.atelier3.gameserver.mapper.GameRoomMapper;
import j3lcardmarket.atelier3.gameserver.repositories.GameRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameRoomService {

    @Autowired
    private GameRoomRepository gameRoomRepo;

    @Autowired
    private GameRoomMapper gameRoomMapper;

    public List<RoomSummaryDto> getRooms() {
        List<GameRoomEntity> rooms = gameRoomRepo.findAll();
        return rooms.stream().map(room -> new RoomSummaryDto().fromGameRoomEntity(room)).toList();
    }

    public GameRoomDto createRoom(String creatorSurname, String name) {
        GameRoom room = new GameRoom();
        GameRoom newRoom = room.init(creatorSurname, name);
        GameRoomEntity savedRoom = gameRoomRepo.save(gameRoomMapper.toEntity(newRoom, null));
        return gameRoomMapper.fromEntity(savedRoom);
    }

    public GameRoomDto joinAsOpponent(String opponentSurname, int roomId) {
        GameRoomEntity roomEntity = gameRoomRepo.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Room not found"));
        GameRoom room = gameRoomMapper.fromEntity(roomEntity);
        room.joinAsOpponent(opponentSurname);
        GameRoomEntity savedRoom = gameRoomRepo.save(gameRoomMapper.toEntity(room, roomEntity.getId()));
        return gameRoomMapper.fromEntity(savedRoom);
    }

    public GameRoomDto selectCard(String playerSurname, int roomId, int cardId) {
        GameRoomEntity roomEntity = gameRoomRepo.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Room not found"));
        GameRoom room = gameRoomMapper.fromEntity(roomEntity);

        UserCard cardToSelect = new UserCard();
        // TODO: get card by id

        if (room.getCreator().getSurname().equals(playerSurname)) {
            List<UserCard> cardsOwned = List.of();
            // TODO: get user cards
            room.getCreator().selectCard(cardToSelect, cardsOwned);
        } else if (room.getOpponent().getSurname().equals(playerSurname)) {
            List<UserCard> cardsOwned = List.of();
            // TODO: get user cards
            room.getOpponent().selectCard(cardToSelect, cardsOwned);
        }

        GameRoomEntity savedRoom = gameRoomRepo.save(gameRoomMapper.toEntity(room, roomEntity.getId()));
        return gameRoomMapper.fromEntity(savedRoom);
    }

    public GameRoomDto play(int roomId) {
        GameRoomEntity roomEntity = gameRoomRepo.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Room not found"));
        GameRoom room = gameRoomMapper.fromEntity(roomEntity);
        room.executeGame();
        // TODO: adapt return
        GameRoomEntity savedRoom = gameRoomRepo.save(gameRoomMapper.toEntity(room, roomEntity.getId()));
        return gameRoomMapper.fromEntity(savedRoom);
    }

    public GameRoomDto cancelRoom(String creatorSurname, int roomId) {
        GameRoomEntity roomEntity = gameRoomRepo.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Room not found"));
        GameRoom room = gameRoomMapper.fromEntity(roomEntity);
        if (!room.getCreator().getSurname().equals(creatorSurname)) {
            throw new IllegalArgumentException("Only the creator can cancel the room");
        }
        room.cancelGame();
        GameRoomEntity savedRoom = gameRoomRepo.save(gameRoomMapper.toEntity(room, roomEntity.getId()));
        return gameRoomMapper.fromEntity(savedRoom);
    }

}
