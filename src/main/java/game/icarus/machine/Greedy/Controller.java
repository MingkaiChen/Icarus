package game.icarus.machine.Greedy;

import java.util.ArrayList;

import game.icarus.controller.GameController;
import game.icarus.entity.Action;

public class Controller {
    public static Action Greedy(Status status) {
        double subValue = Double.NEGATIVE_INFINITY;
        Action maxAction;
        ArrayList<Action> availableActions = GameController.getAvailableActions(status.getChessBoard(),
                status.getOwner(), status.getRollResult());
        for (int i = 0; i < availableActions.size(); i++) {

        }
        return null;
    }
}
