package j3lcardmarket.atelier2.cardserver.dto;

import j3lcardmarket.atelier2.cardserver.models.Transaction;
import j3lcardmarket.atelier2.cardserver.models.UserCard;
import j3lcardmarket.atelier2.cardserver.models.UserIdentifier;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TransactionDTO {
    private final String userCard;

    private final String seller;

    private final String buyer;
    private final Integer price;
    private final String soldOn;

    public TransactionDTO(Transaction src){
        this.userCard = src.getUserCard().toString();
        this.seller = src.getSeller().getSurname();
        this.buyer = src.getBuyer().getSurname();
        this.price = src.getPrice();
        this.soldOn = src.getSoldOn().toString();
    }
}
