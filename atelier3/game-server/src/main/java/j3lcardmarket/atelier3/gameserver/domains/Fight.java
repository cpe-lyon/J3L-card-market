package j3lcardmarket.atelier3.gameserver.domains;

public class Fight {
    private final Player player1;
    private final Player player2;

    public Fight(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void execute() {
        Player winner = Math.random() > 0.5 ? player1 : player2;
        Player looser = winner == player1 ? player2 : player1;

        looser.getSelectedCard().looseAllHp();
        looser.getSelectedCard().looseEnergy(25);

        int winnerLostHp = (int) (Math.random() * 10);
        winner.getSelectedCard().looseHp(winnerLostHp);
        winner.getSelectedCard().looseEnergy(10);
    }
}
