package j3lcardmarket.atelier2.cardserver.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Transaction {
    private Integer id;
    private UserCard userCard;
    private String seller;
    private String buyer;
    private Integer price;
    private LocalDate soldOn;
}
