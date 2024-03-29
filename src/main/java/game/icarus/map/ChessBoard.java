package game.icarus.map;

import java.util.ArrayList;

import game.icarus.entity.Block;
import game.icarus.entity.Cell;
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
        for (int i = 0; i < players.length; i++) {
            takeoffs[i] = new Takeoff(players[i], normalPath);
            parkingAprons[i] = new ParkingApron(players[i], takeoffs[i]);
            terminalPaths[i] = new TerminalPath(players[i]);
            players[i].setToTerminalPath(takeoffs[i].getCell(0).nextCell(50));
            players[i].setToShortcut(takeoffs[i].getCell(0).nextCell(18));
            players[i].setEnd(terminalPaths[i].getCell(5));
            this.setForks(players[i]);
        }
    }

    public ChessBoard(ChessBoard chessBoard) {
        this.normalPath = new NormalPath(chessBoard.normalPath);
        ArrayList<Block> tempBlocks = new ArrayList<Block>();
        tempBlocks.clear();
        for (int i = 0; i < chessBoard.parkingAprons.length; i++) {
            tempBlocks.add(new ParkingApron(chessBoard.parkingAprons[i]));
        }
        this.parkingAprons = (ParkingApron[]) tempBlocks.toArray();
        tempBlocks.clear();
        for (int i = 0; i < chessBoard.takeoffs.length; i++) {
            tempBlocks.add(new Takeoff(chessBoard.takeoffs[i]));
        }
        this.takeoffs = (Takeoff[]) tempBlocks.toArray();
        tempBlocks.clear();
        for (int i = 0; i < chessBoard.terminalPaths.length; i++) {
            tempBlocks.add(new TerminalPath(chessBoard.terminalPaths[i]));
        }
        this.terminalPaths = (TerminalPath[]) tempBlocks.toArray();
    }

    public TerminalPath[] getTerminalPaths() {
        return this.terminalPaths;
    }

    public ParkingApron[] getParkingAprons() {
        return this.parkingAprons;
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

    private boolean setForks(Player availablePlayer) {
        switch (availablePlayer.getColor()) {
            case Blue:
                this.normalPath.getCell(2).setForkCell(this.getTerminalPath(availablePlayer).getCell(0));
                this.normalPath.getCell(22).setForkCell(this.normalPath.getCell(34));
                break;
            case Green:
                this.normalPath.getCell(15).setForkCell(this.getTerminalPath(availablePlayer).getCell(0));
                this.normalPath.getCell(35).setForkCell(this.normalPath.getCell(47));
                break;
            case Red:
                this.normalPath.getCell(28).setForkCell(this.getTerminalPath(availablePlayer).getCell(0));
                this.normalPath.getCell(48).setForkCell(this.normalPath.getCell(8));
                break;
            case Yellow:
                this.normalPath.getCell(41).setForkCell(this.getTerminalPath(availablePlayer).getCell(0));
                this.normalPath.getCell(9).setForkCell(this.normalPath.getCell(21));
                break;
            default:
                return false;
        }
        return true;
    }

    public static ArrayList<Piece> checkEnd(ChessBoard chessBoard) {
        ArrayList<Piece> getEnd = new ArrayList<>();
        for (int i = 0; i < chessBoard.terminalPaths.length; i++)
            getEnd.addAll(TerminalPath.checkEnd(chessBoard.terminalPaths[i]));
        return getEnd;
    }

    public static Cell getTakeoffCell(ChessBoard chessBoard, Player player) {
        for (int i = 0; i < chessBoard.takeoffs.length; i++) {
            if (chessBoard.takeoffs[i].getOwner().equals(player))
                return chessBoard.takeoffs[i].getCell(0);
        }
        return null;
    }

    public static Cell getEndCell(ChessBoard chessBoard, Player player) {
        for (int i = 0; i < chessBoard.terminalPaths.length; i++) {
            if (chessBoard.terminalPaths[i].getOwner().equals(player))
                return chessBoard.terminalPaths[i].getEndCell();
        }
        return null;
    }

    public NormalPath getNormalPath() {
        return this.normalPath;
    }

    public ParkingApron getParkingApronByPlayer(Player player) {
        for (ParkingApron p : parkingAprons) {
            if (p.getOwner().equals(player))
                return p;
        }
        return null;
    }

    // public static Block getPieceLocation(ChessBoard chessBoard, Piece piece) {
    // for (int i = 0; i < 4; i++) {
    // if(chessBoard.getParkingApron(piece.getOwner()).getCell(i).getOccupied().get(0).equals(piece))
    // return chessBoard.getParkingApron(piece.getOwner());
    // }

    // return null;
    // }

    // private ParkingApron getParkingApron(Player owner) {
    // for (int i = 0; i < this.parkingAprons.length; i++) {
    // if (this.parkingAprons[i].getOwner().equals(owner))
    // return this.parkingAprons[i];
    // }
    // return null;
    // }

    public TerminalPath getTerminalPath(Player owner) {
        for (TerminalPath terminalPath : terminalPaths) {
            if (terminalPath.getOwner().equals(owner))
                return terminalPath;
        }
        return null;
    }

    public Takeoff[] getTakeoffs() {
        return takeoffs;
    }
}
