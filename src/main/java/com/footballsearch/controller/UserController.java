package com.footballsearch.controller;

import com.footballsearch.model.User;
import com.footballsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password) {

        Map<String, Object> response = new HashMap<>();

        try {
            User user = userService.createUser(username, email, password);
            response.put("success", true);
            response.put("message", "User registered successfully");
            response.put("userId", user.getId());
            response.put("username", user.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();

        Optional<User> userOpt = userService.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            response.put("success", true);
            response.put("user", createUserResponse(user));
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Map<String, Object>> getUserByUsername(@PathVariable String username) {
        Map<String, Object> response = new HashMap<>();

        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            response.put("success", true);
            response.put("user", createUserResponse(user));
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> response = new HashMap<>();

        List<User> users = userService.getAllUsers();
        response.put("success", true);
        response.put("count", users.size());
        response.put("users", users.stream()
                .map(this::createUserResponse)
                .toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/leaderboard/total")
    public ResponseEntity<Map<String, Object>> getTotalScoreLeaderboard() {
        Map<String, Object> response = new HashMap<>();

        List<User> topUsers = userService.getLeaderboardByTotalScore();
        response.put("success", true);
        response.put("type", "Total Score Leaderboard");
        response.put("leaderboard", topUsers.stream()
                .map(this::createLeaderboardEntry)
                .toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/leaderboard/weekly")
    public ResponseEntity<Map<String, Object>> getWeeklyScoreLeaderboard() {
        Map<String, Object> response = new HashMap<>();

        List<User> topUsers = userService.getLeaderboardByWeeklyScore();
        response.put("success", true);
        response.put("type", "Weekly Score Leaderboard");
        response.put("leaderboard", topUsers.stream()
                .map(this::createLeaderboardEntry)
                .toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/leaderboard/football-points")
    public ResponseEntity<Map<String, Object>> getFootballPointsLeaderboard() {
        Map<String, Object> response = new HashMap<>();

        List<User> topUsers = userService.getLeaderboardByFootballPoints();
        response.put("success", true);
        response.put("type", "Football Points Leaderboard");
        response.put("leaderboard", topUsers.stream()
                .map(this::createLeaderboardEntry)
                .toList());

        return ResponseEntity.ok(response);
    }

    // Helper methods
    private Map<String, Object> createUserResponse(User user) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", user.getId());
        userMap.put("username", user.getUsername());
        userMap.put("email", user.getEmail());
        userMap.put("totalScore", user.getTotalScore());
        userMap.put("weeklyScore", user.getWeeklyScore());
        userMap.put("footballPoints", user.getFootballPoints());
        userMap.put("gamesPlayed", user.getGamesPlayed());
        userMap.put("gamesWon", user.getGamesWon());
        userMap.put("isAdmin", user.getIsAdmin());
        userMap.put("createdAt", user.getCreatedAt());
        userMap.put("lastActive", user.getLastActive());
        return userMap;
    }

    private Map<String, Object> createLeaderboardEntry(User user) {
        Map<String, Object> entry = new HashMap<>();
        entry.put("username", user.getUsername());
        entry.put("totalScore", user.getTotalScore());
        entry.put("weeklyScore", user.getWeeklyScore());
        entry.put("footballPoints", user.getFootballPoints());
        entry.put("gamesPlayed", user.getGamesPlayed());
        entry.put("gamesWon", user.getGamesWon());
        return entry;
    }
}