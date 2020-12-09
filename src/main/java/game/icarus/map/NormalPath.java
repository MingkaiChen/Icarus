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
                    this.cells[i] = new Cell(Color.Red);
                case 1:
                    this.cells[i] = new Cell(Color.Yellow);
                case 2:
                    this.cells[i] = new Cell(Color.Blue);
                case 3:
                    this.cells[i] = new Cell(Color.Green);
                default:
                    continue;
            }
        }
    }

    public static boolean initialize(NormalPath normalPath) {
        for (int i = 0; i < normalPath.cells.length; i++)
            normalPath.cells[i].clear();
        return true;
    }

    public Cell nextCell(Cell origin) {
        for (int i = 0; i < this.cells.length; i++) {
            if (this.cells[i].equals(origin) && i < this.cells.length - 1)
                return this.cells[i + 1];
            else if (this.cells[i].equals(origin) && i == this.cells.length - 1)
                return this.cells[0];
        }
        return null;
    }

    public Cell nextCell(Cell origin, int step) {
        for (int i = 0; i < this.cells.length; i++) {
            if (this.cells[i].equals(origin) && i < this.cells.length - step)
                return this.cells[i + step];
            else if (this.cells[i].equals(origin) && i >= this.cells.length - step)
                return this.cells[step - (this.cells.length - i)];
        }
        return null;
    }

}
