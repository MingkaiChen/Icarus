package game.icarus.map;

import game.icarus.entity.Block;
import game.icarus.entity.Cell;
import game.icarus.entity.Piece;
import game.icarus.entity.Player;

public class TerminalPath extends Block {
    public TerminalPath(Player owner) {
        this.owner = owner;
        this.Cells = new Cell[6];
        for (int i = 0; i < this.Cells.length; i++)
            this.Cells[i] = new Cell(owner.getColor());
    }

    public static boolean initialize(TerminalPath terminalPath) {
        for (int i = 0; i < terminalPath.Cells.length; i++)
            terminalPath.Cells[i].clear();
        return true;
    }

    public static Piece checkEnd(TerminalPath terminalPath) {
        if (terminalPath.Cells[terminalPath.Cells.length - 1].isOccupied()) {
            return terminalPath.Cells[terminalPath.Cells.length - 1].getOccupied();
        } else {
            return null;
        }
    }
}
