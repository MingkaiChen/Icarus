package game.icarus.map;

import game.icarus.entity.Block;
import game.icarus.entity.Cell;
import game.icarus.entity.Player;
import game.icarus.attribute.Color;

public class Takeoff extends Block {
    public Takeoff(Player owner, NormalPath target) {
        this.owner = owner;
        this.cells = new Cell[1];
        cells[0] = new Cell(owner.getColor());
        switch(owner.getColor()){
            case Blue:
                this.cells[0].setNextCell(target.getCell(5));
                break;
            case Green:
                this.cells[0].setNextCell(target.getCell(18));
                break;
            case Red:
                this.cells[0].setNextCell(target.getCell(31));
                break;
            case Yellow:
                this.cells[0].setNextCell(target.getCell(44));
                break;
            default:
                break;
        }
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
