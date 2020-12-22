package game.icarus.machine;

import java.time.temporal.Temporal;

import game.icarus.attribute.Color;
import game.icarus.entity.Action;
import game.icarus.entity.Cell;
import game.icarus.entity.Player;
import game.icarus.map.ChessBoard;

public class AIPlayer extends Player {

    private String algorithm;

    public AIPlayer(Color color) {
        super(color);
        this.algorithm = null;
    }

    public Action takeAction() {

    }

    private Action defaultAlgorithm(ChessBoard chessBoard, Action actions[]) {
        Action maxAction;
        int maxValue = 0;
        for (int i = 0; i < actions.length; i++) {
            if (!actions[i].getDestination().isOccupied()) {
                if (actions[i].getDestination().equals(this.getEnd()))
                    return actions[i];
                else if (!actions[i].getPiece().isOut())
                    return actions[i];
            }
        }
        for (int i = 0; i < actions.length; i++) {
            if (actions[i].getDestination().getOccupied().get(0).getOwner().equals(this))
                return actions[i];
            else if (actions[i].getDestination().getOccupied().size() == 1)
                return actions[i];
        }
        for (int i = 0; i < actions.length; i++) {
            int tempValue = 0;
            Cell tempCell = actions[i].getPiece().getPosition();
            while (true) {
                tempValue++;
                if (tempValue > maxValue) {
                    maxAction = actions[i];
                    break;
                }
                // if (tempCell.equals(actions[i].getDestination()))
                // break;
                // else if (tempCell.equals(this.getToTerminalPath()))
                // tempCell =
                // tempCell = tempCell.nextCell();
            }
        }
    }

}