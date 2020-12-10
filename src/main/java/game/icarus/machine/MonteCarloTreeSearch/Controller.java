package game.icarus.machine.MonteCarloTreeSearch;

import java.util.ArrayList;

import game.icarus.entity.Cell;
import game.icarus.entity.Player;
import game.icarus.map.ChessBoard;

public class Controller {
    public static Node initializeMonteCarloTree(ChessBoard chessBoard, Player owner){
        return new Node(owner, chessBoard);
    }

    public static boolean nodeExpansion(Node parentNode){

    }

    public static Node nodeSelection(Node parentNode){
        double tempMaxWeight = 0;
        int tempMaxIndex = -1;
        for(int i = 0; i < parentNode.getChildrenNodes().size(); i++){
            parentNode.getChildrenNodes().get(i).calculateWeight();
            if(parentNode.getChildrenNodes().get(i).getWeight()>tempMaxWeight){
                tempMaxIndex = i;
                tempMaxWeight = parentNode.getChildrenNodes().get(i).getWeight();
            }
        }
    }

    public static boolean nodeBackpropagation(Node childNode){

    }

    public static ArrayList<Cell> getAvailabelCells(Node node, Map<String, Object> rollResult){
        ArrayList<Cell> availableCells = new ArrayList<Cell>();
        for(int i = 0; i < node.getOwner().getPieces().length; i++){

        }
    }
}
