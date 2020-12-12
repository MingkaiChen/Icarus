package game.icarus.view;

import game.icarus.App;
import game.icarus.controller.GameController;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BoardController implements Initializable {

    public final static double MAGIC_NUMBER = 1.0;

    public final static int[] CELL_INTERVAL = {2, 3, 3, 2};
    @FXML
    public Pane board;

    private GameController controller;

    @FXML
    private BorderPane root;

    private double length;

    private double cellLength;

    /*private void drawMap() {
        int count = 0;
        int angle = 0;
        double length = min(canvas.getHeight(), canvas.getWidth()) * MAGIC_NUMBER;
        double startX = 0.0;
        double startY = 0.0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Rectangle r = new Rectangle(startX, startY, length, length);

                for (int k = 0; k < CELL_INTERVAL[j]; k++) {
                    
                }
            }
        }
    }*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        board.setBackground(new Background(new BackgroundImage
                (new Image("file:src/main/resources/game/icarus/timg.jfif"),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, false))));
        controller = new GameController(App.getSetting());
        resize();
        Circle c = new Circle(board.getHeight() / 2, board.getWidth() / 2, 1, Color.RED);
        board.getChildren().add(c);
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->
                resize();
        root.heightProperty().addListener(stageSizeListener);
        root.widthProperty().addListener(stageSizeListener);

    }

    public void resize() {
        length = Math.min(root.getHeight() * 0.75, root.getWidth());
        cellLength = length / (10 * Math.sqrt(2));
        board.setPrefHeight(length);
        board.setPrefWidth(length);
        System.out.println(length);
        ArrayList<Node> removeList = new ArrayList<>();
        for (Node n : board.getChildren()) {
            if (n instanceof Rectangle) {
                removeList.add(n);
            } else if (n instanceof Circle) {
                ((Circle) n).setCenterX(length / 2);
                ((Circle) n).setCenterY(length / 2);
            }
        }
        for (Node n : removeList) {
            board.getChildren().remove(n);
        }
        drawBoard();
    }

    private void drawBoard() {
        // [  0 1 ] right (x, y) -> (-y, x)
        // [ -1 0 ]
        Point2D[] endCoordinates = new Point2D[4];
        for (int i = 0; i < 4; i++) {
            endCoordinates[i] = new Point2D(length / 2, length / 2);
        }
        Color[] colors = {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};
        int[][] factors = {{-1, -1}, {1, -1}, {1, 1}, {-1, 1}};
        for (int i = 0; i < 4; i++) {
            //draw TerminalPath
            for (int j = 0; j < 7; j++) {
                endCoordinates[i] = endCoordinates[i].add(cellLength / Math.sqrt(2) * factors[i][0],
                        cellLength / Math.sqrt(2) * factors[i][1]);
                Rectangle r = new Rectangle(
                        endCoordinates[i].getX() - cellLength / 2,
                        endCoordinates[i].getY() - cellLength / 2,
                        cellLength,
                        cellLength);
                r.setFill(colors[i]);
                r.setRotate(45);
                board.getChildren().add(r);
            }
            //draw NormalPath
            int tmp = factors[i][0];
            factors[i][0] = -factors[i][1];
            factors[i][1] = tmp;
            int count = (i + 1) % 4;
            for (int j = 0; j < 3; j++) {
                endCoordinates[i] = endCoordinates[i].add(cellLength / Math.sqrt(2) * factors[i][0],
                        cellLength / Math.sqrt(2) * factors[i][1]);
                Rectangle r = new Rectangle(
                        endCoordinates[i].getX() - cellLength / 2,
                        endCoordinates[i].getY() - cellLength / 2,
                        cellLength,
                        cellLength);
                r.setFill(colors[count]);
                count = (count + 1) % 4;
                r.setRotate(45);
                board.getChildren().add(r);
            }
            tmp = factors[i][0];
            factors[i][0] = -factors[i][1];
            factors[i][1] = tmp;
            for (int j = 0; j < 3; j++) {
                endCoordinates[i] = endCoordinates[i].add(cellLength / Math.sqrt(2) * factors[i][0],
                        cellLength / Math.sqrt(2) * factors[i][1]);
                Rectangle r = new Rectangle(
                        endCoordinates[i].getX() - cellLength / 2,
                        endCoordinates[i].getY() - cellLength / 2,
                        cellLength,
                        cellLength);
                r.setFill(colors[count]);
                count = (count + 1) % 4;
                r.setRotate(45);
                board.getChildren().add(r);
            }
            for (int j = 0; j < 1; j++) {
                endCoordinates[i] = endCoordinates[i].add(cellLength / Math.sqrt(2) * factors[i][0],
                        cellLength / Math.sqrt(2) * factors[i][1]);
                Rectangle r = new Rectangle(
                        endCoordinates[i].getX() - cellLength / 2,
                        endCoordinates[i].getY() - cellLength / 2,
                        cellLength,
                        cellLength);
                r.setFill(Color.WHITE);
                r.setRotate(45);
                board.getChildren().add(r);
            }
            tmp = -factors[i][0];
            factors[i][0] = factors[i][1];
            factors[i][1] = tmp;
            for (int j = 0; j < 4; j++) {
                endCoordinates[i] = endCoordinates[i].add(cellLength / Math.sqrt(2) * factors[i][0],
                        cellLength / Math.sqrt(2) * factors[i][1]);
                Rectangle r = new Rectangle(
                        endCoordinates[i].getX() - cellLength / 2,
                        endCoordinates[i].getY() - cellLength / 2,
                        cellLength,
                        cellLength);
                r.setFill(colors[count]);
                count = (count + 1) % 4;
                r.setRotate(45);
                board.getChildren().add(r);
            }
            tmp = factors[i][0];
            factors[i][0] = -factors[i][1];
            factors[i][1] = tmp;
            for (int j = 0; j < 2; j++) {
                endCoordinates[i] = endCoordinates[i].add(cellLength / Math.sqrt(2) * factors[i][0],
                        cellLength / Math.sqrt(2) * factors[i][1]);
                Rectangle r = new Rectangle(
                        endCoordinates[i].getX() - cellLength / 2,
                        endCoordinates[i].getY() - cellLength / 2,
                        cellLength,
                        cellLength);
                r.setFill(colors[count]);
                count = (count + 1) % 4;
                r.setRotate(45);
                board.getChildren().add(r);
            }
        }
    }

    public void drawRectangle(ActionEvent actionEvent) {
        Rectangle r = new Rectangle(length / 2 - cellLength / 2, length / 2 - cellLength / 2, cellLength, cellLength);
        //Rectangle rr = new Rectangle(length/2,length/2, cellLength, cellLength);
        r.setRotate(45);
        board.getChildren().add(r);
        //board.getChildren().add(rr);
    }
}