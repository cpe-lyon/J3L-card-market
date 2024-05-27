package j3lcardmarket.atelier3.gameserver.domains;

import lombok.Getter;

@Getter
public class InGameCard extends UserCard {

        private final Integer hp;

        public InGameCard(UserCard userCard) {
            super(userCard.getId(), userCard.getName());
            this.hp = 100;
        }

}
