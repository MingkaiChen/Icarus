package game.icarus.map;

import game.icarus.entity.Block;
import game.icarus.entity.Cell;
import game.icarus.entity.Piece;
import game.icarus.entity.Player;

public class TerminalPath extends Block {
    public TerminalPath(Player owner) {
        this.owner = owner;
        this.cells = new Cell[6];
        for (int i = 0; i < this.cells.length; i++)
            this.cells[i] = new Cell(owner.getColor());
    }

    public static boolean initialize(TerminalPath terminalPath) {
        for (int i = 0; i < terminalPath.cells.length; i++)
            terminalPath.cells[i].clear();
        return true;
    }

    public static Piece checkEnd(TerminalPath terminalPath) {
        if (terminalPath.cells[terminalPath.cells.length - 1].isOccupied()) {
            return terminalPath.cells[terminalPath.cells.length - 1].getOccupied();
        } else {
            return null;
        }
    }
}
