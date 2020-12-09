package game.icarus.view;

import game.icarus.App;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;

import java.io.IOException;

public class SettingController {

    @FXML
    Slider musicVolume;
    @FXML
    Slider soundVolume;
    @FXML
    CheckBox debugMode;

    public void initialize() {
        musicVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
            App.musicVolume = newValue.intValue();
        });
        soundVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
            App.soundVolume = newValue.intValue();
        });
        debugMode.selectedProperty().addListener((observable, oldValue, newValue) -> {
            App.isDebug = newValue;
        });

    }

    public void back() throws IOException {
        App.setRoot("primary");
    }
}
