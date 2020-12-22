package game.icarus.entity;

import game.icarus.attribute.ActionType;

public class Action {
    private Piece piece;
    private Cell destination;

    public Action(Piece piece, Cell destination) {
        this.piece = piece;
        this.destination = destination;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public Cell getDestination() {
        return this.destination;
    }
}
