package game.icarus.map;

import game.icarus.entity.Block;
import game.icarus.entity.Cell;
import game.icarus.entity.Player;

public class Takeoff extends Block {
    public Takeoff(Player owner) {
        this.owner = owner;
        this.cells = new Cell[1];
        cells[0] = new Cell(owner.getColor());
    }

    public Takeoff(Takeoff anotherTakeoff){
        this.cells = anotherTakeoff.cells.clone();
        this.owner = anotherTakeoff.owner;
    }

    public static boolean initialize(Takeoff takeoff) {
        takeoff.cells[0].clear();
        return true;
    }

}
