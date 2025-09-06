package com.emara.weather.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emara.weather.entity.City;
import com.emara.weather.repository.CityRepository;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public List<City> findAll() {
        return cityRepository.findAll(); 
    }

    public City findById(Long id) {
        return cityRepository.findById(id).orElse(null);
    }
}
