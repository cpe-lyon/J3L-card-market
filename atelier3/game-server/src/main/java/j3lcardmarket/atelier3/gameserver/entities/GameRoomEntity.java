package j3lcardmarket.atelier3.gameserver.entities;

import j3lcardmarket.atelier3.gameserver.domains.GameRoomState;
import jakarta.persistence.*;
import org.springframework.context.annotation.Primary;

@Entity
@Table(name = "game_room")
public class GameRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private GameRoomState state;
    private String creator;
    private String opponent;
}
