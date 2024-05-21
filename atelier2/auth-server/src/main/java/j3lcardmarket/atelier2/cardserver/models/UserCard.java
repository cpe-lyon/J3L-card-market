package j3lcardmarket.atelier2.cardserver.models;

public class UserCard {
    private int id;
    private int userId;
    private int cardId;
    private int price;

    public UserCard(int id, int userId, int cardId, int price) {
        this.id = id;
        this.userId = userId;
        this.cardId = cardId;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public Integer getPrice() {
        return price;
    }
}
