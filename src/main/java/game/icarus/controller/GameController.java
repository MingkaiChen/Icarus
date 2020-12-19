package game.icarus.controller;

import game.icarus.attribute.Color;
import game.icarus.entity.*;
import game.icarus.map.*;

import java.lang.reflect.Array;
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
    private boolean isSelected = false;
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
        // FIXME: add cell details
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
        ChessBoard.initialize(chessBoard);
        pieces = new Piece[playerNumber * 4];
        for (int i = 0; i < playerNumber; i++) {
            for (int j = 0; j < 4; j++) {
                pieces[i * 4 + j] = new Piece(players[i]);
            }
        }
        highlightedCells = new ArrayList<>();
        dice = new Dice();
    }

    public void rollDice() {
        // if (walkable) return;
        diceResult = dice.roll();
        walkable = true;
    }

    @SuppressWarnings("unchecked")
    public boolean selectPiece(Cell cell) {
        highlightedCells.clear();
        ArrayList<Piece> pieces = cell.getOccupied();
        if (pieces.get(0).getColor() != players[currentPlayer].getColor())
            return false;
        selectedPieces = pieces;
        isSelected = true;
        highlightedCells.addAll(getHighlightedCells(chessBoard, pieces.get(0), diceResult));
        return true;
        /*
         * if (walkable && pieces.get(0).isMovable() &&
         * pieces.get(0).getColor().equals(players[currentPlayer].getColor())) {
         * selectedPieces = pieces; for (int i :
         * (HashSet<Integer>)diceResult.get("result")) { //highlightedCells.add(); } }
         */
    }

    private boolean isWin() {
        for (int i = 0; i < 4; i++) {
            if (!pieces[currentPlayer * 4 + i].hasWin())
                return false;
        }
        return true;
    }

    public void movePieceOut() {
        // selectedPieces.get(0).move();
    }

    public void movePiece(Cell newPos) {
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
                    return;
                }
            } else {
                for (Piece p : newPos.getOccupied())
                    p.returnParking();
            }
        }
        ArrayList<Piece> tmp = new ArrayList<>(selectedPieces);
        for (Piece p : tmp) {
            p.move(newPos);
            if (!p.isOut())
                p.out();
        }
        if (isWin()) {
            isGameEnded = true;
            return;
        }
        //nextPlayer();
        walkable = false;
    }

    public Save saveGame(String name) {
        Save s;
        if (walkable)
            s = new Save(name, pieces, players, currentPlayer, diceResult);
        else
            s = new Save(name, pieces, players, currentPlayer);
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

    public static HashSet<Cell> getHighlightedCells(ChessBoard chessBoard, Piece piece, Map<String, Object> result) {
        HashSet<Cell> highlightedCells = new HashSet<Cell>();
        if (!piece.isOut()) {
            if ((boolean) result.get("canTakeOff")) {
                highlightedCells.add(ChessBoard.getTakeoffCell(chessBoard, piece.getOwner()));
                return highlightedCells;
            } else
                return null;
        } else {
            for (int i = 0; i < ((ArrayList<Integer>) result.get("result")).size(); i++) {
                Cell highlightedCell = piece.getPosition();
                for (int j = 0; j < ((ArrayList<Integer>) result.get("result")).get(i); j++) {
                    if (highlightedCell.equals(piece.getOwner().getToTerminalPath())) {
                        TerminalPath terminalPath = chessBoard.getTerminalPath(piece.getOwner());
                        highlightedCell = terminalPath.getCell(0);
                        int remain = ((ArrayList<Integer>) result.get("result")).get(i) - j;
                        if (remain > 6) {
                            highlightedCell = terminalPath.getCell(6 - (remain - 6));
                            break;
                        } else {
                            highlightedCell = terminalPath.getCell(remain);
                            break;
                        }
                    } else if (j == ((ArrayList<Integer>) result.get("result")).get(i) - 1) {
                        if (highlightedCell.nextCell().equals(piece.getOwner().getToShortcut())) {
                            highlightedCells.add(highlightedCell.nextCell(13));
                        } else if (highlightedCell.nextCell().getColor().equals(piece.getColor())
                                && !highlightedCell.nextCell().equals(piece.getOwner().getToTerminalPath())) {
                            highlightedCells.add(highlightedCell.nextCell(5));
                        }
                        continue;
                    }
                    highlightedCell = highlightedCell.nextCell();
                }
                highlightedCells.add(highlightedCell);
            }
        }
        return highlightedCells;
    }

    public static ArrayList<Action> getAvailableActions(ChessBoard chessBoard, Player player,
            Map<String, Object> result) {
        ArrayList<Action> availableActions = new ArrayList<>();

        // FIX ME ASAP!!!

        // for (int i = 0; i < player.getPieces().length; i++) {
        // ArrayList<Cell> availableCells = new ArrayList<Cell>();
        // node.getGameController().selectPiece(node.getOwner().getPieces()[i].getPosition());
        // availableCells.addAll(node.getGameController().getHighlightedCells());
        // for (int j = 0; j < availableCells.size(); j++) {
        // if (availableCells.get(i)
        // .equals(ChessBoard.getTakeoffCell(node.getGameController().getChessBoard(),
        // node.getOwner()))) {
        // availableActions
        // .add(new Action(node.getOwner().getPieces()[i], availableCells.get(j),
        // ActionType.Takeoff));
        // } else if (availableCells.get(i)
        // .equals(ChessBoard.getEndCell(node.getGameController().getChessBoard(),
        // node.getOwner()))) {
        // availableActions
        // .add(new Action(node.getOwner().getPieces()[i], availableCells.get(j),
        // ActionType.Win));
        // } else {
        // availableActions
        // .add(new Action(node.getOwner().getPieces()[i], availableCells.get(j),
        // ActionType.NormalMove));
        // }
        // }
        // }

        return availableActions;
    }

    public Map<String, Object> getDiceResult() {
        return diceResult;
    }

    public boolean hasGameEnded() {
        return isGameEnded;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public Player getCurrentPlayer() {
        return players[currentPlayer];
    }
}