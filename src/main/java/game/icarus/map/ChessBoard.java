package game.icarus.map;

import java.util.ArrayList;

import game.icarus.entity.Piece;
import game.icarus.entity.Player;

public class ChessBoard {
    private final NormalPath normalPath;
    private final ParkingApron[] parkingAprons;
    private final Takeoff[] takeoffs;
    private final TerminalPath[] terminalPaths;

    public ChessBoard(Player[] players) {
        normalPath = new NormalPath();
        parkingAprons = new ParkingApron[4];
        takeoffs = new Takeoff[4];
        terminalPaths = new TerminalPath[4];
        for (int i = 0; i < 4; i++) {
            parkingAprons[i] = new ParkingApron(players[i]);
            takeoffs[i] = new Takeoff(players[i]);
            terminalPaths[i] = new TerminalPath(players[i]);
        }
    }

    public static boolean initialize(ChessBoard chessBoard) {
        NormalPath.initialize(chessBoard.normalPath);
        for (int i = 0; i < chessBoard.parkingAprons.length; i++)
            ParkingApron.initialize(chessBoard.parkingAprons[i]);
        for (int i = 0; i < chessBoard.takeoffs.length; i++)
            Takeoff.initialize(chessBoard.takeoffs[i]);
        for (int i = 0; i < chessBoard.terminalPaths.length; i++)
            TerminalPath.initialize(chessBoard.terminalPaths[i]);
        return true;
    }

    public static ArrayList<Piece> checkEnd(ChessBoard chessBoard) {
        ArrayList<Piece> getEnd = new ArrayList<Piece>();
        for (int i = 0; i < chessBoard.terminalPaths.length; i++) 
            getEnd.addAll(TerminalPath.checkEnd(chessBoard.terminalPaths[i]));
        return getEnd;
    }

}
