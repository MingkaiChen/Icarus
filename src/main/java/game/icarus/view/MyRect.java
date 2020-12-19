package game.icarus.view;

import game.icarus.entity.Cell;
import javafx.scene.shape.Rectangle;

public class MyRect extends Rectangle {
    private Cell cell;

    public MyRect(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }
}
