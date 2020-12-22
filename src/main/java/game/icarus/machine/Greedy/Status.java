package game.icarus.machine.Greedy;

import java.util.ArrayList;
import java.util.Map;

import game.icarus.controller.GameController;
import game.icarus.entity.Action;
import game.icarus.entity.Dice;
import game.icarus.entity.DiceResult;
import game.icarus.entity.Player;
import game.icarus.entity.Save;
import game.icarus.map.ChessBoard;

public class Status {
    private Player owner;
    private ChessBoard chessBoard;
    private int value;
    private Player[] opponents;
    private DiceResult rollResult;

    public Status(ChessBoard chessBoard, Player owner, Player[] opponents, DiceResult rollResult) {
        this.chessBoard = new ChessBoard(chessBoard);
        this.owner = new Player(owner);
        ArrayList<Player> tempOpponents = new ArrayList<Player>();
        for (int i = 0; i < opponents.length; i++)
            tempOpponents.add(new Player(opponents[i]));
        this.opponents = (Player[]) tempOpponents.toArray();
        this.value = Status.calculateValue(this);
        this.rollResult = rollResult;
    }

    public Status(ChessBoard chessBoard, Player owner, Player[] opponents) {
        this.chessBoard = new ChessBoard(chessBoard);
        this.owner = new Player(owner);
        ArrayList<Player> tempOpponents = new ArrayList<Player>();
        for (int i = 0; i < opponents.length; i++)
            tempOpponents.add(new Player(opponents[i]));
        this.opponents = (Player[]) tempOpponents.toArray();
        this.value = Status.calculateValue(this);
    }

    public Status(Status status, Action action) {
        this.chessBoard = new ChessBoard(status.getChessBoard());
        this.owner = new Player(status.owner);
        ArrayList<Player> tempOpponents = new ArrayList<Player>();
        for (int i = 0; i < status.opponents.length; i++)
            tempOpponents.add(new Player(status.opponents[i]));
        this.opponents = (Player[]) tempOpponents.toArray();
        this.value = Status.calculateValue(this);
    }

    public DiceResult getRollResult() {
        return this.rollResult;
    }

    public ChessBoard getChessBoard() {
        return this.chessBoard;
    }

    public Player getOwner() {
        return this.owner;
    }

    public static int calculateValue(Status status) {
        int value = 0;
        for (int i = 0; i < status.owner.getPieces().length; i++) {
            int j = 0;
            while (true) {
                if (status.owner.getPieces()[i].getPosition().nextCell() == null) {
                    break;
                } else if (status.owner.getPieces()[i].getPosition().nextCell(j).getID()
                        .equals(status.owner.getEnd().getID())) {
                    value += 56 - j;
                    break;
                } else if (status.owner.getPieces()[i].getPosition().nextCell(j).getID()
                        .equals(status.owner.getToTerminalPath().getID())) {
                    value += 50 - j;
                    break;
                }
                j++;
            }
        }
        for (int i = 0; i < status.opponents.length; i++) {
            for (int j = 0; j < status.opponents[i].getPieces().length; j++) {
                int k = 0;
                while (true) {
                    if (status.opponents[i].getPieces()[j].getPosition().nextCell() == null) {
                        value += 56;
                        break;
                    } else if (status.opponents[i].getPieces()[j].getPosition().nextCell(k).getID()
                            .equals(status.opponents[i].getEnd().getID())) {
                        value += k;
                        break;
                    } else if (status.opponents[i].getPieces()[j].getPosition().nextCell(k).getID()
                            .equals(status.opponents[i].getToTerminalPath().getID())) {
                        value += 6 + k;
                        break;
                    }
                    k++;
                }
            }
        }
        return value;
    }

}
