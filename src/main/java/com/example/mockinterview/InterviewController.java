package com.example.mockinterview;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;

public class InterviewController {
    @FXML
    private Label statusLabel;
    @FXML
    private Label interviewerNameLabel;
    @FXML
    private FontIcon interviewerIcon;
    @FXML
    private Rectangle bar1, bar2, bar3, bar4, bar5; // Add these variables

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
        statusLabel.setText("Processing your voice...");
        recorder.stopRecording();

        // We use a "Task" so the UI doesn't freeze while talking to the AI
        Task<String> interactionTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                java.io.File audioFile = new java.io.File("user_voice.wav");
                String userSpeech = aiService.transcribeAudio(audioFile);
                updateMessage("You said: " + userSpeech);
                return aiService.getAIResponse(userSpeech, currentRole);
            }

        };


        interactionTask.setOnSucceeded(e -> {
            String aiResponse = interactionTask.getValue();
            statusLabel.setText("AI: " + aiResponse);


            aiService.speak(aiResponse);

            //  Wait 2 seconds and start listening for the user's NEXT answer
            javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(5));
            pause.setOnFinished(event -> {
                statusLabel.setText("Interviewer is listening again...");
                recorder.startRecording();
            });
            pause.play();
        });
    }
}
