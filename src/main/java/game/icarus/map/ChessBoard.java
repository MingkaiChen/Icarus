package game.icarus.map;

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
        for (int i = 0; i < chessBoard.parkingAprons.length; i++) {
            Piece[] pieces = new Piece[4];
            for (int j = 0; j < pieces.length; j++) {
                pieces[j] = new Piece(chessBoard.parkingAprons[i].getOwner().getColor());
            }
            ParkingApron.initialize(chessBoard.parkingAprons[i], pieces);
        }
        for (int i = 0; i < chessBoard.takeoffs.length; i++)
            Takeoff.initialize(chessBoard.takeoffs[i]);
        for (int i = 0; i < chessBoard.terminalPaths.length; i++)
            TerminalPath.initialize(chessBoard.terminalPaths[i]);
        return true;
    }

    public static Piece checkEnd(ChessBoard chessBoard) {
        // Caution! This method only return one piece! Please avoid use this method when
        // there would be more than one piece get to the end.
        for (int i = 0; i < chessBoard.terminalPaths.length; i++) {
            if (TerminalPath.checkEnd(chessBoard.terminalPaths[i]) != null)
                return TerminalPath.checkEnd(chessBoard.terminalPaths[i]);
        }
        return null;
    }

}
