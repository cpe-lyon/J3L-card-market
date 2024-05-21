package j3lcardmarket.atelier2.cardserver.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Transaction {
    private int id;
    private Card card;
    private String seller;
    private String buyer;
    private int price;
    private LocalDate soldOn;
}
