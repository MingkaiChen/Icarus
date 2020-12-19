package game.icarus.entity;

import java.util.UUID;

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

    public Cell getCell(int index){
        return this.cells[index];
    }

    public Cell getCell(UUID uuid){
        for(int i = 0; i <= this.cells.length; i++){
            if(this.cells[i].getID().equals(uuid))
                return this.cells[i];
        }
        return null;
    }
}
