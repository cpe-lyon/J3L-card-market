package j3lcardmarket.atelier3.commons.models;

import j3lcardmarket.atelier3.commons.models.Card;
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

    public UserCard clone(){
        UserCard copy = new UserCard();
        copy.setId(id);
        copy.setCard(card);
        copy.setPrice(price);
        copy.setOwner(owner);
        return copy;
    }
}
