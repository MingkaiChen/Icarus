package game.icarus.view;

public class Theme {
    private final String backGround;
    private final String[] pieceImages;
    private final boolean isShowTile;

    public String getBackGround() {
        return backGround;
    }

    public String[] getPieceImages() {
        return pieceImages;
    }

    public boolean isShowTile() {
        return isShowTile;
    }

    public Theme(String backGround, String[] pieceImages, boolean isShowTile) {
        this.backGround = backGround;
        this.pieceImages = pieceImages;
        this.isShowTile = isShowTile;
    }
}
