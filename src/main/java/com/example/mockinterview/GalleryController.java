package com.example.mockinterview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.ikonli.javafx.FontIcon;
import java.io.IOException;

public class GalleryController {

    @FXML
    protected void onSelectInterviewer(ActionEvent event) throws IOException {
        // This is the code we wrote to switch screens!
        Button clickedButton = (Button) event.getSource();
        VBox card = (VBox) clickedButton.getParent();

        Label nameLabel = (Label) card.getChildren().get(1);
        FontIcon icon = (FontIcon) card.getChildren().get(0);

        String selectedName = nameLabel.getText();
        String selectedIcon = icon.getIconLiteral();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("interview-view.fxml"));
        Parent root = loader.load();

        InterviewController controller = loader.getController();
        controller.initData(selectedName, selectedIcon);

        Stage stage = (Stage) clickedButton.getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
    }
}