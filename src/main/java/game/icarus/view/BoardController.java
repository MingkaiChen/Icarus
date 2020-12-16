package game.icarus.view;

import game.icarus.App;
import game.icarus.controller.GameController;
import game.icarus.entity.Block;
import game.icarus.entity.Cell;
import game.icarus.entity.Piece;
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

import java.net.URL;
import java.util.*;

public class BoardController implements Initializable {

    public final static double MAGIC_NUMBER = 1.0;

    private Map<UUID, Rectangle> rectangleMap = new HashMap<>();
    private List<Rectangle> rectangles = new ArrayList<>();

    private static Color HIGHLIGHTED_COLOR = new Color(1.0f, 0.84313726f, 0.0f, 0.2f);

    public final static int[] CELL_INTERVAL = {2, 3, 3, 2};
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

    private Color[] colors = {Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN};

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
        System.out.println(cell.getColor());
        if (cell.isOccupied()) {
            System.out.println("occupied");
            controller.selectPiece(cell);
            highlightCells();
            return;
        }
        for (Cell c : controller.getHighlightedCells()) {
            if (cell.equals(c)) {
                controller.movePiece(cell);
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
                System.out.println(n);
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

    private int[] turnVector(int[] v, int pos) {
        int tmp = v[0] * pos;
        v[0] = -v[1] * pos;
        v[1] = tmp;
        return v;
    }

    private Rectangle drawCell(double x, double y, Color color, Block block, int index) {
        Rectangle r = new Rectangle(x, y, cellLength, cellLength);
        r.setRotate(45);
        r.setFill(color);
        r.setOnMouseClicked(mouseEvent -> {
            cellHandler(block.getCell(index));
        });
        if (block.getCell(index).isOccupied()) {
            //drawPiece(x, y, block.getCell(index).getOccupied());
        }
        //rectangleMap.put(block.getCell(index).getID(), r);
        //rectangles.add(r);
        return r;
    }
    private Point2D drawWhite(Point2D start, int[] vector) {
        Rectangle r = new Rectangle(start.getX(), start.getY(), cellLength, cellLength);
        r.setRotate(45);
        r.setFill(Color.WHITE);
        board.getChildren().add(r);
        return start.add(cellLength / Math.sqrt(2) * vector[0],
                cellLength / Math.sqrt(2) * vector[1]);
    }

    private Point2D drawCells(int num, Point2D start, int index, int[] vector) {
        for (int i = 0; i < num; i++) {
            board.getChildren().add(drawCell(start.getX(),
                    start.getY(),
                    colors[index % 4],
                    controller.getChessBoard().getNormalPath(),
                    index));
            start = start.add(cellLength / Math.sqrt(2) * vector[0],
                    cellLength / Math.sqrt(2) * vector[1]);
            index++;
        }
        return start;
    }

    private void drawBoard() {
        // [  0 1 ] right (x, y) -> (-y, x)
        // [ -1 0 ]
        //NormalPath
        int[] vector = {-1, -1};
        Point2D start = new Point2D(length / 2, length / 2);
        start = start.add(-cellLength * 6 / Math.sqrt(2), cellLength * 8 / Math.sqrt(2));
        Rectangle r = new Rectangle(
                start.getX() - cellLength / 2,
                start.getY() - cellLength / 2,
                cellLength,
                cellLength);
        r.setRotate(45);
        r.setFill(Color.RED);
        board.getChildren().add(r);
        int index = 0;
        start = drawCells(5, start, index, vector);
        index += 5;
        turnVector(vector, 1);
        start = drawCells(4, start, index, vector);
        turnVector(vector, -1);
        index += 4;
        start = drawWhite(start, vector);
        for (int i = 0; i < 3; i++) {
            start = drawCells(3, start, index, vector);
            turnVector(vector, 1);
            index += 3;
            start = drawCells(6, start, index, vector);
            turnVector(vector, 1);
            index += 6;
            start = drawCells(4, start, index, vector);
            turnVector(vector, -1);
            index += 4;
            start = drawWhite(start, vector);
        }
        drawCells(4, start, index, vector);

        //TermainalPath
    }

    private void drawParkingAprons() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    Rectangle r = drawCell(0, 0, colors[i], controller.getChessBoard().getParkingAprons()[i], j * 2 + k);
                    parkingAprons[i].add(r, j, k);
                }
            }

        }
    }


    private void drawPiece(double x, double y, ArrayList<Piece> pieces) {
        Circle c = new Circle(x, y, cellLength / 4);
        board.getChildren().add(c);
    }

    public void drawRectangle(ActionEvent actionEvent) {
        Rectangle r = new Rectangle(length / 2 - cellLength / 2, length / 2 - cellLength / 2, cellLength, cellLength);
        //Rectangle rr = new Rectangle(length/2,length/2, cellLength, cellLength);
        r.setRotate(45);
        board.getChildren().add(r);
        //board.getChildren().add(rr);
    }
}