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
    private final ArrayList<Cell> highlightedCells;
    private ArrayList<Piece> selectedPieces;
    private final Dice dice;
    private int currentPlayer;
    private final Setting settings;
    private Map<String, Object> diceResult;
    private boolean walkable = false;
    private boolean isGameEnded = false;
    private boolean canTakeOff = false;

    public GameController(Save s) {
        settings = s.getSetting();
        players = s.getPlayers();
        chessBoard = new ChessBoard(players);
        //FIXME: add cell details
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
                pieces[i*4+j] = new Piece(players[i]);
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
    public boolean selectPiece(Cell cell) {
        ArrayList<Piece> pieces = cell.getOccupied();
        if (!pieces.get(0).isOut()) {
            //highlightedCells.add(pieces.get(0).getOwner().);
        }
        if (walkable && pieces.get(0).isMovable() && pieces.get(0).getColor().equals(players[currentPlayer].getColor())) {
            selectedPieces = pieces;
            for (int i : (HashSet<Integer>)diceResult.get("result")) {
                //highlightedCells.add();
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
    public void movePieceOut() {
        //selectedPieces.get(0).move();
    }

    public boolean movePiece(Cell newPos) {
        if (newPos.isOccupied() && newPos.getOccupied().get(0).getColor() != selectedPieces.get(0).getColor()) {
            if (settings.isBattle()) {
                ArrayList<Piece> removed1 = new ArrayList<>();
                for (Piece p : selectedPieces) {
                    ArrayList<Piece> removed2 = new ArrayList<>();
                    for (Piece pp : newPos.getOccupied()) {
                        if (dice.rollOnce() > dice.rollOnce()) {
                            removed2.add(pp);
                        } else {
                            removed1.add(p);
                            break;
                        }
                    }
                    for (Piece pp : removed2) {
                        selectedPieces.remove(pp);
                        pp.returnParking();
                    }
                }
                for (Piece p : removed1) {
                    selectedPieces.remove(p);
                    p.returnParking();
                }
                if (selectedPieces.size() == 0) {
                    nextPlayer();
                    return true;
                }
            } else {
                for (Piece p : newPos.getOccupied()) p.returnParking();
            }
        }
        for (Piece p : selectedPieces) p.move(newPos);
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
        selectedPieces = null;
        highlightedCells.clear();
        diceResult.clear();
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public ArrayList<Cell> getHighlightedCells() {
        return highlightedCells;
    }

    public boolean hasGameEnded() {
        return isGameEnded;
    }
}