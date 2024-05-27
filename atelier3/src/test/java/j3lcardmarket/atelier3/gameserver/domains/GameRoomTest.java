package j3lcardmarket.atelier3.gameserver.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class GameRoomTest {
    private GameRoom gameRoom;
    private Player jossePlayer;
    private Player leoPlayer;
    private Player notInitializedPlayer;

    @BeforeEach
    void setUp() {
        gameRoom = new GameRoom();

        Card dracofeu = new Card(1, "Dracofeu");
        Card pikachu = new Card(2, "Pikachu");

        UserCard userCardDracofeu = new UserCard(dracofeu);
        UserCard userCardPikachu = new UserCard(pikachu);

        User josse = new User("Josse", List.of(userCardDracofeu, userCardPikachu));
        User louis = new User("Louis", List.of(userCardPikachu));
        User leo = new User("Leo", List.of(userCardDracofeu));

        jossePlayer = new Player(josse);
        jossePlayer.selectCard(jossePlayer.getCards().get(0));
        leoPlayer = new Player(leo);
        leoPlayer.selectCard(leoPlayer.getCards().get(0));
        notInitializedPlayer = new Player(louis);
    }

    @Test
    void testInit() {
        // Given
        String expectedRoomName = "Room1";

        // When
        gameRoom.init(jossePlayer, expectedRoomName);

        // Then
        assertEquals(jossePlayer, gameRoom.getCreator());
        assertEquals(expectedRoomName, gameRoom.getRoomName());
        assertEquals(GameRoomState.WAITING_FOR_PLAYERS, gameRoom.getState());
    }

    @Test
    void testInitAlreadyInitialized() {
        // Given
        gameRoom.init(jossePlayer, "Room1");

        // When / Then
        assertThrows(IllegalStateException.class, () -> gameRoom.init(jossePlayer, "Room2"));
    }

    @Test
    void testJoinAsOpponent() {
        // Given
        gameRoom.init(jossePlayer, "Room1");

        // When
        gameRoom.joinAsOpponent(leoPlayer);

        // Then
        assertEquals(leoPlayer, gameRoom.getOpponent());
        assertEquals(GameRoomState.READY_TO_START, gameRoom.getState());
    }

    @Test
    void testJoinAsOpponentWhenCreator() {
        // Given
        gameRoom.init(jossePlayer, "Room1");

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> gameRoom.joinAsOpponent(jossePlayer));
    }

    @Test
    void testJoinAsOpponentWhenAnOpponentAlreadyJoined() {
        // Given
        gameRoom.init(jossePlayer, "Room1");

        // When
        gameRoom.joinAsOpponent(leoPlayer);

        // Then
        assertThrows(IllegalStateException.class, () -> gameRoom.joinAsOpponent(leoPlayer));
    }

    @Test
    void testExecuteGame() {
        // Given
        gameRoom.init(jossePlayer, "Room1");
        gameRoom.joinAsOpponent(leoPlayer);
        int creatorEnergy = gameRoom.getCreator().getSelectedCard().getEnergy();
        int opponentEnergy = gameRoom.getOpponent().getSelectedCard().getEnergy();

        // When
        gameRoom.executeGame();

        // Then
        assertTrue(gameRoom.getCreator().getSelectedCard().getHp() == 0 || gameRoom.getOpponent().getSelectedCard().getHp() == 0);

        Player winner = gameRoom.getCreator().getSelectedCard().getHp() == 0 ? gameRoom.getOpponent() : gameRoom.getCreator();
        Player looser = gameRoom.getCreator().getSelectedCard().getHp() == 0 ? gameRoom.getCreator() : gameRoom.getOpponent();

        assertNotEquals(winner.getSelectedCard().getHp(), 0);
        assertEquals(looser.getSelectedCard().getHp(), 0);

        assertEquals(winner.getSelectedCard().getEnergy(), creatorEnergy - 10);
        assertEquals(looser.getSelectedCard().getEnergy(), opponentEnergy - 25);

        assertEquals(GameRoomState.COMPLETED, gameRoom.getState());
    }

    @Test
    void testExecuteGameNotReady() {
        // Given
        gameRoom.init(jossePlayer, "Room1");

        // When / Then
        assertThrows(IllegalStateException.class, () -> gameRoom.executeGame());
    }

    @Test
    void testExecuteGameNoSelectedCard() {
        // Given
        gameRoom.init(jossePlayer, "Room1");
        gameRoom.joinAsOpponent(notInitializedPlayer);

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> gameRoom.executeGame());
    }

    @Test
    void testCancelGame() {
        // Given
        gameRoom.init(jossePlayer, "Room1");

        // When
        gameRoom.cancelGame();

        // Then
        assertEquals(GameRoomState.CANCELLED, gameRoom.getState());
    }
}