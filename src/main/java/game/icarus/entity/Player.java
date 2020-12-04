package game.icarus.entity;

import java.util.UUID;

import game.icarus.attribute.Color;

public abstract class Player {
    private UUID playerID;
    private Color playerColor;

    public Player(Color color) {
        this.playerID = UUID.randomUUID();
        this.playerColor = color;
    }

    public UUID getID() {
        return this.playerID;
    }

    public Color getColor() {
        return this.playerColor;
    }
}
