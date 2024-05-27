package j3lcardmarket.atelier3.cardserver.dto;

import j3lcardmarket.atelier3.cardserver.models.Card;
import lombok.Getter;

@Getter
public class CardDto {
    private final int id;
    private final String name;

    public CardDto(Card card){
        this.id = card.getId();
        this.name = card.getName();
    }
}
