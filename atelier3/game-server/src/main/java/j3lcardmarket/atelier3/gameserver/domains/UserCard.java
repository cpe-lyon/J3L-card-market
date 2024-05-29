package j3lcardmarket.atelier3.gameserver.domains;

import lombok.Data;
import lombok.Getter;

@Data
public class UserCard {

        private final Integer id;
        private final String name;
        private Integer energy;

        public UserCard() {
            this.id = 0;
            this.name = "";
            this.energy = 100;
        }

        public UserCard(Integer userCardId, String cardName) {
            this.id = userCardId;
            this.name = cardName;
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
