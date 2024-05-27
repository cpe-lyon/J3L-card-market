package j3lcardmarket.atelier3.userserver.dto;

import j3lcardmarket.atelier3.userserver.models.Transaction;
import lombok.Getter;

@Getter
public class TransactionDto {
    private final String userCard;

    private final String seller;

    private final String buyer;
    private final Integer price;
    private final String soldOn;

    public TransactionDto(Transaction src){
        this.userCard = src.getUserCard().toString();
        this.seller = src.getSeller().getSurname();
        this.buyer = src.getBuyer().getSurname();
        this.price = src.getPrice();
        this.soldOn = src.getSoldOn().toString();
    }
}
