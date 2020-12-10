package game.icarus.machine.MonteCarloTreeSearch;

import game.icarus.attribute.ActionType;
import game.icarus.entity.Cell;
import game.icarus.entity.Piece;

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
