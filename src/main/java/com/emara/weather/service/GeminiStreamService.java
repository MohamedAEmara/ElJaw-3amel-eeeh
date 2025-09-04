package com.emara.weather.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class GeminiStreamService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.stream.url}")
    private String GEMINI_URL;

    public void streamGeminiResponse(String prompt, SseEmitter emitter) {
        try {
            // Build URL with API Key
            String urlStr = GEMINI_URL + "?key=" + apiKey;
            URL url = new URL(urlStr);

            // Open connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            // Request body
            String requestBody = "{ \"contents\":[{\"parts\":[{\"text\":\"" + prompt + "\"}]}]}";
            conn.getOutputStream().write(requestBody.getBytes());

            // Stream response line by line
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("\"text\"")) {
                        // Extract text content from JSON line
                        line = line.trim();
                        int start = line.indexOf(":") + 2; // Skip past ':"'
                        int end = line.lastIndexOf("\"");
                        if (start < end) {
                            line = line.substring(start, end);
                        } else {
                            line = "";
                        }
                        // Send chunk to client
                        emitter.send(line);
                    }
                }
            }

            emitter.complete();
        } catch (Exception e) {
            emitter.completeWithError(e);
        }
    }
}
