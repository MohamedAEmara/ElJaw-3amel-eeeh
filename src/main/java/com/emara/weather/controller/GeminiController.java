package com.emara.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.emara.weather.service.GeminiService;
import com.emara.weather.service.GeminiStreamService;
import java.io.IOException;

@RestController
@RequestMapping("/gemini")
public class GeminiController {
    @Autowired
    private GeminiService geminiService;

    @Autowired
    private GeminiStreamService geminiStreamService;

    @PostMapping("/chat")
    public String chat(@RequestBody String message) throws IOException {
        return geminiService.generateContent(message);
    }

    @GetMapping("/stream")
    public SseEmitter streamGemini(@RequestParam String prompt) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        new Thread(() -> {
            geminiStreamService.streamGeminiResponse(prompt, emitter);
        }).start();

        return emitter;
    }
}