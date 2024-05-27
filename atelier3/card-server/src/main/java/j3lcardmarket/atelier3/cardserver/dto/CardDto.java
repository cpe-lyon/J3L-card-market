package j3lcardmarket.atelier3.cardserver.dto;

import j3lcardmarket.atelier3.commons.models.Card;
import lombok.Getter;

@Getter
public class CardDto {
    private final int id;
    private final String name;
    private final String imageUrl;
    private final int level;
    private final int defense;
    private final int attack;
    private final String attribute;

    public CardDto(Card card){
        this.id = card.getId();
        this.name = card.getName();
        this.imageUrl = card.getImageUrl();
        this.level = card.getLevel();
        this.defense = card.getDefense();
        this.attack = card.getAttack();
        this.attribute = card.getAttribute();
    }
}
