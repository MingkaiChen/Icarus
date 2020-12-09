package game.icarus.entity;

import java.util.ArrayList;
import java.util.UUID;
import game.icarus.attribute.Color;

public class Cell {

    private UUID cellID;
    private Color cellColor;
    // private CellType cellType;
    // private Color occupiedColor;
    private ArrayList<Piece> occupiedPieces;

    public Cell(Color color) {
        this.cellID = UUID.randomUUID();
        this.cellColor = color;
        // this.cellType = type;
        this.occupiedPieces = new ArrayList<Piece>();
    }

    public boolean isOccupied() {
        return !this.occupiedPieces.isEmpty();
    }

    // public Color getOccupiedColor() throws Exception {
    // if (this.isOccupied()) {
    // return this.occupiedColor;
    // } else {
    // throw new Exception("[ERROR] This Cell is not occupied!");
    // }
    // }

    public ArrayList<Piece> getOccupied() {
        if (this.isOccupied()) {
            return this.occupiedPieces;
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
        this.occupiedPieces.add(piece);
        return true;
    }

    public boolean clear() {
        this.occupiedPieces.clear();
        return true;
    }

    // public CellType getType() {
    // return this.cellType;
    // }

}
