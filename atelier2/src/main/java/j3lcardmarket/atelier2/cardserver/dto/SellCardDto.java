package j3lcardmarket.atelier2.cardserver.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SellCardDto {

    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price must be greater than 0")
    private int price;
}
