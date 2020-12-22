package game.icarus.view;

import game.icarus.App;
import game.icarus.controller.GameController;
import game.icarus.controller.GameSaver;
import game.icarus.entity.Block;
import game.icarus.entity.Cell;
import game.icarus.entity.Piece;
import game.icarus.entity.Player;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class BoardController implements Initializable {

    /**
     * FXML FIELDS
     **/

    @FXML
    public StackPane settingPane;
    public Slider musicVolume;
    public Label musicValue;
    public Slider soundVolume;
    public Label soundValue;
    public CheckBox fullscreen;
    public CheckBox debugMove;
    public CheckBox debugTakeOff;
    public CheckBox debugLucky;
    public StackPane endPane;
    public Button debugEndGame;
    public VBox winnerId;
    public Label playerName;
    public Label diceRaws;
    public Label diceResults;
    public Label takeOff;
    public Label lucky;
    public Label luckyTime;
    @FXML
    private Pane board;
    @FXML
    private BorderPane root;

    /**
     * FRONTEND VARIABLES
     **/

    private Point2D midPoint;
    private boolean debugEnd = false;
    private double cellLength;
    private double pieceSize;
    private Color[] colors;
    AudioClip click = new AudioClip(this.getClass().getResource("/game/icarus/sound/click.mp3").toExternalForm());
    private static final Color HIGHLIGHTED_COLOR = new Color(1.0f, 0.84313726f, 0.0f, 0.2f);

    /**
     * HYBRID VARIABLES
     **/

    private final Map<UUID, Rectangle> rectangleMap = new HashMap<>();
    private final List<MyPiece> pieces = new ArrayList<>();
    private GameController controller;


    /**
     * ESSENTIAL METHODS
     **/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         if (App.getTheme().isShowTile()) colors = new Color[]{Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN};
         else colors = new Color[]{Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};
        //Board
        //FIXME: ADD DICE NUMBER SELECTION
        board.setBackground(new Background(new BackgroundImage
                (new Image(App.getTheme().getBackGround()),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, false))));
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> resize();
        root.heightProperty().addListener(stageSizeListener);
        root.widthProperty().addListener(stageSizeListener);

        //Settings
        musicVolume.adjustValue(App.musicVolume);
        soundVolume.adjustValue(App.soundVolume);
        musicValue.setText(String.valueOf(App.musicVolume));
        soundValue.setText(String.valueOf(App.soundVolume));
        fullscreen.setSelected(App.isFullscreen);
        musicVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
            App.musicVolume = newValue.intValue();
            musicValue.setText(String.valueOf(newValue.intValue()));
            App.bgmPlayer.setVolume(newValue.doubleValue());
        });
        soundVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
            App.soundVolume = newValue.intValue();
            soundValue.setText(String.valueOf(newValue.intValue()));
        });
        fullscreen.selectedProperty().addListener((observable, oldValue, newValue) -> {
            App.isFullscreen = newValue;
            App.fullscreen(newValue);
        });
        debugMove.selectedProperty().addListener((observable, oldValue, newValue) -> controller.getDice().setDebugMove(newValue));
        debugTakeOff.selectedProperty().addListener((observable, oldValue, newValue) -> controller.getDice().setDebugTakeOver(newValue));
        debugLucky.selectedProperty().addListener((observable, oldValue, newValue) -> controller.getDice().setDebugLucky(newValue));

        //Controller
        controller = App.startGame();
        controller.isGameChanged().addListener((observable, oldValue, newValue) -> {
            if (newValue) resize();
        });
        resize();
    }

    /**
     * FXML HANDLERS
     **/

    public void saveGame() {
        GameSaver.saveGame(controller.saveGame("Game1"), "./save1.json");
    }

    public void roll() {
        if (controller.isGameEnded()) return;
        if (controller.isWalkable()) return;
        if (!controller.rollDice()) changePlayer();
        diceRaws.setText(Arrays.toString(controller.getDiceResult().getRaw()));
        diceResults.setText(controller.getDiceResult().getResult().toString());
        takeOff.setVisible(controller.getDiceResult().canTakeOff());
        lucky.setVisible(controller.getDiceResult().isLucky());
        luckyTime.setVisible(controller.getDiceResult().isLucky());
        luckyTime.setText(String.valueOf(controller.getLuckyCount()));
    }

    public void settings() {
        if (!App.isDebug) {
            debugMove.setVisible(false);
            debugTakeOff.setVisible(false);
            debugLucky.setVisible(false);
            debugEndGame.setVisible(false);
        }
        if (settingPane.isVisible()) {
            settingPane.setVisible(false);
            board.setEffect(null);
        } else {
            settingPane.setVisible(true);
            setBoardBlur();
        }

    }

    public void quit() throws IOException {
        App.endGame();
        App.setRoot("primary");
    }

    public void endGameButtonHandler() {
        debugEnd = true;
        settings();
        endGame();
    }

    public void restart() {
        App.endGame();
        controller = App.startGame();
        controller.isGameChanged().addListener((observable, oldValue, newValue) -> {
            if (newValue) resize();
        });
        resize();
    }

    /**
     * PURE FRONTEND METHODS
     **/

    interface AddToPaneOperation {
        void add(Shape s);
    }

    public void resize() {
        if (controller.isGameEnded()) {
            endGame();
        }
        changePlayer();
        double maxHeight = Math.max(root.getHeight() * 0.875, root.getHeight() - 100);
        double length = Math.min(maxHeight, root.getWidth());
        System.out.println(length);
        settingPane.setPrefWidth(length);
        cellLength = length / (13 * Math.sqrt(2));
        pieceSize = cellLength * 0.75;
        board.setPrefHeight(length);
        board.setPrefWidth(length);
        midPoint = new Point2D(length / 2, length / 2);
        ArrayList<Node> removeList = new ArrayList<>(board.getChildren());
        for (Node n : removeList) {
            board.getChildren().remove(n);
        }
        pieces.clear();
        drawBoard();
        addAllPieces();
        highlightCells();
        if (controller.getDiceResult() != null) {
            takeOff.setVisible(controller.getDiceResult().canTakeOff());
            lucky.setVisible(controller.getDiceResult().isLucky());
            luckyTime.setVisible(controller.getDiceResult().isLucky());
            luckyTime.setText(String.valueOf(controller.getLuckyCount()));
        }
        controller.finishHandling();
    }

    public void cellHandler(Cell cell) {
        boolean isAction = controller.cellHandler(cell);
        if (isAction) {
            System.out.println("Action");
            click.setVolume(App.soundVolume);
            click.play();
        }
    }

    public void endGame() {
        endPane.setVisible(true);
        setBoardBlur();
        ArrayList<Player> winningPlayers = new ArrayList<>();
        if (debugEnd) {
            for (int i = 0; i < App.getSetting().getPlayerNumber(); i++) {
                winningPlayers.add(controller.getPlayers()[i]);
            }
        } else winningPlayers = controller.getWinningPlayers();
        for (int i = 0; i < winningPlayers.size(); i++) {
            String prefix = Integer.toString(i + 1);
            switch (i + 1) {
                case 1:
                    prefix += "st";
                    break;
                case 2:
                    prefix += "nd";
                    break;
                case 3:
                    prefix += "rd";
                    break;
                case 4:
                    prefix += "th";
                    break;
            }
            Label l = new Label(prefix + ": " + winningPlayers.get(i).toString());
            l.setFont(new Font(32));
            winnerId.getChildren().add(l);
        }
    }

    public void changePlayer() {
        playerName.setText(controller.getCurrentPlayer().getColor().toString());
        playerName.setTextFill(colors[controller.getCurrentPlayer().getColor().ordinal()]);
    }

    public void highlightCells() {
        System.out.println(controller.getHighlightedCells());
        for (Cell c : controller.getHighlightedCells()) {
            Rectangle r = rectangleMap.get(c.getID());
            r.setFill(HIGHLIGHTED_COLOR);
        }
    }


    private void addAllPieces() {
        for (MyPiece p : pieces) {
            board.getChildren().add(p);
        }
    }

    private void turnVector(int[] v, int pos) {
        int tmp = v[0] * pos;
        v[0] = -v[1] * pos;
        v[1] = tmp;
    }

    private void drawCell(Point2D location, Color color, Block block, int index, AddToPaneOperation add) {
        double x = location.getX();
        double y = location.getY();
        MyRect r = new MyRect(x - cellLength / 2, y - cellLength / 2, cellLength, cellLength);
        r.setRotate(45);
        if (App.getTheme().isShowTile()) r.setFill(color);
        else r.setFill(Color.TRANSPARENT);
        r.setOnMouseClicked(mouseEvent -> cellHandler(block.getCell(index)));
        add.add(r);
        if (block.getCell(index).isOccupied()) {
            pieces.addAll(drawPiece(x, y, block.getCell(index).getOccupied()));
        }
        r.setCell(block.getCell(index));
        rectangleMap.put(block.getCell(index).getID(), r);
    }

    private Point2D drawWhite(Point2D start, int[] vector) {
        MyRect r = new MyRect(start.getX() - cellLength / 2, start.getY() - cellLength / 2, cellLength, cellLength);
        r.setRotate(45);
        if (App.getTheme().isShowTile()) r.setFill(Color.WHITE);
        else r.setFill(Color.TRANSPARENT);
        board.getChildren().add(r);
        return start.add(cellLength / Math.sqrt(2) * vector[0],
                cellLength / Math.sqrt(2) * vector[1]);
    }

    private Point2D drawNormalPathsCells(int num, Point2D start, int index, int[] vector) {
        for (int i = 0; i < num; i++) {
            drawCell(start,
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

    private void drawTerminalPathsCells(Point2D start, int[] vector, int j) {
        int index = 5;
        for (int i = 0; i < 6; i++) {
            start = start.add(cellLength / Math.sqrt(2) * vector[0],
                    cellLength / Math.sqrt(2) * vector[1]);
            drawCell(start,
                    colors[j],
                    controller.getChessBoard().getTerminalPaths()[j],
                    index,
                    (r) -> board.getChildren().add(r));
            index--;
        }
    }

    private void drawBoard() {
        // [  0 1 ] right (x, y) -> (-y, x)
        // [ -1 0 ]
        //NormalPath
        int[] vector = {-1, -1};
        Point2D start = midPoint;
        start = start.add(-cellLength * 5 / Math.sqrt(2), cellLength * 9 / Math.sqrt(2));
        int index = 0;
        start = drawNormalPathsCells(5, start, index, vector);
        drawParkingAprons(start, vector, 2);
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
            drawParkingAprons(start, vector, (i + 3) % 4);
            turnVector(vector, 1);
            index += 6;
            start = drawNormalPathsCells(4, start, index, vector);
            turnVector(vector, -1);
            index += 4;
            start = drawWhite(start, vector);
        }
        drawNormalPathsCells(4, start, index, vector);

        //TerminalPath
        start = midPoint;
        drawWhite(start, vector);
        vector = new int[]{1, -1};
        for (int i = 0; i < 4; i++) {
            drawTerminalPathsCells(start, vector, i);
            turnVector(vector, 1);
        }
    }

    private void drawParkingAprons(Point2D start, int[] vector, int color) {
        start = start.add(cellLength / Math.sqrt(2) * vector[0],
                cellLength / Math.sqrt(2) * vector[1]);
        drawCell(start,
                colors[color],
                controller.getChessBoard().getTakeoffs()[color],
                0,
                (r) -> board.getChildren().add(r));
        start = start.add(cellLength * Math.sqrt(2) * (vector[0] - vector[1]) / 2,
                cellLength * Math.sqrt(2) * (vector[0] + vector[1]) / 2);
        for (int i = 0; i < 4; i++) {
            drawCell(start,
                    colors[color],
                    controller.getChessBoard().getParkingAprons()[color],
                    i,
                    (r) -> board.getChildren().add(r));
            start = start.add(cellLength / Math.sqrt(2) * vector[0],
                    cellLength / Math.sqrt(2) * vector[1]);
            turnVector(vector, 1);
        }

    }

    private ArrayList<MyPiece> drawPiece(double x, double y, ArrayList<Piece> pieces) {
        Image image = new Image(App.getTheme().getPieceImages()[pieces.get(0).getColor().ordinal()],
                pieceSize, pieceSize, true, true);
        ArrayList<MyPiece> myPieces = new ArrayList<>();
        double tmpSize = pieceSize;
        switch (pieces.size()) {
            case 1:
                myPieces.add(new MyPiece(x, y, tmpSize, image, pieces.get(0)));
                break;
            case 2:
                tmpSize *= 0.75;
                myPieces.add(new MyPiece(x - tmpSize / 2, y, tmpSize, image, pieces.get(0)));
                myPieces.add(new MyPiece(x + tmpSize / 2, y, tmpSize, image, pieces.get(1)));
                break;
            case 3:
                tmpSize *= 0.5;
                myPieces.add(new MyPiece(x - tmpSize / 2, y - tmpSize / 2, tmpSize, image, pieces.get(0)));
                myPieces.add(new MyPiece(x + tmpSize / 2, y - tmpSize / 2, tmpSize, image, pieces.get(1)));
                myPieces.add(new MyPiece(x, y + tmpSize / 2, tmpSize, image, pieces.get(2)));
                break;
            case 4:
                tmpSize *= 0.5;
                myPieces.add(new MyPiece(x - pieceSize / 2, y - pieceSize / 2, tmpSize, image, pieces.get(0)));
                myPieces.add(new MyPiece(x - pieceSize / 2, y + pieceSize / 2, tmpSize, image, pieces.get(1)));
                myPieces.add(new MyPiece(x + pieceSize / 2, y - pieceSize / 2, tmpSize, image, pieces.get(2)));
                myPieces.add(new MyPiece(x + pieceSize / 2, y + pieceSize / 2, tmpSize, image, pieces.get(3)));
                break;
        }
        return myPieces;
    }

    public void setBoardBlur() {
        ColorAdjust adj = new ColorAdjust(0, 0, 0, -0.5);
        GaussianBlur blur = new GaussianBlur(25); // 55 is just to show edge effect more clearly.
        adj.setInput(blur);
        board.setEffect(adj);
    }
}