package game.icarus.map;

import game.icarus.entity.Block;
import game.icarus.entity.Cell;
import game.icarus.entity.Player;

public class Takeoff extends Block {
    public Takeoff(Player owner) {
        this.owner = owner;
        this.Cells = new Cell[1];
        Cells[0] = new Cell(owner.getColor());
    }

    public static boolean initialize(Takeoff takeoff) {
        takeoff.Cells[0].clear();
        return true;
    }

}
