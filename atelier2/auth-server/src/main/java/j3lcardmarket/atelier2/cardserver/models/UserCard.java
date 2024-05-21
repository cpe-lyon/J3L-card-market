package j3lcardmarket.atelier2.cardserver.models;

import lombok.Data;

@Data
public class UserCard {
    private int id;
    private String user;
    private Card card;
    private int price;
}
