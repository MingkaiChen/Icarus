package game.icarus.entity;

import game.icarus.attribute.Color;

public class Piece {
    private final Color color;
    private int pos = -1;
    private Boolean isOut = false;
    private Boolean isWin = false;

    public Piece(Color color) {
        this.color = color;
    }

    public Boolean isMovable() {
        return (isOut && !isWin);
    }

    public void out(int newPos) {
        if (!isOut) {
            pos = newPos;
            isOut = true;
        }
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
    public boolean hasWin() { return isWin; }
}
