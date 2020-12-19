package game.icarus.view;

import game.icarus.App;
import game.icarus.controller.GameController;
import game.icarus.entity.Block;
import game.icarus.entity.Cell;
import game.icarus.entity.Piece;
import game.icarus.map.Takeoff;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.net.URL;
import java.util.*;


public class BoardController implements Initializable {

    interface AddToPaneOperation {
        void add(Shape s);
    }

    private final Map<UUID, Rectangle> rectangleMap = new HashMap<>();
    private final List<Rectangle> rectangles = new ArrayList<>();

    private static final Color HIGHLIGHTED_COLOR = new Color(1.0f, 0.84313726f, 0.0f, 0.2f);

    @FXML
    private Pane board;
    @FXML
    private GridPane parkingOne;
    @FXML
    private GridPane parkingTwo;
    @FXML
    private GridPane parkingThree;
    @FXML
    private GridPane parkingFour;

    GridPane[] parkingAprons;

    private GameController controller;

    @FXML
    private BorderPane root;

    private double length;

    private double cellLength;

    private final Color[] colors = {Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        board.setBackground(new Background(new BackgroundImage
                (new Image("file:src/main/resources/game/icarus/timg.jfif"),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, false))));
        controller = new GameController(App.getSetting());
        parkingAprons = new GridPane[]{parkingOne, parkingTwo, parkingThree, parkingFour};
        resize();
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->
                resize();
        root.heightProperty().addListener(stageSizeListener);
        root.widthProperty().addListener(stageSizeListener);
    }

    public void cellHandler(Cell cell) {
        if (!controller.isWalkable()) return;
        if (cell.isOccupied()) {
            System.out.println("occupied");
            boolean tmp = controller.selectPiece(cell);
            if (tmp) highlightCells();
            return;
        }
        if (controller.isSelected())
            for (Cell c : controller.getHighlightedCells()) {
                if (cell.equals(c)) {
                    controller.movePiece(cell);
                    resize();
                    return;
                }
            }
        System.out.println("Do nothing");
    }

    public void highlightCells() {
        for (Rectangle r : rectangles) {
            //r.setFill(Color.TRANSPARENT);
        }
        for (Cell c : controller.getHighlightedCells()) {
            System.out.println(c.getBelongsto());
            Rectangle r = rectangleMap.get(c.getID());
            r.setFill(HIGHLIGHTED_COLOR);
        }
    }

    public void resize() {
        length = Math.min(root.getHeight() * 0.85, root.getWidth());
        cellLength = length / (11 * Math.sqrt(2));
        board.setPrefHeight(length);
        board.setPrefWidth(length);
        System.out.println(length);
        ArrayList<Node> removeList = new ArrayList<>();
        for (Node n : board.getChildren()) {
            if (n instanceof Rectangle) {
                removeList.add(n);
            }
        }
        for (GridPane pane : parkingAprons) {
            for (Node n : pane.getChildren()) {
                if (n instanceof Rectangle) removeList.add(n);
            }
        }
        for (Node n : removeList) {
            for (GridPane pane : parkingAprons) pane.getChildren().remove(n);
            board.getChildren().remove(n);
        }
        drawBoard();
        drawParkingAprons();
    }

    private void turnVector(int[] v, int pos) {
        int tmp = v[0] * pos;
        v[0] = -v[1] * pos;
        v[1] = tmp;
    }

    private void drawCell(double x, double y, Color color, Block block, int index, AddToPaneOperation add) {
        MyRect r = new MyRect(x, y, cellLength, cellLength);
        r.setRotate(45);
        r.setFill(color);
        r.setOnMouseClicked(mouseEvent -> cellHandler(block.getCell(index)));
        add.add(r);
        if (block.getCell(index).isOccupied()) {
            add.add(drawPiece(x, y, block.getCell(index).getOccupied()));
        }
        r.setCell(block.getCell(index));
        rectangleMap.put(block.getCell(index).getID(), r);
        rectangles.add(r);
    }

    private Point2D drawWhite(Point2D start, int[] vector) {
        MyRect r = new MyRect(start.getX(), start.getY(), cellLength, cellLength);
        r.setRotate(45);
        r.setFill(Color.WHITE);
        board.getChildren().add(r);
        return start.add(cellLength / Math.sqrt(2) * vector[0],
                cellLength / Math.sqrt(2) * vector[1]);
    }

    private Point2D drawNormalPathsCells(int num, Point2D start, int index, int[] vector) {
        for (int i = 0; i < num; i++) {
            drawCell(start.getX(),
                    start.getY(),
                    colors[index % 4],
                    controller.getChessBoard().getNormalPath(),
                    index,
                    (r) -> board.getChildren().add(r));
            start = start.add(cellLength / Math.sqrt(2) * vector[0],
                    cellLength / Math.sqrt(2) * vector[1]);
            index++;
        }
        return start;
    }
    private void drawTerminalPathsCells(int num, Point2D start, int index, int[] vector, int j) {
        for (int i = 0; i < num; i++) {
            start = start.add(cellLength / Math.sqrt(2) * vector[0],
                    cellLength / Math.sqrt(2) * vector[1]);
            drawCell(start.getX(),
                    start.getY(),
                    colors[j],
                    controller.getChessBoard().getTerminalPaths()[j],
                    index,
                    (r) -> board.getChildren().add(r));
            index++;
        }
    }

    private void drawBoard() {
        // [  0 1 ] right (x, y) -> (-y, x)
        // [ -1 0 ]
        //NormalPath
        int[] vector = {-1, -1};
        Point2D start = new Point2D(length / 2, length / 2);
        start = start.add(-cellLength * 5 / Math.sqrt(2), cellLength * 9 / Math.sqrt(2));
        int index = 0;
        start = drawNormalPathsCells(6, start, index, vector);
        index += 5;
        turnVector(vector, 1);
        start = drawNormalPathsCells(4, start, index, vector);
        turnVector(vector, -1);
        index += 4;
        start = drawWhite(start, vector);
        for (int i = 0; i < 3; i++) {
            start = drawNormalPathsCells(3, start, index, vector);
            turnVector(vector, 1);
            index += 3;
            start = drawNormalPathsCells(6, start, index, vector);
            turnVector(vector, 1);
            index += 6;
            start = drawNormalPathsCells(4, start, index, vector);
            turnVector(vector, -1);
            index += 4;
            start = drawWhite(start, vector);
        }
        drawNormalPathsCells(4, start, index, vector);

        //TerminalPath
        start = new Point2D(length / 2, length / 2);
        vector = new int[]{-1, -1};
        drawTerminalPathsCells(6, start, 0, vector, 0);

        //Takeoff
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                drawCell(length / 2 - cellLength * 2 * Math.pow(-1, i) * j,
                        length / 2 - cellLength * 2 * Math.pow(-1, j) * i,
                        colors[i * 2 + j],
                        controller.getChessBoard().getTakeoffs()[i * 2 + j],
                        0,
                        (r) -> board.getChildren().add(r)
                );
            }
        }
    }

    private void drawParkingAprons() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    int finalI = i;
                    int finalJ = j;
                    int finalK = k;
                    drawCell(0, 0,
                            colors[i],
                            controller.getChessBoard().getParkingAprons()[i],
                            j * 2 + k,
                            s -> parkingAprons[finalI].add(s, finalJ, finalK)
                    );
                }
            }

        }
    }


    private Circle drawPiece(double x, double y, ArrayList<Piece> pieces) {
        Circle c = new Circle(x, y, cellLength / 4);
        return c;
    }

    public void roll() {
        controller.rollDice();
        System.out.println("Now: " + controller.getCurrentPlayer().getColor());
        System.out.println("Raw: " + controller.getDiceResult().get("raw"));
        System.out.println("Result: " + controller.getDiceResult().get("result"));
        System.out.println(controller.getDiceResult().get("canTakeOff"));
    }
}