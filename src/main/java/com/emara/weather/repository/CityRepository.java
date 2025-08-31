package com.emara.weather.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emara.weather.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {
    public List<City> findAll();
}
