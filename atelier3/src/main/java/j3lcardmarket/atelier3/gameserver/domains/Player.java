package j3lcardmarket.atelier3.gameserver.domains;

import lombok.Getter;

@Getter
public class Player extends User {

    private InGameCard selectedCard;

    public Player(User user) {
        super(user.getSurname(), user.getCards());
    }

    public void selectCard(UserCard card) {
        if (!this.getCards().contains(card)) {
            throw new IllegalArgumentException("Player does not own this card");
        }
        this.selectedCard = new InGameCard(card);
    }

    public boolean hasNoSelectedCard() {
        return this.selectedCard == null;
    }

}
