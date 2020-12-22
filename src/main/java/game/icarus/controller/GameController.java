package game.icarus.controller;

import game.icarus.attribute.Color;
import game.icarus.entity.*;
import game.icarus.machine.AIPlayer;
import game.icarus.map.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.*;

public class GameController {
    /**
     * In Save
     **/
    private final Player[] players;
    private final ChessBoard chessBoard;
    private int currentPlayer;
    private final Setting settings;
    private DiceResult diceResult;
    private boolean walkable = false;
    private int luckyCount = 0;
    private final HashSet<Piece> movedPieces = new HashSet<>();
    /**
     * Not in Save
     **/
    private boolean isSelected = false;
    private ArrayList<Piece> selectedPieces = new ArrayList<>();
    private final ArrayList<Cell> highlightedCells = new ArrayList<>();
    private final Dice dice;
    private boolean isGameEnded = false;
    private final ArrayList<Player> winningPlayers = new ArrayList<>();
    private final BooleanProperty isGameChanged = new SimpleBooleanProperty(false);
    private boolean isPreviousJumped = false;
    private Cell previousJumpedCell;
    private Cell endJumpCell;
    private boolean isPreviousWentShortCut = false;
    private Cell previousWentShortCutCell;
    private Cell endShortCutCell;

    public GameController(Save s) {
        this(s.getSettings());
        int[] playerCount = {0, 0, 0, 0};
        for (Save.SavedPiece savedPiece : s.getPieces()) {
            int i = savedPiece.getColor().ordinal();
            Piece p = players[i].getPieces()[playerCount[i]];
            p.setPieceID(savedPiece.getUuid());
            for (UUID id : s.getMovedPiecesUUID()) {
                if (p.getPieceID().equals(id)) {
                    movedPieces.add(p);
                }
            }
            Cell position;
            switch (savedPiece.getCellType()) {
                case Normal:
                    position = chessBoard.getNormalPath().getCell(savedPiece.getIndex());
                    break;
                case ParkingApron:
                    position = chessBoard.getParkingApronByPlayer(players[i]).getCell(savedPiece.getIndex());
                    break;
                case Takeoff:
                    position = chessBoard.getTakeoffs()[i].getCell(savedPiece.getIndex());
                    break;
                case TerminalPath:
                    position = chessBoard.getTerminalPath(players[i]).getCell(savedPiece.getIndex());
                    break;
                default:
                    position = chessBoard.getParkingApronByPlayer(players[i]).getAvailableCell();
            }
            p.move(position);
            playerCount[i]++;
        }
        currentPlayer = s.getCurrentPlayer();
        diceResult = s.getDiceResult();
        walkable = s.isWalkable();
        luckyCount = s.getLuckyCount();
    }

