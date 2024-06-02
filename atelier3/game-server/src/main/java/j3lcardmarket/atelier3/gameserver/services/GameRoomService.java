package j3lcardmarket.atelier3.gameserver.services;

import j3lcardmarket.atelier3.commons.models.GameRoomEntity;
import j3lcardmarket.atelier3.commons.models.GameRoomState;
import j3lcardmarket.atelier3.gameserver.domains.GameRoom;
import j3lcardmarket.atelier3.gameserver.domains.UserCard;
import j3lcardmarket.atelier3.gameserver.dto.GameRoomDto;
import j3lcardmarket.atelier3.gameserver.dto.RoomSummaryDto;
import j3lcardmarket.atelier3.gameserver.mapper.GameRoomMapper;
import j3lcardmarket.atelier3.gameserver.repositories.GameRoomRepository;
import j3lcardmarket.atelier3.gameserver.repositories.PlayerCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameRoomService {

    @Autowired
    private GameRoomRepository gameRoomRepo;

    @Autowired
    private PlayerCardRepository playerCardRepo;

    @Autowired
    private GameRoomMapper gameRoomMapper;

    public List<RoomSummaryDto> getRooms() {
        List<GameRoomEntity> rooms = gameRoomRepo.findAllByStateIsNotOrStateIsNot(GameRoomState.CANCELLED, GameRoomState.COMPLETED);
        return rooms.stream().map(room -> new RoomSummaryDto().fromGameRoomEntity(room)).toList();
    }

    public GameRoomDto getRoom(int roomId) {
        GameRoomEntity roomEntity = gameRoomRepo.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Room not found"));
        return gameRoomMapper.fromEntity(roomEntity);
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

    public GameRoomDto selectCard(String playerSurname, int roomId, int userCardId) {
        GameRoomEntity roomEntity = gameRoomRepo.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Room not found"));
        GameRoom room = gameRoomMapper.fromEntity(roomEntity);

        UserCard cardToSelect = new UserCard(userCardId, "card");

        if (room.getCreator().getSurname().equals(playerSurname)) {
            room.getCreator().selectCard(cardToSelect);
        } else if (room.getOpponent().getSurname().equals(playerSurname)) {
            room.getOpponent().selectCard(cardToSelect);
        } else {
            throw new IllegalArgumentException("Player not found in room");
        }

        GameRoomEntity roomToSave = gameRoomMapper.toEntity(room, roomEntity.getId());
        playerCardRepo.save(roomToSave.getCreatorCard());
        if (roomToSave.getOpponentCard() != null) {
            playerCardRepo.save(roomToSave.getOpponentCard());
        }
        GameRoomEntity savedRoom = gameRoomRepo.save(roomToSave);
        return gameRoomMapper.fromEntity(savedRoom);
    }

    public GameRoomDto play(int roomId) {
        GameRoomEntity roomEntity = gameRoomRepo.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Room not found"));
        GameRoom room = gameRoomMapper.fromEntity(roomEntity);
        room.executeGame();
        GameRoomEntity roomToSave = gameRoomMapper.toEntity(room, roomEntity.getId());
        playerCardRepo.save(roomToSave.getCreatorCard());
        if (roomToSave.getOpponentCard() != null) {
            playerCardRepo.save(roomToSave.getOpponentCard());
        }
        GameRoomEntity savedRoom = gameRoomRepo.save(roomToSave);
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
