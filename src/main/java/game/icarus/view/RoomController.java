package game.icarus.view;

import game.icarus.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;

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
    @FXML
    public ToolBar player1Toolbar;
    @FXML
    public ToolBar player2Toolbar;
    @FXML
    public ToolBar player3Toolbar;
    @FXML
    public ToolBar player4Toolbar;
    @FXML
    public ChoiceBox playerNumbers;
    @FXML
    public AnchorPane player3Pane;
    @FXML
    public AnchorPane player4Pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerNumbers.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue.intValue()) {
                case 0:
                    player3Pane.setVisible(false);
                    player4Pane.setVisible(false);
                    break;
                case 1:
                    player3Pane.setVisible(true);
                    player4Pane.setVisible(false);
                    break;
                case 2:
                    player3Pane.setVisible(true);
                    player4Pane.setVisible(true);
                    break;
            }
            System.out.println(newValue.intValue());
            App.getSetting().setPlayerNumber(newValue.intValue() + 2);
        });
        player1UseAI.selectedProperty().addListener((observable, oldValue, newValue) -> {
            App.getSetting().setPlayerAI(0, newValue);
            player1Toolbar.setVisible(newValue);
        });
        player2UseAI.selectedProperty().addListener((observable, oldValue, newValue) -> {
            App.getSetting().setPlayerAI(1, newValue);
            player2Toolbar.setVisible(newValue);
        });
        player3UseAI.selectedProperty().addListener((observable, oldValue, newValue) -> {
            App.getSetting().setPlayerAI(2, newValue);
            player3Toolbar.setVisible(newValue);
        });
        player4UseAI.selectedProperty().addListener((observable, oldValue, newValue) -> {
            App.getSetting().setPlayerAI(3, newValue);
            player4Toolbar.setVisible(newValue);
        });
    }

    public void start() throws IOException {
        App.setRoot("board");
    }

    public void back() throws IOException {
        App.setRoot("primary");
    }
}
