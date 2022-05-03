module library {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens library to javafx.fxml, java.base, javafx.graphics;
    opens library.controllers to javafx.fxml, javafx.base, javafx.graphics;
    opens library.domain to javafx.fxml, javafx.base, javafx.graphics;
    opens library.repository to javafx.fxml, javafx.base, javafx.graphics;

    exports library;
}