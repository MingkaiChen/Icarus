package game.icarus.entity;

import game.icarus.attribute.CellType;
import game.icarus.attribute.Color;

public class Cell {

    private int cellID;
    private Color cellColor;
    private CellType cellType;
    private Color occupiedColor;

    public Cell(int ID, Color color, CellType type) {
        this.cellID = ID;
        this.cellColor = color;
        this.cellType = type;
        this.occupiedColor = Color.Null;
    }

    public boolean isOccupied() {
        return this.occupiedColor != Color.Null;
    }

    public Color getOccupiedColor() throws Exception {
        if (this.isOccupied()) {
            return this.occupiedColor;
        } else {
            throw new Exception("[ERROR] This Cell is not occupied!");
        }
    }

    public int getID() {
        return this.cellID;
    }

    public Color getColor() {
        return this.cellColor;
    }

    public CellType getType() {
        return this.cellType;
    }

}
