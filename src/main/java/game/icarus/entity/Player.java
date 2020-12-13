package game.icarus.entity;

import java.util.UUID;

import game.icarus.attribute.Color;
import game.icarus.map.ChessBoard;
import game.icarus.map.ParkingApron;

public class Player {
    private final UUID playerID;
    private final Color playerColor;
    private final Piece[] playerPieces;
    private Cell toTerminalPath;
    private Cell toShortcut;
    private Cell end;

    public Player(Color color) {
        // toShortcut = null;
        // toTerminalPath = null;
        // end = null;
        this.playerID = UUID.randomUUID();
        this.playerColor = color;
        this.playerPieces = new Piece[4];
        for (int i = 0; i < this.playerPieces.length; i++)
            this.playerPieces[i] = new Piece(this);
    }

    public boolean setCells(ChessBoard chessBoard) {
        switch (this.playerColor) {
            case Yellow:
                this.toShortcut = chessBoard.getNormalPath().getCell(9);
                this.toTerminalPath = chessBoard.getNormalPath().getCell(41);
                this.end = ChessBoard.getEndCell(chessBoard, this);
        }
        return false;
    }

    public Player(Player anotherPlayer){
        this.playerID = anotherPlayer.playerID;
        this.playerColor = anotherPlayer.playerColor;
        this.playerPieces = anotherPlayer.playerPieces.clone();
        this.toTerminalPath = anotherPlayer.toTerminalPath;
        this.toShortcut = anotherPlayer.toShortcut;
        this.end = anotherPlayer.end;
    }

    public static boolean initParkingApron(Player player, ParkingApron parkingApron) {
        for (int i = 0; i < player.playerPieces.length; i++)
            player.getPieces()[i].move(parkingApron.cells[i]);
        return true;
    }

    public UUID getID() {
        return this.playerID;
    }

    public Piece[] getPieces() {
        return this.playerPieces;
    }

    public Color getColor() {
        return this.playerColor;
    }

    public Cell getToTerminalPath() {
        return this.toTerminalPath;
    }

    public Cell getEnd() {
        return this.end;
    }
}
