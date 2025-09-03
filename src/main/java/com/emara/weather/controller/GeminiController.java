package com.emara.weather.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emara.weather.service.GeminiService;


@RestController
@RequestMapping("/gemini")
public class GeminiController {
    @Autowired
    private GeminiService geminiService;

    @PostMapping("/chat")
    public String chat(@RequestBody String message) throws IOException {
        String reply = geminiService.generateContent(message);
        return reply;
    }
}
