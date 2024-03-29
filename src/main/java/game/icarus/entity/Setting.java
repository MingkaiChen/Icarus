package game.icarus.entity;

public class Setting {
    private int playerNumber;
    private boolean[] arePlayersAI;
    private int diceNumber;
    private boolean isBattle;
    private boolean isStack;

    public Setting() {
        playerNumber = 4;
        diceNumber = 2;
        isBattle = false;
        isStack = false;
        arePlayersAI = new boolean[]{false, false, false, false};
    }
    public Setting(int playerNumber, int diceNumber, boolean isBattle, boolean isStack) {
        this();
        this.isBattle = isBattle;
        this.isStack = isStack;
        if (playerNumber > 4) throw new IllegalArgumentException("Maximum player number is 4.");
        if (playerNumber < 2) throw new IllegalArgumentException("Minimum player number is 2.");
        this.playerNumber = playerNumber;
        this.diceNumber = diceNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getDiceNumber() { return diceNumber; }

    public void setDiceNumber(int diceNumber) {
        this.diceNumber = diceNumber;
    }

    public boolean isBattle() {
        return isBattle;
    }

    public boolean isStack() {
        return isStack;
    }

    public void setPlayerAI(int i, boolean flag) {
        this.arePlayersAI[i] = flag;
    }

    public boolean isPlayerAI(int i) {
        return this.arePlayersAI[i];
    }

    public boolean[] getArePlayersAI() {
        return arePlayersAI;
    }

    public void setArePlayersAI(boolean[] arePlayersAI) {
        this.arePlayersAI = arePlayersAI;
    }

    public void setBattle(boolean battle) {
        isBattle = battle;
    }

    public void setStack(boolean stack) {
        isStack = stack;
    }
}
