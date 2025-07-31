package com.footballsearch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Document(collection = "games")
public class Game {

    @Id
    private String id;

    private String gameCode;
    private String team1Id;
    private String team2Id;
    private List<GamePlayer> players = new ArrayList<>();
    private Integer currentQuestion = 1;
    private String gameStatus = "waiting"; // waiting, playing, finished
    private LocalDateTime questionStartTime;
    private LocalDateTime gameStartTime;
    private LocalDateTime gameEndTime;
    private String winnerId;
    private List<String> usedAnswers = new ArrayList<>();

    public Game() {}

    public Game(String team1Id, String team2Id) {
        this.team1Id = team1Id;
        this.team2Id = team2Id;
        this.gameCode = generateGameCode();
        this.gameStartTime = LocalDateTime.now();
    }

    private String generateGameCode() {
        return "GAME" + System.currentTimeMillis();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getGameCode() { return gameCode; }
    public void setGameCode(String gameCode) { this.gameCode = gameCode; }

    public String getTeam1Id() { return team1Id; }
    public void setTeam1Id(String team1Id) { this.team1Id = team1Id; }

    public String getTeam2Id() { return team2Id; }
    public void setTeam2Id(String team2Id) { this.team2Id = team2Id; }

    public List<GamePlayer> getPlayers() { return players; }
    public void setPlayers(List<GamePlayer> players) { this.players = players; }

    public Integer getCurrentQuestion() { return currentQuestion; }
    public void setCurrentQuestion(Integer currentQuestion) { this.currentQuestion = currentQuestion; }

    public String getGameStatus() { return gameStatus; }
    public void setGameStatus(String gameStatus) { this.gameStatus = gameStatus; }

    public LocalDateTime getQuestionStartTime() { return questionStartTime; }
    public void setQuestionStartTime(LocalDateTime questionStartTime) { this.questionStartTime = questionStartTime; }

    public LocalDateTime getGameStartTime() { return gameStartTime; }
    public void setGameStartTime(LocalDateTime gameStartTime) { this.gameStartTime = gameStartTime; }

    public LocalDateTime getGameEndTime() { return gameEndTime; }
    public void setGameEndTime(LocalDateTime gameEndTime) { this.gameEndTime = gameEndTime; }

    public String getWinnerId() { return winnerId; }
    public void setWinnerId(String winnerId) { this.winnerId = winnerId; }

    public List<String> getUsedAnswers() { return usedAnswers; }
    public void setUsedAnswers(List<String> usedAnswers) { this.usedAnswers = usedAnswers; }

    // Helper methods
    public static class GamePlayer {
        private String userId;
        private String username;
        private LocalDateTime joinedAt;
        private Integer currentScore = 0;
        private List<Answer> answers = new ArrayList<>();

        public GamePlayer() {}

        public GamePlayer(String userId, String username) {
            this.userId = userId;
            this.username = username;
            this.joinedAt = LocalDateTime.now();
        }

        // Getters and Setters
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public LocalDateTime getJoinedAt() { return joinedAt; }
        public void setJoinedAt(LocalDateTime joinedAt) { this.joinedAt = joinedAt; }

        public Integer getCurrentScore() { return currentScore; }
        public void setCurrentScore(Integer currentScore) { this.currentScore = currentScore; }

        public List<Answer> getAnswers() { return answers; }
        public void setAnswers(List<Answer> answers) { this.answers = answers; }
    }

    public static class Answer {
        private Integer questionNumber;
        private String answer;
        private Boolean isCorrect;
        private Long responseTime;
        private Integer points;

        public Answer() {}

        public Answer(Integer questionNumber, String answer, Boolean isCorrect, Long responseTime, Integer points) {
            this.questionNumber = questionNumber;
            this.answer = answer;
            this.isCorrect = isCorrect;
            this.responseTime = responseTime;
            this.points = points;
        }

        // Getters and Setters
        public Integer getQuestionNumber() { return questionNumber; }
        public void setQuestionNumber(Integer questionNumber) { this.questionNumber = questionNumber; }

        public String getAnswer() { return answer; }
        public void setAnswer(String answer) { this.answer = answer; }

        public Boolean getIsCorrect() { return isCorrect; }
        public void setIsCorrect(Boolean isCorrect) { this.isCorrect = isCorrect; }

        public Long getResponseTime() { return responseTime; }
        public void setResponseTime(Long responseTime) { this.responseTime = responseTime; }

        public Integer getPoints() { return points; }
        public void setPoints(Integer points) { this.points = points; }
    }
}