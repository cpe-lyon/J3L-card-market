package j3lcardmarket.atelier2.cardserver.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_card")
public class UserCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Card cardId;

    @ManyToOne
    private UserIdentifier owner;

    private Integer price;
}
