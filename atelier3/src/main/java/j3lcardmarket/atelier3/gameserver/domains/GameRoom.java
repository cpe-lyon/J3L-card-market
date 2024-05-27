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
        if (creator.hasNoSelectedCard()) {
            throw new IllegalArgumentException("Creator must select a card before creating a game room");
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
        if (opponent.hasNoSelectedCard()) {
            throw new IllegalArgumentException("Opponent must select a card before joining a game room");
        }

        this.opponent = opponent;
        this.state = GameRoomState.IN_PROGRESS;
    }

    public void completeGame() {
        if (this.state != GameRoomState.IN_PROGRESS) {
            throw new IllegalStateException("Game can only be completed if it is in progress");
        }

        // TODO: Implement game completion logic
        this.state = GameRoomState.COMPLETED;
    }

    public void cancelGame() {
        this.state = GameRoomState.CANCELLED;
    }
}