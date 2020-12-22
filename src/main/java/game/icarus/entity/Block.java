package game.icarus.entity;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Block {
    protected Cell[] cells;
    protected Player owner;

    public static boolean initialize(Block block) {
        return false;
    }

    public Block() {

    }

    public Block(Block block) {
        this.owner = new Player(block.getOwner());
        ArrayList<Cell> tempCells = new ArrayList<Cell>();
        for (int i = 0; i < block.cells.length; i++)
            tempCells.add(new Cell(block.cells[i]));
        this.cells = (Cell[]) tempCells.toArray();
    }

    public static boolean clear(Block block) {
        for (int i = 0; i < block.cells.length; i++)
            block.cells[i].clear();
        return true;
    }

    public Player getOwner() {
        if (this.owner != null) {
            return this.owner;
        } else {
            return null;
        }
    }

    public Cell getCell(int index) {
        return this.cells[index];
    }

    public Cell getCell(UUID uuid) {
        for (int i = 0; i <= this.cells.length; i++) {
            if (this.cells[i].getID().equals(uuid))
                return this.cells[i];
        }
        return null;
    }

    public int getIndex(UUID uuid) {
        for (int i = 0; i <= this.cells.length; i++) {
            if (this.cells[i].getID().equals(uuid))
                return i;
        }
        return -1;
    }

    public int getIndex(Cell cell) {
        for (int i = 0; i <= this.cells.length; i++) {
            if (this.cells[i].equals(cell))
                return i;
        }
        return -1;
    }

}
