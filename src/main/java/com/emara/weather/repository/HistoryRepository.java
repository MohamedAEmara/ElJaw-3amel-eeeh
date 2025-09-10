package com.emara.weather.repository;

import com.emara.weather.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface HistoryRepository extends JpaRepository<History, Long> {
    Integer countByUser_IdAndDateBetween(Long userId, Date date1, Date date2);
}
