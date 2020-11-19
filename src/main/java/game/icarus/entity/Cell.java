package game.icarus.entity;

public class Cell {

    private int cellID;
    private String cellColor;
    private String cellType;
    private String occupiedColor;

    public Cell(int ID, String color, String type) {
        this.cellID = ID;
        this.cellColor = color;
        this.cellType = type;
        this.occupiedColor = "null";
    }

    public boolean isOuccupied() {
        if (this.occupiedColor == "null") {
            return false;
        } else {
            return true;
        }
    }

    public String getOuccupiedColor() throws Exception {
        if (this.isOuccupied()) {
            return this.occupiedColor;
        } else {
            throw new Exception("[ERROR] This Cell is not occupied!");
        }
    }

    public int getID() {
        return this.cellID;
    }

    public String getColor() {
        return this.cellColor;
    }

    public String getType() {
        return this.cellType;
    }

}
