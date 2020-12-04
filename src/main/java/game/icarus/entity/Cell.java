package game.icarus.entity;

import java.util.UUID;
import game.icarus.attribute.Color;

public class Cell {

    private UUID cellID;
    private Color cellColor;
    // private CellType cellType;
    // private Color occupiedColor;
    private Piece occupiedPiece;

    public Cell(Color color) {
        this.cellID = UUID.randomUUID();
        this.cellColor = color;
        // this.cellType = type;
        this.occupiedPiece = null;
    }

    public boolean isOccupied() {
        return this.occupiedPiece != null;
    }

    // public Color getOccupiedColor() throws Exception {
    // if (this.isOccupied()) {
    // return this.occupiedColor;
    // } else {
    // throw new Exception("[ERROR] This Cell is not occupied!");
    // }
    // }

    public Piece getOccupied() {
        if (this.isOccupied()) {
            return this.occupiedPiece;
        }
        return null;
    }

    public UUID getID() {
        return this.cellID;
    }

    public Color getColor() {
        return this.cellColor;
    }

    public boolean setOccipied(Piece piece) {
        this.occupiedPiece = piece;
        return true;
    }

    public boolean clear() {
        this.occupiedPiece = null;
        return true;
    }

    // public CellType getType() {
    // return this.cellType;
    // }

}
