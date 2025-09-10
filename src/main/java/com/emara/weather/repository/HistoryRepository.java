package com.emara.weather.repository;

import com.emara.weather.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByUserIdAndDate(Long userId, Date date);
}
