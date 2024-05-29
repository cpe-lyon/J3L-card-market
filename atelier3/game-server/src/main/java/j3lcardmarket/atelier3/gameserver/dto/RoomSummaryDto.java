package j3lcardmarket.atelier3.gameserver.dto;

import j3lcardmarket.atelier3.commons.models.GameRoomEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomSummaryDto {
    private String name;
    private Integer playersCount;

    public RoomSummaryDto() {}

    public RoomSummaryDto fromGameRoomEntity(GameRoomEntity room) {
        int playersCount = room.getOpponent() == null ? 1 : 2;
        return new RoomSummaryDto(room.getName(), playersCount);
    }
}
