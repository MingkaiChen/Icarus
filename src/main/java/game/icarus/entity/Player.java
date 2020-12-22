package game.icarus.entity;

import java.util.ArrayList;

import game.icarus.attribute.Color;
import game.icarus.map.ChessBoard;
import game.icarus.map.ParkingApron;

public class Player {
    private final Color playerColor;
    private final Piece[] playerPieces;
    private Cell toTerminalPath;
    private Cell toShortcut;
    private Cell end;
    private boolean isMachine;

    public Color getPlayerColor() {
        return playerColor;
    }

    public Player(Color color) {
        // toShortcut = null;
        // toTerminalPath = null;
        // end = null;
        this.playerColor = color;
        this.playerPieces = new Piece[4];
        for (int i = 0; i < this.playerPieces.length; i++)
            this.playerPieces[i] = new Piece(this);
    }



    public boolean setCells(ChessBoard chessBoard) {
        // switch (this.playerColor) {
        // case Yellow:
        // this.toShortcut = chessBoard.getNormalPath().getCell(9);
        // this.toTerminalPath = chessBoard.getNormalPath().getCell(41);
        // this.end = ChessBoard.getEndCell(chessBoard, this);
        // }
        return false;
    }

    public Player(Player anotherPlayer) {
        isMachine = anotherPlayer.isMachine;
        this.playerColor = anotherPlayer.playerColor;
        ArrayList<Piece> tempPieces = new ArrayList<>();
        for (int i = 0; i < anotherPlayer.playerPieces.length; i++)
            tempPieces.add(new Piece(anotherPlayer.playerPieces[i]));
        this.playerPieces = (Piece[]) tempPieces.toArray();
        this.toTerminalPath = new Cell(anotherPlayer.toTerminalPath);
        this.toShortcut = new Cell(anotherPlayer.toShortcut);
        this.end = new Cell(anotherPlayer.end);
    }

    public static void initParkingApron(Player player, ParkingApron parkingApron) {
        for (int i = 0; i < player.playerPieces.length; i++)
            player.getPieces()[i].move(parkingApron.cells[i]);
    }

    public boolean equals(Player player) {
        return playerColor.equals(player.getColor());
    }

    public Piece[] getPieces() {
        return playerPieces;
    }

    public Color getColor() {
        return this.playerColor;
    }

    public Cell getToTerminalPath() {
        return this.toTerminalPath;
    }

    public boolean setToTerminalPath(Cell cell) {
        this.toTerminalPath = cell;
        return true;
    }

    public boolean setToShortcut(Cell cell) {
        this.toShortcut = cell;
        return true;
    }

    public boolean setEnd(Cell cell) {
        this.end = cell;
        return true;
    }

    public Cell getToShortcut() {
        return this.toShortcut;
    }

    public Cell getEnd() {
        return this.end;
    }

    public boolean isWin() {
        for (Piece p : playerPieces) {
            if (!p.isWin())
                return false;
        }
        return true;
    }

    public boolean isMachine() {
        return isMachine;
    }

    @Override
    public String toString() {
        return playerColor.toString();
    }
}
