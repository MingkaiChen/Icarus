package game.icarus.model;

public class Piece {
    public final ChessColors color;
    private int pos = -1;
    private Boolean isOut = false;
    private Boolean isWin = false;
    Piece(ChessColors color) {
        this.color = color;
    }
    public Boolean isMovable() {
        return (isOut && !isWin);
    }
    private Boolean move(int newPos) {
        if (!isMovable()) return false;
        pos = newPos;
        //FIXME: ChessBoard hasn't been implemented yet
        /*if (pos == getWinningPos(color)) {
            isWin = true;
        }*/
        return true;
    }
}
