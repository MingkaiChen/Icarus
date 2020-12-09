package game.icarus.entity;

public abstract class Block {
    protected Cell[] cells;
    protected Player owner;

    public static boolean initialize(Block block) {
        return false;
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
