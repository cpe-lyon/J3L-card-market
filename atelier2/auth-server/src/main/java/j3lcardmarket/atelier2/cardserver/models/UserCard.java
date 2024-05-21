package j3lcardmarket.atelier2.cardserver.models;

import lombok.Data;

@Data
public class UserCard {
    private Integer id;
    private String owner;
    private Card card;
    private Integer price;
}
