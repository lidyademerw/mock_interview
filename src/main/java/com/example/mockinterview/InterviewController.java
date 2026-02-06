package com.example.mockinterview;

import javafx.fxml.FXML;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.scene.control.Label;
public class InterviewController {
    @FXML
    private Label interviewerNameLabel;
    @FXML
    private FontIcon interviewerIcon;
    @FXML
    private Label statusLabel;
    public void initData(String name, String iconLiteral) {
        interviewerNameLabel.setText(name);
        interviewerIcon.setIconLiteral(iconLiteral);
    }
}
