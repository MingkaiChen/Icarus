package game.icarus.entity;

import game.icarus.attribute.Color;

public class Piece {
    private Color color;
    private int pos = -1;
    private Boolean isOut = false;
    private Boolean isWin = false;

    public Piece(Color color) {
        this.color = color;
    }

    public Boolean isMovable() {
        return (isOut && !isWin);
    }

    public Boolean move(int newPos) {
        if (!isMovable())
            return false;
        pos = newPos;
        return true;
    }

    public void win() {
        isWin = true;
    }

    public Color getColor() {
        return this.color;
    }

    public int getPos() {
        return pos;
    }
}
