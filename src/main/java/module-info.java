module library {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens library to javafx.fxml;
    opens library.controllers to javafx.fxml;

    exports library;
}