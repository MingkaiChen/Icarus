package game.icarus.map;

import game.icarus.entity.Block;
import game.icarus.entity.Cell;
import game.icarus.entity.Player;

public class ParkingApron extends Block {
    public ParkingApron(Player owner, Takeoff takeoff) {
        this.owner = owner;
        this.cells = new Cell[4];
        for (int i = 0; i < 4; i++){
            this.cells[i] = new Cell(owner.getColor(), this);
            this.cells[i].setNextCell(takeoff.getCell(0));
        }
    }

    public ParkingApron(ParkingApron anotherParkingApron){
        this.cells = anotherParkingApron.cells.clone();
        this.owner = anotherParkingApron.owner;
    }

    public static boolean initialize(ParkingApron parkingApron) {
        Player.initParkingApron(parkingApron.getOwner(), parkingApron);
        return true;
    }

}
