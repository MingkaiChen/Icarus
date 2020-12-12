package game.icarus.view;

import game.icarus.App;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;

import java.io.IOException;

public class SettingController {

    @FXML
    Label musicValue;
    @FXML
    Label soundValue;
    @FXML
    Slider musicVolume;
    @FXML
    Slider soundVolume;
    @FXML
    CheckBox debugMode;

    public void initialize() {
        musicVolume.adjustValue(App.musicVolume);
        soundVolume.adjustValue(App.soundVolume);
        musicValue.setText(String.valueOf(App.musicVolume));
        soundValue.setText(String.valueOf(App.soundVolume));
        debugMode.setSelected(App.isDebug);
        musicVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
            App.musicVolume = newValue.intValue();
            musicValue.setText(String.valueOf(newValue.intValue()));
        });
        soundVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
            App.soundVolume = newValue.intValue();
            soundValue.setText(String.valueOf(newValue.intValue()));
        });
        debugMode.selectedProperty().addListener((observable, oldValue, newValue) -> {
            App.isDebug = newValue;
        });

    }

    public void back() throws IOException {
        App.setRoot("primary");
    }
}
