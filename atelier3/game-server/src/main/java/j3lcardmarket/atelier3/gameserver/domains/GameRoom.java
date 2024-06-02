package j3lcardmarket.atelier3.gameserver.domains;

import j3lcardmarket.atelier3.commons.models.GameRoomState;
import lombok.Data;

@Data
public class GameRoom {

    private GameRoomState state;
    private Player creator;
    private Player opponent;
    private String name;
    private String winnerSurname;

    public GameRoom() {
        this.state = GameRoomState.INITIAL;
    }

    public GameRoom init(String creatorSurname, String roomName) {
        if (this.state != GameRoomState.INITIAL) {
            throw new IllegalStateException("Game room already initialized");
        }

        this.creator = new Player(creatorSurname);
        this.name = roomName;
        this.state = GameRoomState.WAITING_FOR_PLAYERS;

        return this;
    }

    public void joinAsOpponent(String opponentSurname) {
        if (this.state != GameRoomState.WAITING_FOR_PLAYERS) {
            throw new IllegalStateException("Cannot join as opponent in current state: " + this.state);
        }
        if (this.opponent != null) {
            throw new IllegalStateException("An opponent has already joined this game room");
        }
        if (opponentSurname.equals(this.creator.getSurname())) {
            throw new IllegalArgumentException("Cannot join a game room as opponent if you are the creator");
        }

        this.opponent = new Player(opponentSurname);
        this.state = GameRoomState.READY_TO_START;
    }

    public void executeGame() {
        if (this.state != GameRoomState.READY_TO_START) {
            throw new IllegalStateException("Game can only be started if it is ready to start");
        }
        if (creator.hasNoSelectedCard() || opponent.hasNoSelectedCard()) {
            throw new IllegalArgumentException("Player must select a card before starting the game");
        }

        Fight fight = new Fight(creator, opponent);
        fight.execute();

        if (this.creator.getSelectedCard().getHp() == 0) {
            this.winnerSurname = this.opponent.getSurname();
        } else {
            this.winnerSurname = this.creator.getSurname();
        }

        this.state = GameRoomState.COMPLETED;
    }

    public void cancelGame() {
        this.state = GameRoomState.CANCELLED;
    }
}