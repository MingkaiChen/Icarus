package game.icarus.view;

import java.io.IOException;

import game.icarus.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    public void startGame() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    public void openSettings() throws IOException {
        App.setRoot("Settings");
    }

    public void openAddition() {
    }

    public void exit() {
    }
}
