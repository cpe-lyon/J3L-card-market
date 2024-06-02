package j3lcardmarket.atelier3.sagaorchestrator.dto;
import j3lcardmarket.atelier3.commons.models.UserCard;
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
}

