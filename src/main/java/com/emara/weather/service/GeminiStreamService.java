package com.emara.weather.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
public class GeminiStreamService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.stream.url}")
    private String GEMINI_URL;

    private final WebClient webClient;

    public GeminiStreamService() {
        this.webClient = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)) // 10MB buffer
                .build();
    }

    public Flux<String> streamGeminiResponse(String prompt) {
        // Build URL with API Key
        String urlStr = GEMINI_URL + "?key=" + apiKey;
        
        // Request body
        String requestBody = "{ \"contents\":[{\"parts\":[{\"text\":\"" + prompt + "\"}]}]}";

        return webClient.post()
                .uri(urlStr)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToFlux(String.class)
                .flatMap(this::processResponseLine)
                .filter(line -> !line.isEmpty())
                .onErrorResume(throwable -> {
                    return Flux.just("Error: " + throwable.getMessage());
                })
                .timeout(Duration.ofMinutes(5)); // Add timeout for safety
    }

    private Flux<String> processResponseLine(String line) {
        if (line.contains("\"text\"")) {
            line = line.trim();
            int start = line.indexOf(":") + 2;
            int end = line.lastIndexOf("\"");
            if (start < end) {
                String extractedText = line.substring(start, end);
                return Flux.just(extractedText);
            }
        }
        return Flux.empty(); 
    }
}
