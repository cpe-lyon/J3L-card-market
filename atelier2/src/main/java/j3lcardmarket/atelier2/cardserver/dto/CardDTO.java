package j3lcardmarket.atelier2.cardserver.dto;

import j3lcardmarket.atelier2.cardserver.models.Card;
import j3lcardmarket.atelier2.cardserver.models.UserCard;
import lombok.Getter;

@Getter
public class CardDTO {
    private final int id;
    private final String name;

    public CardDTO(Card card){
        this.id = card.getId();
        this.name = card.getName();
    }
}
