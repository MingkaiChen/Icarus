package game.icarus.entity;

import java.time.LocalDateTime;
import java.util.Map;

public class Save {
    private LocalDateTime saveTime;
    private String name;
    private Setting setting;
    private Piece[] pieces;
    private Player[] players;
    private int currentPlayer;
    private Map<String, Object> diceResult;
    private boolean hasDiceResult = true;
    private Piece selectedPiece;

    public Save(String name, Piece[] pieces, Player[] p, int cp) {
        this(name, pieces, p, cp, null);
        this.hasDiceResult = false;
    }

    public Save(String name, Piece[] pieces, Player[] players, int currentPlayer, Map<String, Object> diceResult) {
        this.pieces = pieces;
        this.players = players;
        this.currentPlayer = currentPlayer;
        this.diceResult = diceResult;
        this.saveTime = LocalDateTime.now();
    }

    public Piece[] getPieces() {
        return pieces;
    }

    public void setPieces(Piece[] pieces) {
        this.pieces = pieces;
    }

    public Map<String, Object> getDiceResult() {
        return diceResult;
    }

    public void setDiceResult(Map<String, Object> diceResult) {
        this.diceResult = diceResult;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public boolean getHasDiceResult() {
        return hasDiceResult;
    }

    public void setHasDiceResult(boolean hasDiceResult) {
        this.hasDiceResult = hasDiceResult;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public LocalDateTime getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(LocalDateTime saveTime) {
        this.saveTime = saveTime;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
