package com.emara.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.emara.weather.service.WeatherService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/weather")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/{cityKey}")
    public String getMethodName(@PathVariable String cityKey) {
        System.out.println("City Key: " + cityKey);
        ResponseEntity<?> response = weatherService.getCityWeather(cityKey);
        if(response == null || !response.getStatusCode().is2xxSuccessful()) {
            return "No data found";
        }
        System.out.println("Response: " + response);
        return response.getBody().toString();
    }
}
