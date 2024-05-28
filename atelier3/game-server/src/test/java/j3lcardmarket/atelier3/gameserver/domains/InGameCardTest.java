package j3lcardmarket.atelier3.gameserver.domains;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InGameCardTest {

    @Test
    public void testInGameCardCreation() {
        // Given
        Card card = new Card(1, "Dracofeu");
        UserCard userCard = new UserCard(card);
        InGameCard inGameCard = new InGameCard(userCard);

        // Then
        assertEquals(userCard.getId(), inGameCard.getId());
        assertEquals(inGameCard.getHp(), 100);
    }

}