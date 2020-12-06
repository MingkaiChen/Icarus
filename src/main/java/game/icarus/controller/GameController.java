package game.icarus.controller;

import game.icarus.attribute.Color;
import game.icarus.entity.*;
import game.icarus.map.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

/* process:
    1. start game by init GameController
    2. player 1 roll dice
    3. player 1 select piece by selectPiece()
    4. player 1 select pos to move
    5. next player

 */


public class GameController {
    private final Player[] players;
    private final ChessBoard chessBoard;
    private final Piece[] pieces;
    private final ArrayList<Integer> highlightedCells;
    private Piece selectedPiece;
    private final Dice dice;
    private int currentPlayer;
    private final Setting settings;
    private Map<String, Object> diceResult;
    private boolean walkable = false;
    private boolean isGameEnded = false;

    public GameController(Save s) {
        settings = s.getSetting();
        players = s.getPlayers();
        chessBoard = new ChessBoard(players);
        pieces = s.getPieces();
        currentPlayer = s.getCurrentPlayer();
        walkable = s.getHasDiceResult();
        if (walkable) {
            diceResult = s.getDiceResult();
        }
        highlightedCells = new ArrayList<>();
        dice = new Dice();
    }

    public GameController(Setting settings) {
        this.settings = settings;
        int playerNumber = settings.getPlayerNumber();
        players = new Player[playerNumber];
        for (int i = 0; i < playerNumber; i++) {
            players[i] = new Player(Color.values()[i]);
        }
        chessBoard = new ChessBoard(players);
        pieces = new Piece[playerNumber * 4];
        for (int i = 0; i < playerNumber; i++) {
            for (int j = 0; j < 4; j++) {
                pieces[i*4+j] = new Piece(Color.values()[i]);
            }
        }
        highlightedCells = new ArrayList<>();
        dice = new Dice();
    }

    public void rollDice() {
        if (walkable) return;
        diceResult = dice.roll();
        walkable = true;
    }

    @SuppressWarnings("unchecked")
    public boolean selectPiece(Piece piece) {
        if (walkable && piece.isMovable() && piece.getColor().equals(players[currentPlayer].getColor())) {
            selectedPiece = piece;
            for (int i : (HashSet<Integer>)diceResult.get("result")) {
                highlightedCells.add(piece.getPos() + i);
            }
            return true;
        }
        return false;
    }

    private boolean isWin() {
        for (int i = 0; i < 4; i++) {
            if (!pieces[currentPlayer*4+i].hasWin()) return false;
        }
        return true;
    }

    public boolean movePiece(int newPos) {
        selectedPiece.move(newPos);
        if (isWin()) {
            isGameEnded = true;
            return true;
        }
        nextPlayer();
        return true;
    }

    public Save saveGame(String name) {
        Save s;
        if (walkable) s = new Save(name, pieces, players, currentPlayer, diceResult);
        else s = new Save(name, pieces, players, currentPlayer);
        return s;
    }

    public void nextPlayer() {
        currentPlayer = (currentPlayer + 1) % settings.getPlayerNumber();
        selectedPiece = null;
        highlightedCells.clear();
        diceResult.clear();
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public ArrayList<Integer> getHighlightedCells() {
        return highlightedCells;
    }

    public boolean hasGameEnded() {
        return isGameEnded;
    }
}