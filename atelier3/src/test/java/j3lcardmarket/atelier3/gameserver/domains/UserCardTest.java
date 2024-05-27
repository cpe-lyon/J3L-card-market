package j3lcardmarket.atelier3.gameserver.domains;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserCardTest {

    private UserCard dracofeu;

    @BeforeEach
    public void setUp() {
        Card card = new Card(1, "Dracofeu");
        dracofeu = new UserCard(card);
    }

    @Test
    public void testUserCardCreation() {
        // Then
        assertEquals(dracofeu.getEnergy(), 100);
    }

    @Test
    public void testLooseEnergy() {
        // When
        dracofeu.looseEnergy(40);

        // Then
        assertEquals(dracofeu.getEnergy(), 60);
    }

    @Test
    public void testResetEnergy() {
        // When
        dracofeu.looseEnergy(40);
        dracofeu.resetEnergy();

        // Then
        assertEquals(dracofeu.getEnergy(), 100);
    }

}