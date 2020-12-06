package game.icarus.entity;

public class Setting {
    private final int playerNumber;
    private final int diceNumber;

    public Setting() {
        playerNumber = 4;
        diceNumber = 2;
    }
    public Setting(int playerNumber, int diceNumber) {
        if (playerNumber > 8) throw new IllegalArgumentException("Maximum player number is 8.");
        if (playerNumber < 2) throw new IllegalArgumentException("Minimum player number is 2.");
        this.playerNumber = playerNumber;
        this.diceNumber = diceNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getDiceNumber() { return diceNumber; }
}
