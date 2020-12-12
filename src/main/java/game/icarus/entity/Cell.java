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
    private Cell nextCell;

    public Cell(Color color) {
        this.cellID = UUID.randomUUID();
        this.cellColor = color;
        // this.cellType = type;
        this.occupiedPieces = new ArrayList<Piece>();
    }

    public Cell(Cell anotherCell) {
        this.cellID = anotherCell.cellID;
        this.cellColor = anotherCell.cellColor;
        this.occupiedPieces = (ArrayList<Piece>) anotherCell.occupiedPieces.clone();
        this.nextCell = new Cell(anotherCell.nextCell);
    }

    public boolean isOccupied() {
        return !this.occupiedPieces.isEmpty();
    }

    public boolean setNextCell(Cell nextCell) {
        this.nextCell = nextCell;
        return true;
    }

    // public Color getOccupiedColor() throws Exception {
    // if (this.isOccupied()) {
    // return this.occupiedColor;
    // } else {
    // throw new Exception("[ERROR] This Cell is not occupied!");
    // }
    // }

    public ArrayList<Piece> getOccupied() {
        return this.occupiedPieces;
    }

    public UUID getID() {
        return this.cellID;
    }

    public Color getColor() {
        return this.cellColor;
    }

    public boolean setOccupied(Piece piece) {
        this.occupiedPieces.add(piece);
        return true;
    }

    public boolean clear() {
        this.occupiedPieces.clear();
        return true;
    }

    public Cell nextCell() {
        return this.nextCell;
    }

    public Cell nextCell(int steps) {
        return this.nextCell.nextCell(steps - 1);
    }

    // public CellType getType() {
    // return this.cellType;
    // }

}
