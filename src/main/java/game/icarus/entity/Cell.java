package game.icarus.entity;

import java.util.ArrayList;
import java.util.UUID;

import game.icarus.attribute.CellType;
import game.icarus.attribute.Color;

public class Cell {

    private UUID cellID;
    private Color cellColor;
    // private CellType cellType;
    // private Color occupiedColor;
    private ArrayList<Piece> occupiedPieces;
    private Cell nextCell;
    private Cell forkCell;
    private Block belongsTo;

    public Cell(Color color, Block cellType) {
        this.cellID = UUID.randomUUID();
        this.cellColor = color;
        // this.cellType = type;
        this.occupiedPieces = new ArrayList<Piece>();
        this.belongsTo = cellType;
    }

    public Cell(Cell anotherCell) {
        this.cellID = anotherCell.cellID;
        this.cellColor = anotherCell.cellColor;
        this.occupiedPieces = (ArrayList<Piece>) anotherCell.occupiedPieces.clone();
        this.nextCell = new Cell(anotherCell.nextCell);
        this.forkCell = new Cell(anotherCell.forkCell);
    }

    public boolean isForkAvailable() {
        return this.getOccupied().get(0).getOwner().getColor().equals(this.forkCell.cellColor);
    }

    public boolean hasFork() {
        return this.forkCell!=null;
    }

    public Cell getFork() {
        return this.forkCell;
    }

    public Block getBelongsto() {
        return this.belongsTo;
    }

    public boolean isOccupied() {
        return !this.occupiedPieces.isEmpty();
    }

    public boolean setNextCell(Cell nextCell) {
        this.nextCell = nextCell;
        return true;
    }

    public boolean setForkCell(Cell forkCell) {
        this.forkCell = forkCell;
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
        Cell nextCell = this;
        for(int i = 0; i < steps; i++){
            nextCell = nextCell.nextCell();
        }
        return nextCell;
    }

    // public CellType getType() {
    // return this.cellType;
    // }

}
