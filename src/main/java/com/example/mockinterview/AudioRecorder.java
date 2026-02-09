package com.example.mockinterview;

import javax.sound.sampled.*;
import java.io.*;

public class AudioRecorder {
    private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    private TargetDataLine line;
    private File wavFile = new File("user_voice.wav");

    public void startRecording() {
        try {
            // 1. Define the audio format (Standard for AI APIs)
            AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            // 2. Open the microphone line
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            // 3. Start a background thread to write the data to a file
            Thread recordingThread = new Thread(() -> {
                try (AudioInputStream ais = new AudioInputStream(line)) {
                    AudioSystem.write(ais, fileType, wavFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            recordingThread.start();

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        if (line != null) {
            line.stop();
            line.close();
            System.out.println("Recording saved to: " + wavFile.getAbsolutePath());
        }
    }
}
