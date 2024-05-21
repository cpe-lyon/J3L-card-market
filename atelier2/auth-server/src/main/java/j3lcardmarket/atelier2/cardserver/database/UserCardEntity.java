package j3lcardmarket.atelier2.cardserver.database;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_card")
public class UserCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Integer cardId;

    private String owner;

    private int price;
}
