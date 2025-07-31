package com.footballsearch.repository;

import com.footballsearch.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {

    Optional<Game> findByGameCode(String gameCode);

    @Query("{ 'gameStatus': ?0 }")
    List<Game> findByGameStatus(String status);

    @Query("{ 'gameStatus': 'waiting' }")
    List<Game> findWaitingGames();

    @Query("{ 'gameStatus': 'playing' }")
    List<Game> findActiveGames();

    @Query("{ 'players.userId': ?0 }")
    List<Game> findGamesByUserId(String userId);
}