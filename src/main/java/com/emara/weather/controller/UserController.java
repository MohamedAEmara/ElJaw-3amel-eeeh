package com.emara.weather.controller;

import com.emara.weather.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private HistoryService historyService;

    @GetMapping("/{userId}")
    public Integer countHistory(@PathVariable Long userId) {
        return historyService.countHistoryByDay(userId);
    }
}
