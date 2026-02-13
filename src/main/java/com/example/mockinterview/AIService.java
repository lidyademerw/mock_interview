package com.example.mockinterview;
import java.io.File;
import java.nio.file.Files;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AIService {
    private final String API_KEY = "YOUR_GROQ_KEY_HERE";
    private final HttpClient client = HttpClient.newHttpClient();
    public void speak(String text) {
        try {
            // We use a free TTS URL (Google's public Translate TTS)
            String url = "https://translate.google.com/translate_tts?ie=UTF-8&client=tw-ob&tl=en&q=" +
                    java.net.URLEncoder.encode(text, "UTF-8");

            Media hit = new Media(url);
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();

            System.out.println("AI is speaking...");
        } catch (Exception e) {
            System.out.println("Voice failed, but here is the text: " + e.getMessage());
        }
    }

    public String transcribeAudio(File audioFile) {
        try {
            String boundary = "Boundary-" + UUID.randomUUID().toString();

            // This is the "Box" we put the audio file in
            byte[] fileContent = Files.readAllBytes(audioFile.toPath());
            String head = "--" + boundary + "\r\n" +
                    "Content-Disposition: form-data; name=\"file\"; filename=\"" + audioFile.getName() + "\"\r\n" +
                    "Content-Type: audio/wav\r\n\r\n";
            String mid = "\r\n--" + boundary + "\r\n" +
                    "Content-Disposition: form-data; name=\"model\"\r\n\r\n" +
                    "whisper-large-v3\r\n";
            String tail = "--" + boundary + "--\r\n";

            byte[] body = concatenate(head.getBytes(), fileContent, mid.getBytes(), tail.getBytes());


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.groq.com/openai/v1/audio/transcriptions"))
                    .header("Authorization", "Bearer " + API_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Simple way to grab the text without a JSON library
            String bodyText = response.body();
            int start = bodyText.indexOf("\"Text\": \"") + 8;
            int end = bodyText.lastIndexOf("\"");
            return bodyText.substring(start, end);

        } catch (Exception e) {
            e.printStackTrace();
            return "Could not understand audio.";
        }
    }

    private byte[] concatenate(byte[]... arrays){
        int totalLength = 0;
        for (byte[] array : arrays) totalLength += array.length;
        byte[] result = new byte[totalLength];
        int currentIndex = 0;
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, result, currentIndex, array.length);
            currentIndex += array.length;
        }
        return result;
    }


    public String getAIResponse(String userSpeech, String currentRole) {
        return null;
    }
}