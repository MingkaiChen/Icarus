package game.icarus.machine.Greedy;

import java.util.ArrayList;

import game.icarus.entity.Player;
import game.icarus.map.ChessBoard;

public class Status {
    private Player owner;
    private ChessBoard chessBoard;
    private int value;
    private Player[] opponents;

    public Status(ChessBoard chessBoard, Player owner, Player[] opponents) {
        this.chessBoard = chessBoard;
        this.owner = owner;
        this.opponents = opponents;
        this.value = Status.calculateValue(this);
    }

    public static int calculateValue(Status status) {
        int value = 0;
        for (int i = 0; i < status.owner.getPieces().length; i++) {
            int j = 0;
            while (true) {
                if (status.owner.getPieces()[i].getPosition().nextCell() == null) {
                    break;
                } else if (status.owner.getPieces()[i].getPosition().nextCell(j).equals(status.owner.getEnd())) {
                    value += 56 - j;
                    break;
                } else if (status.owner.getPieces()[i].getPosition().nextCell(j)
                        .equals(status.owner.getToTerminalPath())) {
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
                    } else if (status.opponents[i].getPieces()[j].getPosition().nextCell(k)
                            .equals(status.opponents[i].getEnd())) {
                        value += k;
                        break;
                    } else if (status.opponents[i].getPieces()[j].getPosition().nextCell(k)
                            .equals(status.opponents[i].getToTerminalPath())) {
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
