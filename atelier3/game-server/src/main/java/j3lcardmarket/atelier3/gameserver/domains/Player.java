package j3lcardmarket.atelier3.gameserver.domains;

import lombok.Data;

import java.util.List;

@Data
public class Player {

    private final String surname;
    private InGameCard selectedCard;

    public Player(String surname) {
        this.surname = surname;
    }

    public void selectCard(UserCard cardToSelect, List<UserCard> cardsOwned) {
        if (!cardsOwned.contains(cardToSelect)) {
            throw new IllegalArgumentException("Player does not own this card");
        }
        if (cardToSelect.getEnergy() <= 0) {
            throw new IllegalArgumentException("Selected card has no energy");
        }

        this.selectedCard = new InGameCard(cardToSelect.getId(), cardToSelect.getName());
    }

    public boolean hasNoSelectedCard() {
        return this.selectedCard == null;
    }

}
