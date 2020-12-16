package game.icarus.view;

import game.icarus.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {

    @FXML
    CheckBox fullscreen;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        musicVolume.adjustValue(App.musicVolume);
        soundVolume.adjustValue(App.soundVolume);
        musicValue.setText(String.valueOf(App.musicVolume));
        soundValue.setText(String.valueOf(App.soundVolume));
        debugMode.setSelected(App.isDebug);
        fullscreen.setSelected(App.isFullscreen);
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
        fullscreen.selectedProperty().addListener((observable, oldValue, newValue) -> {
            App.isFullscreen = newValue;
            App.fullscreen(newValue);
        });
    }

    public void back() throws IOException {
        App.setRoot("primary");
    }

}
