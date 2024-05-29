package j3lcardmarket.atelier3.commons.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "player_card")
public class PlayerCardEntity implements Serializable {

    @EmbeddedId
    private PlayerCardId id;

    private String cardName;

    private Integer energy;
}

