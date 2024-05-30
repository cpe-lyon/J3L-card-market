package j3lcardmarket.atelier3.usercardserver.dto;

import j3lcardmarket.atelier3.commons.models.UserCard;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class EditUserCardDto {
    private String owner;
    private Integer price;


    public EditUserCardDto(String owner, Integer price){
        this.owner = owner;
        this.price = price;
    }

    public EditUserCardDto(UserCard ucard){
        this(ucard.getOwner().getSurname(), ucard.getPrice());
    }
}
