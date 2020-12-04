package game.icarus.map;

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
}
