package game.icarus;

import java.io.IOException;
import java.util.Map;

import game.icarus.controller.GameController;
import game.icarus.controller.GameSaver;
import game.icarus.entity.Dice;
import game.icarus.entity.Setting;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SecondaryController {
    GameController c;
    GameSaver saver;
    @FXML
    private Label secondaryText;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    public void rollDice() {
        Dice dice = new Dice(3);
        StringBuilder s = new StringBuilder();
        Map<String, Object> m = dice.roll();
        for (int i : (int[]) m.get("raw")) {
            s.append(i).append(" ");
        }
        s.append("\n");
        s.append(m.get("result").toString());
        secondaryText.setText(s.toString());
    }

    public void startGame() {
        c = new GameController(new Setting());
    }

    public void saveGame() {
        GameSaver.saveGame(c.saveGame(), "./save1.json");
    }
}