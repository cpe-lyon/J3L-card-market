package j3lcardmarket.atelier3.usercardserver.dto;

import j3lcardmarket.atelier3.commons.models.Card;
import j3lcardmarket.atelier3.commons.models.UserCard;
import j3lcardmarket.atelier3.commons.models.UserIdentifier;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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