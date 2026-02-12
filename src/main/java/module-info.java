module com.example.mockinterview {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.net.http; // This is built-in to Java!

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    opens com.example.mockinterview to javafx.fxml;
    exports com.example.mockinterview;
}