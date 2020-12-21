package game.icarus.view;

import java.io.IOException;
import java.util.Map;

import game.icarus.App;
import game.icarus.controller.GameController;
import game.icarus.controller.GameSaver;
import game.icarus.entity.Dice;
import game.icarus.entity.DiceResult;
import game.icarus.entity.Setting;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SecondaryController {
    GameController c;
    @FXML
    private Label secondaryText;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    public void rollDice() {
        Dice dice = new Dice(3);
        StringBuilder s = new StringBuilder();
        DiceResult m = dice.roll();
        for (int i : (int[]) m.getRaw()) {
            s.append(i).append(" ");
        }
        s.append("\n");
        s.append(m.getResult().toString());
        secondaryText.setText(s.toString());
    }

    public void startGame() {
        c = new GameController(new Setting());
    }

    public void saveGame() {
        GameSaver.saveGame(c.saveGame("saveaaa"), "./save1.json");
    }
}