package j3lcardmarket.atelier3.gameserver.domains;

import lombok.Getter;

@Getter
public class GameRoom {

    private GameRoomState state;
    private Player creator;
    private Player opponent;
    private String roomName;

    public GameRoom() {
        this.state = GameRoomState.INITIAL;
    }

    public void init(Player creator, String roomName) {
        if (this.state != GameRoomState.INITIAL) {
            throw new IllegalStateException("Game room already initialized");
        }

        this.creator = creator;
        this.roomName = roomName;
        this.state = GameRoomState.WAITING_FOR_PLAYERS;
    }

    public void joinAsOpponent(Player opponent) {
        if (this.state != GameRoomState.WAITING_FOR_PLAYERS) {
            throw new IllegalStateException("Cannot join as opponent in current state: " + this.state);
        }
        if (this.opponent != null) {
            throw new IllegalStateException("An opponent has already joined this game room");
        }
        if (opponent.equals(this.creator)) {
            throw new IllegalArgumentException("Cannot join a game room as opponent if you are the creator");
        }

        this.opponent = opponent;
        this.state = GameRoomState.READY_TO_START;
    }

    public void startGame() {
        if (this.state != GameRoomState.READY_TO_START) {
            throw new IllegalStateException("Game can only be started if it is ready to start");
        }
        if (creator.hasNoSelectedCard() || opponent.hasNoSelectedCard()) {
            throw new IllegalArgumentException("Player must select a card before starting the game");
        }

        this.state = GameRoomState.IN_PROGRESS;
    }

    public void cancelGame() {
        this.state = GameRoomState.CANCELLED;
    }
}