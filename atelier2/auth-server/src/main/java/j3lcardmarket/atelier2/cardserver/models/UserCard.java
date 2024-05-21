package j3lcardmarket.atelier2.cardserver.models;

import lombok.Data;

@Data
public class UserCard {
    private int id;
    private int userId;
    private int cardId;
    private int price;
}
