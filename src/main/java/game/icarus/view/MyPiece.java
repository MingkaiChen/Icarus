package game.icarus.view;

import game.icarus.entity.Piece;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MyPiece extends ImageView {
    Piece piece;
    public MyPiece(double x, double y, double size, Image background, Piece piece) {
        super(background);
        setX(x - size / 2);
        setY(y - size / 2);
        setFitHeight(size);
        setFitWidth(size);
        this.piece = piece;
        setMouseTransparent(true);
    }
}
