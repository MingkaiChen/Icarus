module game.icarus {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires fastjson;
    requires java.sql;
    requires javafx.media;

    opens game.icarus to fastjson, javafx.fxml;
    opens game.icarus.view to javafx.fxml;
    opens game.icarus.entity to fastjson;
    opens game.icarus.map to fastjson;

    exports game.icarus;
    exports game.icarus.entity;
    exports game.icarus.map;
    exports game.icarus.attribute;
    exports game.icarus.view;
    exports game.icarus.controller;
}