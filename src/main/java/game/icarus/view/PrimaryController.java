package game.icarus.view;

import java.io.IOException;

import game.icarus.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class PrimaryController {

    @FXML
    Label title;


    @FXML
    public void startGame() throws IOException {
        App.setRoot("room");
    }

    @FXML
    public void openSettings() throws IOException {
        App.setRoot("Settings");
    }

    public void openAddition() {
    }

    public void exit() {
        Platform.exit();
    }
}
