package game.icarus.view;

import game.icarus.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RoomController implements Initializable {
    @FXML
    public CheckBox player1UseAI;
    @FXML
    public CheckBox player2UseAI;
    @FXML
    public CheckBox player3UseAI;
    @FXML
    public CheckBox player4UseAI;
    @FXML
    public ChoiceBox<String> player1AIDifficulty;
    @FXML
    public ChoiceBox<String> player2AIDifficulty;
    @FXML
    public ChoiceBox<String> player3AIDifficulty;
    @FXML
    public ChoiceBox<String> player4AIDifficulty;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        player1UseAI.selectedProperty().addListener((observable, oldValue, newValue) -> {
            App.getSetting().setPlayerAI(0, newValue);
        });
        player2UseAI.selectedProperty().addListener((observable, oldValue, newValue) -> {
            App.getSetting().setPlayerAI(1, newValue);
        });
        player3UseAI.selectedProperty().addListener((observable, oldValue, newValue) -> {
            App.getSetting().setPlayerAI(2, newValue);
        });
        player4UseAI.selectedProperty().addListener((observable, oldValue, newValue) -> {
            App.getSetting().setPlayerAI(3, newValue);
        });
    }

    public void start(ActionEvent actionEvent) throws IOException {
        App.setRoot("board");
    }

    public void back(ActionEvent actionEvent) throws IOException {
        App.setRoot("primary");
    }
}
