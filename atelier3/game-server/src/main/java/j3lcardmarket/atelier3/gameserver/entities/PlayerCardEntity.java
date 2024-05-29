package j3lcardmarket.atelier3.gameserver.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "player_card")
public class PlayerCardEntity {
    @Id
    private Integer userCardId;

    @Id
    private String userSurname;

    private String cardName;

    private Integer energy;
}
