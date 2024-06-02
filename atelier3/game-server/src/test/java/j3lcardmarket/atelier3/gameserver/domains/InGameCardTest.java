package j3lcardmarket.atelier3.gameserver.domains;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InGameCardTest {

    @Test
    public void testInGameCardCreation() {
        // Given
        InGameCard inGameCard = new InGameCard(1, "Dracofeu");

        // Then
        assertEquals(inGameCard.getId(), 1);
        assertEquals(inGameCard.getName(), "Dracofeu");
        assertEquals(inGameCard.getHp(), 100);
    }

}