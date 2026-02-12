package com.example.mockinterview;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AIService {
    private final String API_KEY = "YOUR_GROQ_KEY_HERE";
    private final HttpClient client = HttpClient.newHttpClient();

    public String getAIResponse(String userSpeech, String interviewerRole) {
        try {
            // Create the JSON message manually to avoid needing more libraries
            String json = """
                {
                    "model": "llama-3.1-70b-versatile",
                    "messages": [
                        {"role": "system", "content": "You are a %s. Response naturally and ask a follow-up."},
                        {"role": "user", "content": "%s"}
                    ]
                }
                """.formatted(interviewerRole, userSpeech);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.groq.com/openai/v1/chat/completions"))
                    .header("Authorization", "Bearer " + API_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Simple way to grab the text without a JSON library
            String body = response.body();
            int start = body.indexOf("\"content\": \"") + 12;
            int end = body.indexOf("\"", start);
            return body.substring(start, end).replace("\\n", "\n");

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Connection failed. Check your internet!";
        }
    }
}