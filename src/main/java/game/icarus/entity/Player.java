package game.icarus.entity;

import java.util.UUID;

import game.icarus.attribute.Color;
import game.icarus.map.ParkingApron;

public class Player {
    private final UUID playerID;
    private final Color playerColor;
    private final Piece[] playerPieces;
    private final Cell toTerminalPath;
    private final Cell toShortcut;
    private final Cell End;

    public Player(Color color) {
        this.playerID = UUID.randomUUID();
        this.playerColor = color;
        this.playerPieces = new Piece[4];
        for (int i = 0; i < this.playerPieces.length; i++)
            this.playerPieces[i] = new Piece(this);
    }

    public static boolean initParkingApron(Player player, ParkingApron parkingApron) {
        for (int i = 0; i < player.playerPieces.length; i++)
            player.getPieces()[i].move(parkingApron.cells[i]);
        return true;
    }

    public UUID getID() {
        return this.playerID;
    }

    public Piece[] getPieces() {
        return this.playerPieces;
    }

    public Color getColor() {
        return this.playerColor;
    }

    public Cell getToTerminalPath() {
        return this.toTerminalPath;
    }

    public Cell getEnd() {
        return this.End;
    }
}
