package game.icarus;

import game.icarus.entity.Setting;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static int musicVolume = 50;
    public static int soundVolume = 50;
    public static boolean isDebug = false;
    private static Setting setting;

    @Override
    public void start(Stage stage) throws IOException {
        setting = new Setting();
        Font.loadFont(getClass().getResourceAsStream("game/icarus/fonts/airstrikeb3d.ttf"), -1);
        scene = new Scene(loadFXML("primary"), 600, 400);
        stage.setScene(scene);
        stage.showingProperty().addListener((observable, oldValue, showing) -> {
            if(showing) {
                stage.setMinHeight(stage.getHeight());
                stage.setMinWidth(stage.getWidth());
                //stage.setTitle("My mininal size is: W"+ stage.getMinWidth()+" H"+stage.getMinHeight());
            }
        });
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
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
}