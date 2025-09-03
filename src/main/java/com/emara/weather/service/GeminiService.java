package com.emara.weather.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@Service
public class GeminiService {
    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${google.gemini.url}")
    private String geminiUrl;

    public String generateContent(String prompt) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestJson = """
                {
                    "contents": [
                        {
                            "parts": [
                                { "text": "%s" }
                            ]
                        }
                    ]
                }
                """.formatted(prompt);

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        String rawResponse = restTemplate.postForEntity(geminiUrl + apiKey, entity, String.class).getBody();

        // Parse JSON:
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(rawResponse);
        String reply = root
                .path("candidates")
                .get(0)
                .path("content")
                .path("parts")
                .get(0)
                .path("text")
                .asText();

        return reply;
    }
}
