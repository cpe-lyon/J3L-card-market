package j3lcardmarket.atelier3.gameserver.domains;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player jossePlayer;
    private UserCard userCardDracofeu;
    private UserCard userCardPikachu;

    @BeforeEach
    void setUp() {
        Card dracofeu = new Card(1, "Dracofeu");
        Card pikachu = new Card(2, "Pikachu");

        userCardDracofeu = new UserCard(dracofeu);
        userCardPikachu = new UserCard(pikachu);

        User josse = new User("Josse", List.of(userCardDracofeu));
        jossePlayer = new Player(josse);
    }

    @Test
    void testSelectCard() {
        // When
        jossePlayer.selectCard(userCardDracofeu);

        // Then
        assertEquals(userCardDracofeu.getId(), jossePlayer.getSelectedCard().getId());
    }

    @Test
    void testSelectCardNotOwned() {
        // When / Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> jossePlayer.selectCard(userCardPikachu));
        assertEquals("Player does not own this card", exception.getMessage());
    }

    @Test
    void testHasNoSelectedCard() {
        // Then
        assertTrue(jossePlayer.hasNoSelectedCard());

        // When
        jossePlayer.selectCard(userCardDracofeu);

        // Then
        assertFalse(jossePlayer.hasNoSelectedCard());
    }
}