package game.icarus.entity;

import game.icarus.attribute.Color;
import game.icarus.map.ChessBoard;
import game.icarus.map.ParkingApron;

public class Piece {
    private final Player owner;
    private Cell position;
    private Boolean isOut = false;
    private Boolean isWin = false;

    public Piece(Player owner) {
        this.position = null;
        this.owner = owner;
    }

    public Piece(Piece piece) {
        this.owner = new Player(piece.owner);
        this.position = new Cell(piece.position);
        this.isOut = piece.isOut;
        this.isWin = piece.isWin;
    }

    public Boolean isMovable() {
        return (isOut && !isWin);
    }

    public void out() {
        isOut = true;
    }

    public void move(Cell destination) {
        if (this.position != null) {
            this.position.removePiece(this);
            if (this.position.getBelongsto() instanceof ParkingApron)
                isOut = true;
        }
        this.position = destination;
        destination.setOccupied(this);
        if (destination.getBelongsto() instanceof ParkingApron)
            isOut = false;
    }

    public void win() {
        isWin = true;
    }

    public Boolean isOut() {
        return isOut;
    }

    public Boolean isWin() {
        return isWin;
    }

    public Color getColor() {
        return this.owner.getColor();
    }

    public Cell getPosition() {
        return position;
    }

    public boolean hasWin() {
        return isWin;
    }

    public Player getOwner() {
        return owner;
    }
}
