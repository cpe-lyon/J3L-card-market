package j3lcardmarket.atelier3.gameserver.domains;

public enum GameRoomState {
    INITIAL,
    WAITING_FOR_PLAYERS,
    READY_TO_START,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}