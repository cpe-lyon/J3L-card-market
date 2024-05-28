package j3lcardmarket.atelier3.gameserver.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class GameRoomTest {
    private GameRoom gameRoom;
    private User josse;
    private User leo;
    private User louis;

    @BeforeEach
    void setUp() {
        gameRoom = new GameRoom();

        Card dracofeu = new Card(1, "Dracofeu");
        Card pikachu = new Card(2, "Pikachu");

        UserCard userCardDracofeu = new UserCard(dracofeu);
        UserCard userCardPikachu = new UserCard(pikachu);

        josse = new User("Josse", List.of(userCardDracofeu, userCardPikachu));
        louis = new User("Louis", List.of(userCardPikachu));
        leo = new User("Leo", List.of(userCardDracofeu));
    }

    @Test
    void testInit() {
        // Given
        String expectedRoomName = "Room1";

        // When
        gameRoom.init(josse, expectedRoomName);

        // Then
        assertEquals(josse.getSurname(), gameRoom.getCreator().getSurname());
        assertEquals(expectedRoomName, gameRoom.getName());
        assertEquals(GameRoomState.WAITING_FOR_PLAYERS, gameRoom.getState());
    }

    @Test
    void testInitAlreadyInitialized() {
        // Given
        gameRoom.init(josse, "Room1");

        // When / Then
        assertThrows(IllegalStateException.class, () -> gameRoom.init(josse, "Room2"));
    }

    @Test
    void testJoinAsOpponent() {
        // Given
        gameRoom.init(josse, "Room1");

        // When
        gameRoom.joinAsOpponent(leo);

        // Then
        assertEquals(leo.getSurname(), gameRoom.getOpponent().getSurname());
        assertEquals(GameRoomState.READY_TO_START, gameRoom.getState());
    }

    @Test
    void testJoinAsOpponentWhenCreator() {
        // Given
        gameRoom.init(josse, "Room1");

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> gameRoom.joinAsOpponent(josse));
    }

    @Test
    void testJoinAsOpponentWhenAnOpponentAlreadyJoined() {
        // Given
        gameRoom.init(josse, "Room1");

        // When
        gameRoom.joinAsOpponent(leo);

        // Then
        assertThrows(IllegalStateException.class, () -> gameRoom.joinAsOpponent(leo));
    }

    @Test
    void testExecuteGame() {
        // Given
        gameRoom.init(josse, "Room1");
        gameRoom.joinAsOpponent(leo);
        gameRoom.getCreator().selectCard(josse.getCards().get(0));
        gameRoom.getOpponent().selectCard(leo.getCards().get(0));

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
        gameRoom.init(josse, "Room1");

        // When / Then
        assertThrows(IllegalStateException.class, () -> gameRoom.executeGame());
    }

    @Test
    void testExecuteGameNoSelectedCard() {
        // Given
        gameRoom.init(josse, "Room1");
        gameRoom.joinAsOpponent(louis);

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> gameRoom.executeGame());
    }

    @Test
    void testCancelGame() {
        // Given
        gameRoom.init(josse, "Room1");

        // When
        gameRoom.cancelGame();

        // Then
        assertEquals(GameRoomState.CANCELLED, gameRoom.getState());
    }
}