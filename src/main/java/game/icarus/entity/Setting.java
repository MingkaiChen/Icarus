package game.icarus.entity;

public class Setting {
    private int playerNumber;
    private int diceNumber;
    public Setting() {
        playerNumber = 4;
        diceNumber = 2;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        if (playerNumber > 8) throw new IllegalArgumentException("Maximum player number is 8.");
        if (playerNumber < 2) throw new IllegalArgumentException("Minimum player number is 2.");
        this.playerNumber = playerNumber;
    }
}
