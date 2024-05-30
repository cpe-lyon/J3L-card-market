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
        userCardDracofeu = new UserCard(1, "Dracofeu");
        userCardPikachu = new UserCard(2, "Pikachu");

        jossePlayer = new Player("Josse");
    }

    @Test
    void testSelectCard() {
        // When
        jossePlayer.selectCard(userCardDracofeu, List.of(userCardDracofeu, userCardPikachu));

        // Then
        assertEquals(userCardDracofeu.getId(), jossePlayer.getSelectedCard().getId());
    }

    @Test
    void testSelectCardNotOwned() {
        // When / Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> jossePlayer.selectCard(userCardPikachu, List.of(userCardDracofeu)));
        assertEquals("Player does not own this card", exception.getMessage());
    }

    @Test
    void testHasNoSelectedCard() {
        // Then
        assertTrue(jossePlayer.hasNoSelectedCard());

        // When
        jossePlayer.selectCard(userCardDracofeu, List.of(userCardDracofeu, userCardPikachu));

        // Then
        assertFalse(jossePlayer.hasNoSelectedCard());
    }

    @Test
    void testSelectCardWithoutEnergy() {
        // Given
        userCardDracofeu.looseEnergy(100);

        // When / Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> jossePlayer.selectCard(userCardDracofeu, List.of(userCardDracofeu, userCardPikachu)));
        assertEquals("Selected card has no energy", exception.getMessage());
    }
}