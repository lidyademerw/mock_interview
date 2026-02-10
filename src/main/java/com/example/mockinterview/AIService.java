package com.example.mockinterview;

//import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

public class AIService {

    // Paste your FREE Google Key here
    private final String API_KEY = "YOUR_KEY_HERE";

    private GoogleAiGeminiChatModel brain;

    public AIService() {
        this.brain = GoogleAiGeminiChatModel.builder()
                .apiKey(API_KEY)
                .modelName("gemini-1.5-flash") // Flash is free and very fast!
                .build();
    }

    public String getAIResponse(String userSpeech, String interviewerRole) {
        String prompt = "You are a " + interviewerRole + ". " +
                "The user is practicing for an English interview. " +
                "Respond to this statement naturally and ask a follow-up: " +
                userSpeech;

        return brain.generate(prompt).content().text();
    }
}