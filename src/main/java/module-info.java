module com.example.mockinterview {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    // This matches the new Gemini library
    requires dev.langchain4j.googleai.gemini;
    requires langchain4j.google.ai.gemini;

    opens com.example.mockinterview to javafx.fxml;
    exports com.example.mockinterview;
}