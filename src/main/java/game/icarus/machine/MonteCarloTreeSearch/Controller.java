package game.icarus.machine.MonteCarloTreeSearch;

import java.util.ArrayList;

import game.icarus.attribute.ActionType;
import game.icarus.entity.Action;
import game.icarus.entity.Cell;
import game.icarus.entity.Player;
import game.icarus.map.ChessBoard;

public class Controller {
    public static Node initializeMonteCarloTree(ChessBoard chessBoard, Player owner) {
        return new Node(owner, chessBoard);
    }

    public static boolean nodeExpansion(Node parentNode) {
        ArrayList<Action> availableActions = getAvailabelActions(parentNode);
        for(int i = 0; i < availableActions.size(); i++){

        }
        return false;
    }

    public static Node nodeSelection(Node parentNode) {
        double tempMaxWeight = 0;
        int tempMaxIndex = -1;
        for (int i = 0; i < parentNode.getChildrenNodes().size(); i++) {
            if (parentNode.getChildrenNodes().get(i).getNValue() == 0) {
                Controller.nodeExpansion(parentNode.getChildrenNodes().get(i));
            }
            parentNode.getChildrenNodes().get(i).calculateWeight();
            if (parentNode.getChildrenNodes().get(i).getWeight() > tempMaxWeight) {
                tempMaxIndex = i;
                tempMaxWeight = parentNode.getChildrenNodes().get(i).getWeight();
            }
        }
        return parentNode.getChildrenNodes().get(tempMaxIndex);
    }

    public static boolean nodeBackpropagation(Node childNode) {
        return false;
    }

    public static ArrayList<Action> getAvailabelActions(Node node) {
        ArrayList<Action> availableActions = new ArrayList<Action>();
        for (int i = 0; i < node.getOwner().getPieces().length; i++) {
            ArrayList<Cell> availableCells = new ArrayList<Cell>();
            node.getGameController().selectPiece(node.getOwner().getPieces()[i].getPosition());
            availableCells.addAll(node.getGameController().getHighlightedCells());
            for (int j = 0; j < availableCells.size(); j++) {
                if (availableCells.get(i)
                        .equals(ChessBoard.getTakeoffCell(node.getGameController().getChessBoard(), node.getOwner()))) {
                    availableActions
                            .add(new Action(node.getOwner().getPieces()[i], availableCells.get(j), ActionType.Takeoff));
                } else if (availableCells.get(i)
                        .equals(ChessBoard.getEndCell(node.getGameController().getChessBoard(), node.getOwner()))) {
                    availableActions
                            .add(new Action(node.getOwner().getPieces()[i], availableCells.get(j), ActionType.Win));
                } else {
                    availableActions
                            .add(new Action(node.getOwner().getPieces()[i], availableCells.get(j), ActionType.NormalMove));
                }
            }
        }
        return availableActions;
    }
}
