module com.example.proiectiss {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.proiectiss to javafx.fxml;
    exports com.example.proiectiss;
}