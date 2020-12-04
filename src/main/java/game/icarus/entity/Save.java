package game.icarus.entity;

import com.alibaba.fastjson.annotation.JSONField;
import game.icarus.map.ChessBoard;

import java.util.Map;

public class Save {
    private Piece[] pieces;
    private Player[] players;
    private int currentPlayer;
    private Map<String, Object> diceResult;
    private boolean hasDiceResult = true;
    public Save(Piece[] pieces, Player[] p, int cp) {
        this(pieces, p, cp, null);
        this.hasDiceResult = false;
    }
    public Save(Piece[] pieces, Player[] players, int currentPlayer, Map<String, Object> diceResult) {
        this.pieces = pieces;
        this.players = players;
        this.currentPlayer = currentPlayer;
        this.diceResult = diceResult;
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
}
