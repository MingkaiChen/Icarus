package game.icarus.controller;

import game.icarus.attribute.Color;
import game.icarus.attribute.GameModel;
import game.icarus.entity.*;
import game.icarus.map.*;

import java.util.Map;

public class GameController {
    private final Player[] players;
    private final ChessBoard chessBoard;
    private final Piece[] pieces;
    private final Dice dice;
    private int currentPlayer;
    private final Setting settings;
    private Map<String, Object> diceResult;
    private boolean walkable = false;
    public GameController(Setting settings) {
        this.settings = settings;
        int playerNumber = settings.getPlayerNumber();
        players = new Player[playerNumber];
        for (int i = 0; i < playerNumber; i++) {
            players[i] = new Player(Color.values()[i]);
        }
        chessBoard = new ChessBoard(players);
        pieces = new Piece[playerNumber * 4];
        for (int i = 0; i < playerNumber; i++) {
            for (int j = 0; j < 4; j++) {
                pieces[i*4+j] = new Piece(Color.values()[i]);
            }
        }

        dice = new Dice();
    }
    public Save saveGame() {
        Save s;
        if (walkable) s = new Save(pieces, players, currentPlayer, diceResult);
        else s = new Save(pieces, players, currentPlayer);
        return s;
    }
    public void initializeGame() {
        currentPlayer = 0;
        //model.placeInitialPieces();
    }

    public void nextPlayer() {
        currentPlayer = (currentPlayer + 1) % settings.getPlayerNumber();
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public boolean selectPiece() {
        //TODO: select a cell and judge if a piece can be select
        return true;
    }
    public void rollDice() {
        diceResult = dice.roll();
        walkable = true;
    }

}