package game.icarus.entity;

import game.icarus.attribute.CellType;
import game.icarus.attribute.Color;
import game.icarus.map.NormalPath;
import game.icarus.map.ParkingApron;
import game.icarus.map.Takeoff;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

public class Save {

    public static class SavedPiece {
        private Color color;
        private CellType cellType;
        private int index;
        private UUID uuid;
        public SavedPiece () {}
        public SavedPiece(Piece p) {
            uuid = p.getPieceID();
            color = p.getColor();
            Block tmp = p.getPosition().getBelongsto();
            if (tmp instanceof NormalPath) cellType = CellType.Normal;
            else if (tmp instanceof ParkingApron) cellType = CellType.ParkingApron;
            else if (tmp instanceof Takeoff) cellType = CellType.Takeoff;
            else cellType = CellType.TerminalPath;
            index = tmp.getIndex(p.getPosition());
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public CellType getCellType() {
            return cellType;
        }

        public void setCellType(CellType cellType) {
            this.cellType = cellType;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public UUID getUuid() {
            return uuid;
        }

        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }
    }

    private LocalDateTime saveTime;
    private String name;

    private ArrayList<SavedPiece> pieces = new ArrayList<>();
    private int currentPlayer;
    private Setting settings;
    private DiceResult diceResult;
    private boolean walkable;
    private int luckyCount;
    private HashSet<UUID> movedPiecesUUID = new HashSet<>();

    public Save() {}

    public Save(String name, ArrayList<Piece> pieces, int currentPlayer, Setting settings, DiceResult diceResult, boolean walkable, int luckyCount, HashSet<Piece> movedPieces) {
        for (Piece p : pieces) {
            this.pieces.add(new SavedPiece(p));
        }
        this.name = name;
        this.currentPlayer = currentPlayer;
        this.settings = settings;
        this.diceResult = diceResult;
        this.walkable = walkable;
        this.luckyCount = luckyCount;
        for (Piece p : movedPieces) {
            this.movedPiecesUUID.add(p.getPieceID());
        }
    }

    public ArrayList<SavedPiece> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<SavedPiece> pieces) {
        this.pieces = pieces;
    }

    public LocalDateTime getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(LocalDateTime saveTime) {
        this.saveTime = saveTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Setting getSettings() {
        return settings;
    }

    public void setSettings(Setting settings) {
        this.settings = settings;
    }

    public DiceResult getDiceResult() {
        return diceResult;
    }

    public void setDiceResult(DiceResult diceResult) {
        this.diceResult = diceResult;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public int getLuckyCount() {
        return luckyCount;
    }

    public void setLuckyCount(int luckyCount) {
        this.luckyCount = luckyCount;
    }

    public HashSet<UUID> getMovedPiecesUUID() {
        return movedPiecesUUID;
    }

    public void setMovedPiecesUUID(HashSet<UUID> movedPiecesUUID) {
        this.movedPiecesUUID = movedPiecesUUID;
    }
}
