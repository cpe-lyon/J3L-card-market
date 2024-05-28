package j3lcardmarket.atelier3.gameserver.services;

import j3lcardmarket.atelier3.gameserver.domains.GameRoom;
import j3lcardmarket.atelier3.gameserver.domains.User;
import j3lcardmarket.atelier3.gameserver.dto.RoomDto;
import j3lcardmarket.atelier3.gameserver.dto.RoomSummaryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameRoomService {

    public List<RoomSummaryDto> getRooms() {
        //TODO: get from db
        return List.of();
    }

    public RoomSummaryDto createRoom(String creatorSurname, String name) {
        GameRoom room = new GameRoom();

        // TODO: remove and get from db
        User creator = new User(creatorSurname, List.of());

        room.init(creator, name);
        return new RoomSummaryDto().fromGameRoom(room);
    }

    public RoomSummaryDto joinAsOpponent(String opponentSurname, int roomId) {
        // TODO
        return null;
    }

    public RoomDto play(int roomId) {
        // TODO
        return null;
    }

    public void cancelRoom(String creatorSurname, int roomId) {
        // TODO
    }

}
