package j3lcardmarket.atelier3.gameserver.domains;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import j3lcardmarket.atelier3.gameserver.models.GameRoomState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameRoomTest {
    private GameRoom gameRoom;
    private Player creator;
    private Player opponent;

    @BeforeEach
    void setUp() {
        gameRoom = new GameRoom();
        creator = new Player("Player1", 1);
        opponent = new Player("Player2", 2);
    }

    @Test
    void testInit() {
        // Given
        String expectedRoomName = "Room1";

        // When
        gameRoom.init(creator, expectedRoomName);

        // Then
        assertEquals(creator, gameRoom.getCreator());
        assertEquals(expectedRoomName, gameRoom.getRoomName());
        assertEquals(GameRoomState.WAITING_FOR_PLAYERS, gameRoom.getState());
    }

    @Test
    void testInitAlreadyInitialized() {
        // Given
        gameRoom.init(creator, "Room1");

        // When / Then
        assertThrows(IllegalStateException.class, () -> gameRoom.init(creator, "Room2"));
    }

    @Test
    void testJoinAsOpponent() {
        // Given
        gameRoom.init(creator, "Room1");

        // When
        gameRoom.joinAsOpponent(opponent);

        // Then
        assertEquals(opponent, gameRoom.getOpponent());
        assertEquals(GameRoomState.IN_PROGRESS, gameRoom.getState());
    }

    @Test
    void testJoinAsOpponentInvalidState() {
        // When / Then
        assertThrows(IllegalStateException.class, () -> gameRoom.joinAsOpponent(opponent));
    }

    @Test
    void testJoinAsOpponentAlreadyJoined() {
        // Given
        gameRoom.init(creator, "Room1");
        gameRoom.joinAsOpponent(opponent);

        // When / Then
        Player anotherOpponent = new Player("Player3", 3);
        assertThrows(IllegalStateException.class, () -> gameRoom.joinAsOpponent(anotherOpponent));
    }

    @Test
    void testCompleteGame() {
        // Given
        gameRoom.init(creator, "Room1");
        gameRoom.joinAsOpponent(opponent);

        // When
        gameRoom.completeGame();

        // Then
        assertEquals(GameRoomState.COMPLETED, gameRoom.getState());
    }

    @Test
    void testCompleteGameInvalidState() {
        // When / Then
        assertThrows(IllegalStateException.class, () -> gameRoom.completeGame());
    }

    @Test
    void testCancelGame() {
        // Given
        gameRoom.init(creator, "Room1");

        // When
        gameRoom.cancelGame();

        // Then
        assertEquals(GameRoomState.CANCELLED, gameRoom.getState());
    }

    @Test
    void testCancelGameFromInProgress() {
        // Given
        gameRoom.init(creator, "Room1");
        gameRoom.joinAsOpponent(opponent);

        // When
        gameRoom.cancelGame();

        // Then
        assertEquals(GameRoomState.CANCELLED, gameRoom.getState());
    }

    @Test
    void testCancelGameFromCompleted() {
        // Given
        gameRoom.init(creator, "Room1");
        gameRoom.joinAsOpponent(opponent);
        gameRoom.completeGame();

        // When
        gameRoom.cancelGame();

        // Then
        assertEquals(GameRoomState.CANCELLED, gameRoom.getState());
    }
}