package j3lcardmarket.atelier3.gameserver.domains;

import lombok.Getter;

@Getter
public class UserCard extends Card {

        private Integer energy;

        public UserCard(Integer cardId, String cardName) {
            super(cardId, cardName);
            this.energy = 100;
        }

        public UserCard(Card card) {
            super(card.getId(), card.getName());
            this.energy = 100;
        }

        public void looseEnergy(Integer energy) {
            this.energy -= energy;

            if (this.energy < 0) {
                this.energy = 0;
            }
        }

        public void resetEnergy() {
            this.energy = 100;
        }
}
