package game.icarus.view;

import game.icarus.entity.Piece;
import javafx.scene.shape.Circle;

public class MyPiece extends Circle {
    Piece piece;
    public MyPiece(double x, double y, double radius, Piece piece) {
        super(x, y, radius);
        this.piece = piece;
    }
}
