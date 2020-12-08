package game.icarus.entity;

import game.icarus.attribute.Color;

public class Piece {
    private final Player owner;
    private Cell position;
    private Boolean isOut = false;
    private Boolean isWin = false;

    public Piece(Player owner) {
        this.owner = owner;
    }

    public Boolean isMovable() {
        return (isOut && !isWin);
    }

    // public void out(int newPos) {
    // if (!isOut) {
    // pos = newPos;
    // isOut = true;
    // }
    // }

    // public Boolean move(int newPos) {
    // if (!isMovable())
    // return false;
    // pos = newPos;
    // return true;
    // }

    public boolean move(Cell destination) {
        this.position.clear();
        this.position = destination;
        destination.setOccipied(this);
        return true;
    }

    public void win() {
        isWin = true;
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
}
