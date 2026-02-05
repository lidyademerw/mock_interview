module com.example.mockinterview {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    requires org.kordamp.ikonli.fontawesome5;
    requires java.desktop;

    opens com.example.mockinterview to javafx.fxml;
    exports com.example.mockinterview;

}