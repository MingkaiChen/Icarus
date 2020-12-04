package game.icarus.map;

import game.icarus.entity.Block;
import game.icarus.entity.Cell;
import game.icarus.entity.Piece;
import game.icarus.entity.Player;

public class ParkingApron extends Block {
    public ParkingApron(Player owner) {
        this.owner = owner;
        this.Cells = new Cell[4];
        for (int i = 0; i < 4; i++)
            this.Cells[i] = new Cell(owner.getColor());
    }

    public static boolean initialize(ParkingApron parkingApron, Piece[] pieces) {
        for (int i = 0; i < parkingApron.Cells.length; i++)
            parkingApron.Cells[i].setOccipied(pieces[i]);
        return true;
    }

}
