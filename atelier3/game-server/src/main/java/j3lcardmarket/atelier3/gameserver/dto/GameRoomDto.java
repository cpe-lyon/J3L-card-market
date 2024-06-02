package j3lcardmarket.atelier3.gameserver.dto;

import j3lcardmarket.atelier3.gameserver.domains.GameRoom;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GameRoomDto extends GameRoom {
    private Integer id;
}
