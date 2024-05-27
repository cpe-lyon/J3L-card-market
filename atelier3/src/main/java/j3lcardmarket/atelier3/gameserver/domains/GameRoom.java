package j3lcardmarket.atelier3.gameserver.domains;

import j3lcardmarket.atelier3.gameserver.models.GameRoomState;
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