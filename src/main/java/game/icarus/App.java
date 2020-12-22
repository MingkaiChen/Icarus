package game.icarus;

import game.icarus.controller.GameController;
import game.icarus.controller.GameSaver;
import game.icarus.entity.Setting;
import game.icarus.view.Theme;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage stage;
    private static Scene scene;
    public static int musicVolume = 50;
    public static int soundVolume = 50;
    public static boolean isDebug = false;
    public static boolean isFullscreen = false;
    private static Setting setting;
    public static boolean isLoad = false;
    public static MediaPlayer bgmPlayer = new MediaPlayer(new Media(App.class.getResource("/game/icarus/sound/Machiavellian Bach.mp3").toExternalForm()));
    private static GameController controller;
    private final static Theme[] themes = new Theme[2];
    private static int useTheme = 0;

    @Override
    public void start(Stage stage) throws IOException {
        themes[0] = new Theme("file:src/main/resources/game/icarus/maps/default.png",
                new String[]{"file:src/main/resources/game/icarus/icons/yellowPiece.png",
                        "file:src/main/resources/game/icarus/icons/greenPiece.png",
                        "file:src/main/resources/game/icarus/icons/bluePiece.png",
                        "file:src/main/resources/game/icarus/icons/redPiece.png"},
                false
        );
        themes[1] = new Theme("file:src/main/resources/game/icarus/maps/empty.png",
                new String[]{"file:src/main/resources/game/icarus/icons/redPiece.png",
                        "file:src/main/resources/game/icarus/icons/yellowPiece.png",
                        "file:src/main/resources/game/icarus/icons/bluePiece.png",
                        "file:src/main/resources/game/icarus/icons/greenPiece.png"},
                true
        );
        App.stage = stage;
        setting = new Setting(4, 2, true, true);
        scene = new Scene(loadFXML("primary"), 640, 480);
        //scene = new Scene(loadFXML("board"), 640, 480);
        stage.setScene(scene);
        stage.showingProperty().addListener((observable, oldValue, showing) -> {
            if (showing) {
                stage.setMinHeight(stage.getHeight());
                stage.setMinWidth(stage.getWidth());
            }
        });

        bgmPlayer.play();
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static void fullscreen(boolean flag) {
        stage.setFullScreen(flag);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Setting getSetting() {
        return setting;
    }

    public static GameController startGame() {
        if (App.isLoad) {
            try {
                controller = new GameController(GameSaver.loadSave("./save1.json"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else controller = new GameController(App.getSetting());
        return controller;
    }

    public static void endGame() {
        controller = null;
    }

    public static GameController getController() {
        return controller;
    }

    public static Theme getTheme() {
        return themes[useTheme];
    }

    public static void useTheme(int useTheme) {
        App.useTheme = useTheme;
    }
}