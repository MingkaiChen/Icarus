package game.icarus.map;

import game.icarus.attribute.Color;
import game.icarus.entity.Block;
import game.icarus.entity.Cell;

public class NormalPath extends Block {
    public NormalPath() {
        this.Cells = new Cell[52];
        for (int i = 0; i < this.Cells.length; i++) {
            switch (i % 4) {
                case 0:
                    this.Cells[i] = new Cell(Color.Red);
                case 1:
                    this.Cells[i] = new Cell(Color.Yellow);
                case 2:
                    this.Cells[i] = new Cell(Color.Blue);
                case 3:
                    this.Cells[i] = new Cell(Color.Green);
            }
        }
    }

    public static boolean initialize(NormalPath normalPath) {
        for (int i = 0; i < normalPath.Cells.length; i++)
            normalPath.Cells[i].clear();
        return true;
    }

}
