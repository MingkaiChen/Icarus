package game.icarus.map;

import game.icarus.entity.Cell;

public class ChessBoard {
    private Cell[] Cells;

    public ChessBoard(String gameModel) {
        if (gameModel == "Normal") {
            Cell[] newBoard = new Cell[96];
            for (int i = 0; i < 96; i++) {
                String cellColor = "null";
                if (i < 52) {
                    switch (i % 4) {
                        case 0:
                            cellColor = "Red";
                        case 1:
                            cellColor = "Yellow";
                        case 2:
                            cellColor = "Blue";
                        case 3:
                            cellColor = "Green";
                    }
                    newBoard[i] = new Cell(i, cellColor, "NormalCell");
                } else if (i < 76) {
                    switch ((i % 52) / 6) {
                        case 0:
                            cellColor = "Blue";
                        case 1:
                            cellColor = "Green";
                        case 2:
                            cellColor = "Red";
                        case 3:
                            cellColor = "Yellow";
                    }
                    newBoard[i] = new Cell(i, cellColor, "TerminalRoute");
                } else if (i < 92) {
                    switch ((i % 76) / 4) {
                        case 0:
                            cellColor = "Yellow";
                        case 1:
                            cellColor = "Green";
                        case 2:
                            cellColor = "Blue";
                        case 3:
                            cellColor = "Red";
                    }
                    newBoard[i] = new Cell(i, cellColor, "ParkingApron");
                } else {
                    switch (i % 92) {
                        case 0:
                            cellColor = "Yellow";
                        case 1:
                            cellColor = "Green";
                        case 2:
                            cellColor = "Blue";
                        case 3:
                            cellColor = "Red";
                    }
                    newBoard[i] = new Cell(i, cellColor, "TakeoffCell");
                }
            }
            newBoard[9] = new Cell(9, "Yellow", "Shortcut");
            newBoard[22] = new Cell(9, "Blue", "Shortcut");
            newBoard[35] = new Cell(9, "Green", "Shortcut");
            newBoard[48] = new Cell(9, "Red", "Shortcut");
            this.Cells = newBoard;
        }
    }
}
