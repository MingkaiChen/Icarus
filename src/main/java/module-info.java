module me.alvis {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens me.alvis to javafx.fxml;
    exports me.alvis;
}