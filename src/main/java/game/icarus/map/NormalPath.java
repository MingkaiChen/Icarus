package game.icarus.map;

import game.icarus.attribute.Color;
import game.icarus.entity.Block;
import game.icarus.entity.Cell;

public class NormalPath extends Block {
    public NormalPath() {
        this.cells = new Cell[52];
        for (int i = 0; i < this.cells.length; i++) {
            switch (i % 4) {
                case 0:
                    this.cells[i] = new Cell(Color.Red, this);
                    break;
                case 1:
                    this.cells[i] = new Cell(Color.Yellow, this);
                    break;
                case 2:
                    this.cells[i] = new Cell(Color.Blue, this);
                    break;
                case 3:
                    this.cells[i] = new Cell(Color.Green, this);
                    break;
                default:
                    continue;
            }
            if (i != 0) {
                this.cells[i - 1].setNextCell(this.cells[i]);
            }
        }
        this.cells[this.cells.length - 1].setNextCell(this.cells[0]);
    }

    public NormalPath(NormalPath anotherNormalPath) {
        super(anotherNormalPath);
    }

    public static boolean initialize(NormalPath normalPath) {
        for (int i = 0; i < normalPath.cells.length; i++)
            normalPath.cells[i].clear();
        return true;
    }

}
