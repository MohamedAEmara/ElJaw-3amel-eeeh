package com.emara.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ai.autoconfigure.vertexai.gemini.VertexAiGeminiAutoConfiguration;

@SpringBootApplication(exclude = {VertexAiGeminiAutoConfiguration.class})
public class WeatherRecommendationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherRecommendationsApplication.class, args);
	}

}
