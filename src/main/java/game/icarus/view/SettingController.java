package game.icarus.view;

import game.icarus.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {

    @FXML
    public ChoiceBox<String> useTheme;
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
        useTheme.setValue((App.getTheme().isShowTile()) ? "Simple" : "Default");
        musicVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
            App.musicVolume = newValue.intValue();
            musicValue.setText(String.valueOf(newValue.intValue()));
            App.bgmPlayer.setVolume(newValue.doubleValue());
        });
        soundVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
            App.soundVolume = newValue.intValue();
            soundValue.setText(String.valueOf(newValue.intValue()));
        });
        debugMode.selectedProperty().addListener((observable, oldValue, newValue) -> App.isDebug = newValue);
        fullscreen.selectedProperty().addListener((observable, oldValue, newValue) -> {
            App.isFullscreen = newValue;
            App.fullscreen(newValue);
        });
        useTheme.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> App.useTheme(newValue.intValue()));
    }

    public void back() throws IOException {
        App.setRoot("primary");
    }

}
