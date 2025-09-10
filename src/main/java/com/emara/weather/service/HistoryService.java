package com.emara.weather.service;

import com.emara.weather.entity.History;
import com.emara.weather.entity.User;
import com.emara.weather.repository.HistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    public Integer countHistoryByDay(Long userId) {
        System.out.println("Count hit: " + userId);
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        return historyRepository.countByUser_IdAndDateBetween(userId, Date.valueOf(today), Date.valueOf(tomorrow));
    }

    @Transactional
    public void logSearch(User user, String content) {
        System.out.println("User in logSearch: " + user);
        History history = new History();
        history.setUser(user);
        history.setContent(content);
        historyRepository.save(history);
    }
}
