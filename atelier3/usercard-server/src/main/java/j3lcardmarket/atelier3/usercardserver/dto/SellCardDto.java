package j3lcardmarket.atelier3.usercardserver.dto;

import lombok.Data;

@Data
public class SellCardDto {
    private Integer price;

    public SellCardDto(int price) {
        this.price = price;
    }
}
