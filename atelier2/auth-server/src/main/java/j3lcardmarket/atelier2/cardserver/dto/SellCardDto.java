package j3lcardmarket.atelier2.cardserver.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SellCardDto {

    @NotEmpty(message = "Price is required")
    @Min(value = 1, message = "Price must be greater than 0")
    private int price;
}
