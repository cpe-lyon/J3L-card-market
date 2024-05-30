package j3lcardmarket.atelier3.gameserver.domains;

import lombok.Getter;

@Getter
public class InGameCard extends UserCard {

        private Integer hp;

        public InGameCard(Integer userCardId, String cardName) {
            super(userCardId, cardName);
            this.hp = 100;
        }

        public void looseAllHp() {
            this.hp = 0;
        }

        public void looseHp(Integer hp) {
            this.hp -= hp;
        }

}
