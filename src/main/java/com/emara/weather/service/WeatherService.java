package com.emara.weather.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Service
public class WeatherService {
    private static String baseUrl = "https://api.openweathermap.org/data/2.5/weather";

    @Autowired
    GeminiStreamService geminiStreamService;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    public ResponseEntity<?> getCityWeather(String cityKey) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = String.format("%s?q=%s&appid=%s", baseUrl, cityKey, apiKey);
            HttpEntity<String> response = restTemplate.getForEntity(url, String.class);
            
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            String weather = root
                .path("weather")
                .get(0)
                .path("description")
                .asText();
            Integer temp = root
                .path("main")
                .path("feels_like")
                .asInt() - 273;
            String cityName = root
                .path("name")
                .asText();
            int humidity = root
                .path("main")
                .path("humidity")
                .asInt();
            int pressure = root
                .path("main")
                .path("pressure")
                .asInt();
            int windSpeed = root
                .path("wind")
                .path("speed")
                .asInt();
            
            String formattedResponse = String.format(
                "Weather in %s: %s, Temperature: %d°C, Humidity: %d%%, Pressure: %d hPa, Wind Speed: %d m/s",
                cityName, weather, temp, humidity, pressure, windSpeed
            );
            return ResponseEntity.status(200).body(formattedResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error fetching weather data");
        }
    }


    public Flux<String> getWeatherRecommendations(String cityKey) {
        try {
            String weatherData = getCityWeather(cityKey).getBody().toString();
            String prompt = "Based on the following weather data, provide recommendations for outdoor activities and clothing in max 200 words:\n" + weatherData;
            return geminiStreamService.streamGeminiResponse(prompt);
        } catch (Exception e) {
            e.printStackTrace();
            return Flux.just("Error fetching weather recommendations");
        }
    }
}
