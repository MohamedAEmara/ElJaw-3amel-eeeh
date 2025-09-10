package com.emara.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.RestController;

import com.emara.weather.service.WeatherService;

import reactor.core.publisher.Flux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/weather")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/{cityKey}")
    public Mono<String> getCityWeather(@PathVariable String cityKey, @AuthenticationPrincipal OidcUser oidcUser) {
        return Mono.fromCallable(() -> {
            System.out.println("City Key: " + cityKey);
            System.out.println("User: " + oidcUser.getEmail());
            ResponseEntity<?> response = weatherService.getCityWeather(cityKey);
            if (response == null || !response.getStatusCode().is2xxSuccessful()) {
                return "No data found!";
            }
            return response.getBody().toString();
        });
    }

    @GetMapping("/recommendations/{cityKey}")
    public Flux<String> getRecommendations(@PathVariable String cityKey, @AuthenticationPrincipal OidcUser oidcUser) {
        System.out.println("Recommendations requested for city: " + cityKey);
        return weatherService.getWeatherRecommendations(cityKey);
    }
}