    public GameController(Setting settings) {
        this.settings = settings;
        players = new Player[4];
        for (int i = 0; i < 4; i++) {
            if (settings.isPlayerAI(i)) players[i] = new AIPlayer(Color.values()[i]);
            else players[i] = new Player(Color.values()[i]);
        }
        chessBoard = new ChessBoard(players);
        ChessBoard.initialize(chessBoard);
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
        if (isSelected())
            for (Cell c : getHighlightedCells()) {
                if (cell.equals(c)) {
                    movePiece(cell);
                    return true;
                }
            }
        if (cell.isOccupied()) {
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
        highlightedCells.addAll(getHighlightedCells(pieces.get(0), diceResult));
        isGameChanged.setValue(Boolean.TRUE);
        return true;
    }

    public void cancelSelection() {
        int count = 0;
        for (Player p : players) {
            if (p.isWin()) count++;
        }
        if (count == settings.getPlayerNumber() - 1) {
            isGameEnded = true;
            return;
        }
        selectedPieces = new ArrayList<>();
        isSelected = false;
        highlightedCells.clear();
        isGameChanged.setValue(Boolean.TRUE);
    }

    private void finishOneTurn() {
        walkable = false;
        cancelSelection();
    }

    public boolean checkBattle(Cell pos) {
        if (pos.isOccupied() && pos.getOccupied().get(0).getColor() != selectedPieces.get(0).getColor()) {
            if (settings.isBattle()) {
                ArrayList<Piece> removed1 = new ArrayList<>();
                for (Piece p : selectedPieces) {
                    ArrayList<Piece> removed2 = new ArrayList<>();
                    for (Piece pp : pos.getOccupied()) {
                        int result1 = dice.rollOnce();
                        int result2 = dice.rollOnce();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText(String.format("Result1: %d\nResult2: %d", result1, result2));
                        alert.showAndWait();
                        if (result1 > result2) {
                            removed2.add(pp);
                        } else {
                            removed1.add(p);
                            break;
                        }
                    }
                    for (Piece pp : removed2) {
                        pos.getOccupied().remove(pp);
                        pp.move(chessBoard.getParkingApronByPlayer(pp.getOwner()).getAvailableCell());
                    }
                }
                for (Piece p : removed1) {
                    selectedPieces.remove(p);
                    p.move(chessBoard.getParkingApronByPlayer(p.getOwner()).getAvailableCell());
                }
                return selectedPieces.size() == 0;
            } else {
                ArrayList<Piece> tmp = new ArrayList<>(pos.getOccupied());
                for (Piece p : tmp){
                    p.move(chessBoard.getParkingApronByPlayer(p.getOwner()).getAvailableCell());
                }

            }
        }
        return false;
    }

    public void movePiece(Cell newPos) {
        if (isPreviousWentShortCut && newPos.equals(endShortCutCell)) {
            isPreviousWentShortCut = false;
            if (checkBattle(previousWentShortCutCell)) {
                nextPlayer();
                finishOneTurn();
                return;
            }
        }
        if (isPreviousJumped && newPos.equals(endJumpCell)) {
            isPreviousJumped = false;
            if (checkBattle(previousJumpedCell)) {
                nextPlayer();
                finishOneTurn();
                return;
            }
        }
        if (checkBattle(newPos)) {
            nextPlayer();
            finishOneTurn();
            return;
        }
        while (newPos.isOccupied() && newPos.getOccupied().get(0).getColor() == selectedPieces.get(0).getColor() && !newPos.equals(ChessBoard.getEndCell(chessBoard, selectedPieces.get(0).getOwner()))) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you going to stack?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult().equals(ButtonType.NO)) newPos = newPos.nextCell();
            else break;
        }
        if (diceResult.isLucky()) {
            if (luckyCount < 2) {
                luckyCount++;
                movedPieces.addAll(selectedPieces);
                System.out.println(movedPieces.size());
            } else {
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
        finishOneTurn();
    }

    public Save saveGame(String name) {
        Save s;
        ArrayList<Piece> pieces = new ArrayList<>();
        for (Player p : players) {
            pieces.addAll(Arrays.asList(p.getPieces()));
        }
        s = new Save(name, pieces, currentPlayer, settings, diceResult, walkable, luckyCount, movedPieces);
        return s;
    }

    public void nextPlayer() {
        int count = 0;
        for (Player p : players) {
            if (p.isWin()) count++;
        }
        if (count == settings.getPlayerNumber() - 1) {
            isGameEnded = true;
            return;
        }
        currentPlayer = (currentPlayer + 1) % settings.getPlayerNumber();
        if (getCurrentPlayer().isWin()) nextPlayer();
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public ArrayList<Cell> getHighlightedCells() {
        return highlightedCells;
    }

    public HashSet<Cell> getHighlightedCells(Piece piece, DiceResult result) {
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
                            previousWentShortCutCell = highlightedCell.nextCell();
                            endShortCutCell = highlightedCell.nextCell(13);
                            isPreviousWentShortCut = true;
                        } else if (highlightedCell.nextCell().getColor().equals(piece.getColor())
                                && !highlightedCell.nextCell().equals(piece.getOwner().getToTerminalPath())) {
                            highlightedCells.add(highlightedCell.nextCell(5));
                            previousJumpedCell = highlightedCell.nextCell();
                            endJumpCell = highlightedCell.nextCell(5);
                            isPreviousJumped = true;
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
                                                        DiceResult result) {
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
        isGameChanged.setValue(Boolean.FALSE);
    }

    public int getLuckyCount() {
        return luckyCount;
    }
}