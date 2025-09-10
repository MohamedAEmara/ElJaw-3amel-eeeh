package com.emara.weather.service;

import com.emara.weather.dto.UserDTO;
import com.emara.weather.entity.History;
import com.emara.weather.entity.User;
import com.emara.weather.repository.UserRepository;
import com.google.api.gax.rpc.NotFoundException;
import com.google.api.gax.rpc.StatusCode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {
    private static final int DAILY_QUOTA = 5;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoryService historyService;

    public User findByUserId(String userId) throws Exception {
        Optional<User> userOpt = userRepository.findByUserId(userId);
        if(userOpt.isPresent()) {
            return userOpt.get();
        } else {
            throw new Exception("User not found!");
        }
    }
    @Transactional
    public User saveOrUpdateUser(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findByUserId(userDTO.getUserId());
        User user;
        if (existingUser.isPresent()) {
            user = existingUser.get();
        } else {
            user = new User();
            user.setUserId(userDTO.getUserId());
        }
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setImageUrl(userDTO.getImageUrl());
        return userRepository.save(user);
    }


    public boolean isQuotaExceeded(User user) {
        return historyService.countHistoryByDay(user.getId()) >= DAILY_QUOTA;
    }

    @Transactional
    public void logSearch(User user, String content) {
        historyService.logSearch(user, content);
    }
}
