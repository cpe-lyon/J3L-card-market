package j3lcardmarket.atelier3.gameserver.domains;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void testInitWhenCreatorHasNoSelectedCard() {
        // When / Then
        assertThrows(IllegalArgumentException.class, () -> gameRoom.init(notInitializedPlayer, "Room1"));
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
        assertEquals(GameRoomState.IN_PROGRESS, gameRoom.getState());
    }

    @Test
    void testJoinAsOpponentInvalidState() {
        // When / Then
        assertThrows(IllegalStateException.class, () -> gameRoom.joinAsOpponent(notInitializedPlayer));
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
    void testCancelGame() {
        // Given
        gameRoom.init(jossePlayer, "Room1");

        // When
        gameRoom.cancelGame();

        // Then
        assertEquals(GameRoomState.CANCELLED, gameRoom.getState());
    }

    @Test
    void testCancelGameFromInProgress() {
        // Given
        gameRoom.init(jossePlayer, "Room1");
        gameRoom.joinAsOpponent(leoPlayer);

        // When
        gameRoom.cancelGame();

        // Then
        assertEquals(GameRoomState.CANCELLED, gameRoom.getState());
    }
}