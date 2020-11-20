package game.icarus.map;

import game.icarus.attribute.CellType;
import game.icarus.attribute.Color;
import game.icarus.attribute.GameModel;
import game.icarus.entity.Cell;

public class ChessBoard {
    private Cell[] Cells;

    public ChessBoard(GameModel gameModel) {
        if (gameModel == GameModel.Regular) {
            Cell[] newBoard = new Cell[96];
            for (int i = 0; i < 96; i++) {
                if (i < 52) {
                    switch (i % 4) {
                        case 0:
                            newBoard[i] = new Cell(i, Color.Red, CellType.Normal);
                        case 1:
                            newBoard[i] = new Cell(i, Color.Yellow, CellType.Normal);
                        case 2:
                            newBoard[i] = new Cell(i, Color.Blue, CellType.Normal);
                        case 3:
                            newBoard[i] = new Cell(i, Color.Green, CellType.Normal);
                    }
                } else if (i < 76) {
                    switch ((i % 52) / 6) {
                        case 0:
                            newBoard[i] = new Cell(i, Color.Blue, CellType.TerminalPath);
                        case 1:
                            newBoard[i] = new Cell(i, Color.Green, CellType.TerminalPath);
                        case 2:
                            newBoard[i] = new Cell(i, Color.Red, CellType.TerminalPath);
                        case 3:
                            newBoard[i] = new Cell(i, Color.Yellow, CellType.TerminalPath);
                    }
                } else if (i < 92) {
                    switch ((i % 76) / 4) {
                        case 0:
                            newBoard[i] = new Cell(i, Color.Yellow, CellType.ParkingApron);
                        case 1:
                            newBoard[i] = new Cell(i, Color.Green, CellType.ParkingApron);
                        case 2:
                            newBoard[i] = new Cell(i, Color.Blue, CellType.ParkingApron);
                        case 3:
                            newBoard[i] = new Cell(i, Color.Red, CellType.ParkingApron);
                    }
                } else {
                    switch (i % 92) {
                        case 0:
                            newBoard[i] = new Cell(i, Color.Yellow, CellType.Takeoff);
                        case 1:
                            newBoard[i] = new Cell(i, Color.Green, CellType.Takeoff);
                        case 2:
                            newBoard[i] = new Cell(i, Color.Blue, CellType.Takeoff);
                        case 3:
                            newBoard[i] = new Cell(i, Color.Red, CellType.Takeoff);
                    }
                }
            }
            newBoard[9] = new Cell(9, Color.Yellow, CellType.Shortcut);
            newBoard[22] = new Cell(22, Color.Blue, CellType.Shortcut);
            newBoard[35] = new Cell(35, Color.Green, CellType.Shortcut);
            newBoard[48] = new Cell(48, Color.Red, CellType.Shortcut);
            this.Cells = newBoard;
        }
    }
}
