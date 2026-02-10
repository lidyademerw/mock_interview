package com.example.mockinterview;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import org.kordamp.ikonli.javafx.FontIcon;

public class InterviewController {
    @FXML private Label statusLabel;
    @FXML private Label interviewerNameLabel;
    @FXML private FontIcon interviewerIcon;
    @FXML private Rectangle bar1, bar2, bar3, bar4, bar5; // Add these variables

    private void startWaveformAnimation() {
        java.util.Random random = new java.util.Random();
        javafx.animation.Timeline timeline = new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(javafx.util.Duration.millis(150), e -> {
                    // This makes the bars jump randomly
                    bar1.setHeight(20 + random.nextInt(20));
                    bar2.setHeight(40 + random.nextInt(30));
                    bar3.setHeight(25 + random.nextInt(25));
                    bar4.setHeight(50 + random.nextInt(35));
                    bar5.setHeight(20 + random.nextInt(20));
                })
        );
        timeline.setCycleCount(javafx.animation.Timeline.INDEFINITE);
        timeline.play();
    }

    private AudioRecorder recorder = new AudioRecorder();
    private AIService aiService = new AIService();
    private String currentRole;

    public void initData(String name, String iconLiteral) {
        this.currentRole = name;
        interviewerNameLabel.setText(name);

        // 3. This is what makes the icon change dynamically!
        interviewerIcon.setIconLiteral(iconLiteral);

        recorder.startRecording();
        //statusLabel.setText("Interviewer is listening...");
        startWaveformAnimation();
    }

    @FXML
    private void handleStopAndProcess() {
        statusLabel.setText("Thinking...");
        recorder.stopRecording();

        // We use a "Task" so the UI doesn't freeze while talking to the AI
        Task<String> aiTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                // For Day 5, we are using a placeholder string.
                String userText = "I am interested in the " + currentRole + " position.";
                return aiService.getAIResponse(userText, currentRole);
            }
        };


        aiTask.setOnSucceeded(e -> {
            statusLabel.setText(aiTask.getValue());
        });


        aiTask.setOnFailed(e -> {
            statusLabel.setText("Error: Check your API Key in AIService.java");
            aiTask.getException().printStackTrace();
        });

        new Thread(aiTask).start();
    }
}