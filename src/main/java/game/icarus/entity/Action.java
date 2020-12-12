package game.icarus.entity;

import game.icarus.attribute.ActionType;

public class Action {
    private Piece piece;
    private Cell destination;
    private ActionType type;

    public Action(Piece piece, Cell destination, ActionType type){
        this.piece = piece;
        this.destination = destination;
        this.type = type;
    }
}
