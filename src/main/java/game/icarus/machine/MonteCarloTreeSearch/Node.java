package game.icarus.machine.MonteCarloTreeSearch;

import java.util.ArrayList;

import game.icarus.controller.GameController;
import game.icarus.entity.Player;
import game.icarus.entity.Save;
import game.icarus.map.ChessBoard;

public class Node {
    private int nValue;
    private int qValue;
    private ArrayList<Node> childrenNodes;
    private Node parentNode;
    private Player owner;
    private ChessBoard chessBoard;
    private GameController gameController;
    private double weight;

    public Node(Node parentNode, Save tempSave){
        this.parentNode = parentNode;
        this.gameController = new GameController(tempSave);
        parentNode.addChild(this);
        this.nValue = 0;
        this.qValue = 0;
        this.weight = 0;
        this.owner = parentNode.getOwner();
    }

    public Node(Player owner, ChessBoard chessBoard) {
        this.parentNode = null;
        this.chessBoard = chessBoard;
        this.nValue = 0;
        this.qValue = 0;
        this.owner = owner;
    }

    public boolean addChild(Node childNode) {
        this.childrenNodes.add(childNode);
        return true;
    }

    public boolean addChild(ArrayList<Node> childrenNodes) {
        this.childrenNodes.addAll(childrenNodes);
        return true;
    }

    public ArrayList<Node> getChildrenNodes() {
        return this.childrenNodes;
    }

    public Player getOwner() {
        return this.owner;
    }

    public GameController getGameController() {
        return this.gameController;
    }

    public boolean calculateWeight() {
        this.weight = ((double) this.qValue / (double) this.nValue)
                + Math.sqrt((2 * Math.log(this.parentNode.nValue)) / this.nValue);
        return true;
    }

    public double getWeight() {
        return this.weight;
    }

    public int getNValue() {
        return this.nValue;
    }

}
