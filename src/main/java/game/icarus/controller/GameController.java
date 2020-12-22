package game.icarus.controller;

import game.icarus.attribute.Color;
import game.icarus.entity.*;
import game.icarus.map.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class GameController {
    private final Player[] players;
    private final ChessBoard chessBoard;
    private final Piece[] pieces;
    private final ArrayList<Cell> highlightedCells;
    private ArrayList<Piece> selectedPieces = new ArrayList<>();
    private boolean isSelected = false;
    private final Dice dice;
    private int currentPlayer;
    private final Setting settings;
    private DiceResult diceResult;
    private boolean walkable = false;
    private boolean isGameEnded = false;
    private int playerNumber;
    private int luckyCount = 0;
    private final HashSet<Piece> movedPieces = new HashSet<>();
    private final ArrayList<Player> winningPlayers = new ArrayList<>();
    private final BooleanProperty isGameChanged = new SimpleBooleanProperty(false);

    public GameController(Save s) {
        settings = s.getSetting();
        players = s.getPlayers();
        chessBoard = new ChessBoard(players);
        // FIXME: add cell details
        pieces = s.getPieces();
        for (Piece p : pieces) {
            p.move(p.getPosition());
        }
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
        this.playerNumber = settings.getPlayerNumber();
        players = new Player[4];
        for (int i = 0; i < 4; i++) {
            players[i] = new Player(Color.values()[i]);
        }
        chessBoard = new ChessBoard(players);
        ChessBoard.initialize(chessBoard);
        pieces = new Piece[4 * 4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                pieces[i * 4 + j] = new Piece(players[i]);
            }
        }
        highlightedCells = new ArrayList<>();
        dice = new Dice(settings.getDiceNumber());
    }

    public boolean rollDice() {
        diceResult = dice.roll();
        for (Piece p : getCurrentPlayer().getPieces()) {
            if (p.isMovable() || (!p.isOut() && diceResult.canTakeOff())) {
                walkable = true;
                return true;
            }
        }
        nextPlayer();
        return false;
    }

    public boolean cellHandler(Cell cell) {
        if (!isWalkable()) return false;
        System.out.println((isSelected) ? "" : "Not " + "Selected");
        if (isSelected())
            for (Cell c : getHighlightedCells()) {
                if (cell.equals(c)) {
                    System.out.println("MovePiece");
                    movePiece(cell);
                    return true;
                }
            }
        if (cell.isOccupied()) {
            System.out.println("SelectPiece");
            return selectPiece(cell);
        }
        cancelSelection();
        return false;
    }

    public boolean selectPiece(Cell cell) {
        highlightedCells.clear();
        ArrayList<Piece> pieces = cell.getOccupied();
        if (pieces.get(0).isWin()) return false;
        if (pieces.get(0).getColor() != players[currentPlayer].getColor())
            return false;
        selectedPieces = pieces;
        isSelected = true;
        highlightedCells.addAll(getHighlightedCells(chessBoard, pieces.get(0), diceResult));
        isGameChanged.setValue(true);
        return true;
    }

    public void cancelSelection() {
        selectedPieces = new ArrayList<>();
        isSelected = false;
        highlightedCells.clear();
        isGameChanged.setValue(true);
    }

    private void finishOneTurn() {
        walkable = false;
        cancelSelection();
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
                        newPos.getOccupied().remove(pp);
                        pp.move(chessBoard.getParkingApronByPlayer(pp.getOwner()).getAvailableCell());
                    }
                }
                for (Piece p : removed1) {
                    selectedPieces.remove(p);
                    p.move(chessBoard.getParkingApronByPlayer(p.getOwner()).getAvailableCell());
                }
                if (selectedPieces.size() == 0) {
                    nextPlayer();
                    finishOneTurn();
                    return;
                }
            } else {
                ArrayList<Piece> tmp = newPos.getOccupied();
                for (Piece p : tmp)
                    p.move(chessBoard.getParkingApronByPlayer(p.getOwner()).getAvailableCell());
            }
        }
        ArrayList<Piece> tmp = new ArrayList<>(selectedPieces);
        for (Piece p : tmp) {
            p.move(newPos);
            if (newPos.equals(ChessBoard.getEndCell(chessBoard, p.getOwner()))) {
                p.win();
                if (p.getOwner().isWin() && !winningPlayers.contains(p.getOwner())) {
                    winningPlayers.add(p.getOwner());
                }
            }
        }
        if (diceResult.isLucky()) {
            if (luckyCount < 2) {
                luckyCount++;
                movedPieces.addAll(selectedPieces);
            } else {
                //FIXME: LUCKY COUNT IS IN PRACTICE && PIECES WONT RETURN TO PARKING
                luckyCount = 0;
                for (Piece p : movedPieces)
                    p.move(chessBoard.getParkingApronByPlayer(p.getOwner()).getAvailableCell());
                movedPieces.clear();
                nextPlayer();
            }
        } else {
            luckyCount = 0;
            movedPieces.clear();
            nextPlayer();
        }
        finishOneTurn();
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
        int count = 0;
        for (Player p : players) {
            if (p.isWin()) count++;
        }
        if (count == playerNumber - 1) {
            isGameEnded = true;
            return;
        }
        currentPlayer = (currentPlayer + 1) % playerNumber;
        if (getCurrentPlayer().isWin()) nextPlayer();
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public ArrayList<Cell> getHighlightedCells() {
        return highlightedCells;
    }

    public static HashSet<Cell> getHighlightedCells(ChessBoard chessBoard, Piece piece, DiceResult result) {
        HashSet<Cell> highlightedCells = new HashSet<>();
        if (!piece.isOut()) {
            if (result.canTakeOff()) {
                highlightedCells.add(ChessBoard.getTakeoffCell(chessBoard, piece.getOwner()));
            }
            return highlightedCells;
        } else {
            for (int i = 0; i < result.getResult().size(); i++) {
                Cell highlightedCell = piece.getPosition();
                for (int j = 0; j < result.getResult().get(i); j++) {
                    if (highlightedCell.equals(piece.getOwner().getToTerminalPath())) {
                        TerminalPath terminalPath = chessBoard.getTerminalPath(piece.getOwner());
                        //highlightedCell = terminalPath.getCell(0);
                        int remain = result.getResult().get(i) - j;
                        if (remain > 6) {
                            if (remain <= 12)
                                highlightedCell = terminalPath.getCell(6 - (remain - 6));
                            else
                                highlightedCell = ChessBoard.getTakeoffCell(chessBoard, piece.getOwner()).nextCell(50 - (remain - 12));
                        } else {
                            highlightedCell = terminalPath.getCell(remain - 1);
                        }
                        break;
                    } else if (j == result.getResult().get(i) - 1) {
                        if (highlightedCell.nextCell().equals(piece.getOwner().getToShortcut())) {
                            highlightedCells.add(highlightedCell.nextCell(13));
                        } else if (highlightedCell.nextCell().getColor().equals(piece.getColor())
                                && !highlightedCell.nextCell().equals(piece.getOwner().getToTerminalPath())) {
                            highlightedCells.add(highlightedCell.nextCell(5));
                        }
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

    public DiceResult getDiceResult() {
        return diceResult;
    }

    public boolean isGameEnded() {
        if (isGameEnded) {
            for (Player p : players) {
                if (!winningPlayers.contains(p)) {
                    winningPlayers.add(p);
                    break;
                }
            }
        }
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

    public Dice getDice() {
        return dice;
    }

    public ArrayList<Player> getWinningPlayers() {
        return winningPlayers;
    }

    public Player[] getPlayers() {
        return players;
    }

    public BooleanProperty isGameChanged() {
        return isGameChanged;
    }

    public void finishHandling() {
        isGameChanged.setValue(false);
    }
}