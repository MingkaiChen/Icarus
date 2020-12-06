package game.icarus.entity;

public abstract class Block {
    protected Cell[] Cells;
    protected Player owner;

    public static boolean initialize(Block block) {
        return false;
    }

    public static boolean clear(Block block) {
        for (int i = 0; i < block.Cells.length; i++)
            block.Cells[i].clear();
        return true;
    }

    public Player getOwner() {
        if (this.owner != null) {
            return this.owner;
        } else {
            return null;
        }
    }
}
