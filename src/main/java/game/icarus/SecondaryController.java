package game.icarus;

import java.io.IOException;
import java.util.Map;

import game.icarus.entity.Dice;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SecondaryController {

    @FXML
    private Label secondaryText;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    public void rollDice() {
        Dice dice = new Dice();
        StringBuilder s = new StringBuilder();
        Map<String, Object> m = dice.roll();
        for (int i : (int[]) m.get("raw")) {
            s.append(i).append(" ");
        }
        s.append("\n");
        s.append(m.get("result").toString());
        secondaryText.setText(s.toString());
    }
}