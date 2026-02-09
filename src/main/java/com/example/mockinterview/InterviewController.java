package com.example.mockinterview;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.scene.control.Label;

import java.util.Random;

public class InterviewController {
    @FXML
    private Label interviewerNameLabel;
    @FXML
    private FontIcon interviewerIcon;
    @FXML
    private Label statusLabel;
    @FXML private Rectangle bar1, bar2, bar3, bar4, bar5;
    private AudioRecorder recorder = new AudioRecorder();
    public void initData(String name, String iconLiteral) {
        interviewerNameLabel.setText(name);
        interviewerIcon.setIconLiteral(iconLiteral);
        startInterviewFlow();
    }

    private void startInterviewFlow() {
        statusLabel.setText("Listening...");
        recorder.startRecording();
        startWaveformAnimation();
    }

    private void startWaveformAnimation() {
        Random random = new Random();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(150), e -> {
            // Randomly change bar heights to simulate voice movement
            bar1.setHeight(15 + random.nextInt(20));
            bar2.setHeight(25 + random.nextInt(30));
            bar3.setHeight(20 + random.nextInt(25));
            bar4.setHeight(30 + random.nextInt(35));
            bar5.setHeight(15 + random.nextInt(20));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
