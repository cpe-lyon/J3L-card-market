package j3lcardmarket.atelier3.gameserver.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class GameRoomTest {
    private GameRoom gameRoom;
    private UserCard dracofeu;
    private UserCard pikachu;

    @BeforeEach
    void setUp() {
        gameRoom = new GameRoom();

        dracofeu = new UserCard(1, "Dracofeu");
        pikachu = new UserCard(2, "Pikachu");
    }

    @Test
    void testInit() {
        // Given
        String expectedRoomName = "Room1";

        // When
        gameRoom.init("Josse", expectedRoomName);

        // Then
        assertEquals("Josse", gameRoom.getCreator().getSurname());
        assertEquals(expectedRoomName, gameRoom.getName());
        assertEquals(GameRoomState.WAITING_FOR_PLAYERS, gameRoom.getState());
    }

    @Test
    void testInitAlreadyInitialized() {
        // Given
        gameRoom.init("Josse", "Room1");

        // When / Then
        assertThrows(IllegalStateException.class, () -> gameRoom.init("Josse", "Room2"));
    }

    @Test
    void testJoinAsOpponent() {
        // Given
        gameRoom.init("Josse", "Room1");

        // When
        gameRoom.joinAsOpponent("Leo");

        // Then
        assertEquals("Leo", gameRoom.getOpponent().getSurname());
        assertEquals(GameRoomState.READY_TO_START, gameRoom.getState());
    }

    @Test
    void testJoinAsOpponentWhenCreator() {
        // Given
        gameRoom.init("Josse", "Room1");

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> gameRoom.joinAsOpponent("Josse"));
    }

    @Test
    void testJoinAsOpponentWhenAnOpponentAlreadyJoined() {
        // Given
        gameRoom.init("Josse", "Room1");

        // When
        gameRoom.joinAsOpponent("Leo");

        // Then
        assertThrows(IllegalStateException.class, () -> gameRoom.joinAsOpponent("Leo"));
    }

    @Test
    void testExecuteGame() {
        // Given
        gameRoom.init("Josse", "Room1");
        gameRoom.joinAsOpponent("Leo");
        gameRoom.getCreator().selectCard(dracofeu, List.of(dracofeu, pikachu));
        gameRoom.getOpponent().selectCard(pikachu, List.of(dracofeu, pikachu));

        // When
        gameRoom.executeGame();

        // Then
        assertTrue(gameRoom.getCreator().getSelectedCard().getHp() == 0 || gameRoom.getOpponent().getSelectedCard().getHp() == 0);

        Player winner = gameRoom.getCreator().getSelectedCard().getHp() == 0 ? gameRoom.getOpponent() : gameRoom.getCreator();
        Player looser = gameRoom.getCreator().getSelectedCard().getHp() == 0 ? gameRoom.getCreator() : gameRoom.getOpponent();

        assertNotEquals(winner.getSelectedCard().getHp(), 0);
        assertEquals(looser.getSelectedCard().getHp(), 0);

        assertEquals(winner.getSelectedCard().getEnergy(), 90);
        assertEquals(looser.getSelectedCard().getEnergy(), 75);

        assertEquals(GameRoomState.COMPLETED, gameRoom.getState());
    }

    @Test
    void testExecuteGameNotReady() {
        // Given
        gameRoom.init("Josse", "Room1");

        // When / Then
        assertThrows(IllegalStateException.class, () -> gameRoom.executeGame());
    }

    @Test
    void testExecuteGameNoSelectedCard() {
        // Given
        gameRoom.init("Josse", "Room1");
        gameRoom.joinAsOpponent("Louis");

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> gameRoom.executeGame());
    }

    @Test
    void testCancelGame() {
        // Given
        gameRoom.init("Josse", "Room1");

        // When
        gameRoom.cancelGame();

        // Then
        assertEquals(GameRoomState.CANCELLED, gameRoom.getState());
    }
}