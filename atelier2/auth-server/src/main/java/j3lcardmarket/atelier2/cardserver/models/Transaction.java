package j3lcardmarket.atelier2.cardserver.models;

public class Transaction {
    private int id;
    private int cardId;
    private String seller;
    private String buyer;
    private int price;
    private String soldOn;

    public Transaction(int id, int cardId, String seller, String buyer, int price, String soldOn) {
        this.id = id;
        this.cardId = cardId;
        this.seller = seller;
        this.buyer = buyer;
        this.price = price;
        this.soldOn = soldOn;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCardId() {
        return cardId;
    }

    public String getSeller() {
        return seller;
    }

    public String getBuyer() {
        return buyer;
    }

    public Integer getPrice() {
        return price;
    }

    public String getSoldOn() {
        return soldOn;
    }
}
