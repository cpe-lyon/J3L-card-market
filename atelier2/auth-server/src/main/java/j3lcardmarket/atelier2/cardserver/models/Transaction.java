package j3lcardmarket.atelier2.cardserver.models;

import lombok.Data;

import java.util.Date;

@Data
public class Transaction {
    private int id;
    private Card card;
    private String seller;
    private String buyer;
    private int price;
    private Date soldOn;
}
