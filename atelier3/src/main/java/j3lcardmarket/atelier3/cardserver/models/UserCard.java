package j3lcardmarket.atelier3.cardserver.models;

import j3lcardmarket.atelier3.commons.models.UserIdentifier;
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
    private Card card;

    @ManyToOne
    private UserIdentifier owner;

    private Integer price;

    public String toString(){
        return String.format("[%d] %s",id,  card.getName());
    }
}
