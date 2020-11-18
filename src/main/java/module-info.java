module game.icarus {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens game.icarus to javafx.fxml;

    exports game.icarus;
}