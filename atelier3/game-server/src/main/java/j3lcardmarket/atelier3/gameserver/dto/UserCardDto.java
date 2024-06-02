package j3lcardmarket.atelier3.gameserver.dto;

import j3lcardmarket.atelier3.commons.models.UserCard;
import lombok.Getter;

@Getter
public class UserCardDto {
    private final Integer id;
    private final Integer cardId;
    private final String owner;
    private final Integer price;

    public UserCardDto(UserCard ucard) {
        this.id = ucard.getId();
        this.cardId = ucard.getCard().getId();
        this.owner = ucard.getOwner().getSurname();
        this.price = ucard.getPrice();
    }
}