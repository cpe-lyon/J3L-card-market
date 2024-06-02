package j3lcardmarket.atelier3.cardserver.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCardDto {

    @NotEmpty(message = "Name is required")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    private String name;
    private String imageUrl;
    private int level;
    private int defense;
    private int attack;
    private String element;
}
