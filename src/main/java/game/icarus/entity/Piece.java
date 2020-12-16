package game.icarus.entity;

import game.icarus.attribute.Color;

public class Piece {
    private final Player owner;
    private Cell position;
    private Boolean isOut = false;
    private Boolean isWin = false;

    public Piece(Player owner) {
        this.position = new Cell(Color.Null);
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
    public void returnParking() {
        this.position.clear();
        //FIXME: idk how to return it to parking
    }

    public boolean move(Cell destination) {
        this.position.clear();
        this.position = destination;
        destination.setOccupied(this);
        return true;
    }

    public void win() {
        isWin = true;
    }

    public Boolean isOut() {
        return isOut;
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
