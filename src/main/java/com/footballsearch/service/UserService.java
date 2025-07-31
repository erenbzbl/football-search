package com.footballsearch.service;

import com.footballsearch.model.User;
import com.footballsearch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(String username, String email, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedAt(LocalDateTime.now());
        user.setLastActive(LocalDateTime.now());

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUserScore(String userId, int scoreToAdd) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setTotalScore(user.getTotalScore() + scoreToAdd);
            user.setWeeklyScore(user.getWeeklyScore() + scoreToAdd);
            user.setLastActive(LocalDateTime.now());
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found");
    }

    public User incrementGamesPlayed(String userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setGamesPlayed(user.getGamesPlayed() + 1);
            user.setLastActive(LocalDateTime.now());
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found");
    }

    public User incrementGamesWon(String userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setGamesWon(user.getGamesWon() + 1);
            user.setLastActive(LocalDateTime.now());
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found");
    }

    public User addFootballPoints(String userId, int points) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setFootballPoints(user.getFootballPoints() + points);
            user.setLastActive(LocalDateTime.now());
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found");
    }

    public List<User> getLeaderboardByTotalScore() {
        return userRepository.findTopPlayersByTotalScore();
    }

    public List<User> getLeaderboardByWeeklyScore() {
        return userRepository.findTopPlayersByWeeklyScore();
    }

    public List<User> getLeaderboardByFootballPoints() {
        return userRepository.findTopPlayersByFootballPoints();
    }

    public void resetWeeklyScores() {
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            user.setWeeklyScore(0);
        }
        userRepository.saveAll(allUsers);
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}