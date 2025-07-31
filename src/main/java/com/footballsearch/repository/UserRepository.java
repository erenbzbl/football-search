package com.footballsearch.repository;

import com.footballsearch.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("{ 'isAdmin': true }")
    List<User> findAllAdmins();

    @Query(value = "{}", sort = "{ 'totalScore': -1 }")
    List<User> findTopPlayersByTotalScore();

    @Query(value = "{}", sort = "{ 'weeklyScore': -1 }")
    List<User> findTopPlayersByWeeklyScore();

    @Query(value = "{}", sort = "{ 'footballPoints': -1 }")
    List<User> findTopPlayersByFootballPoints();
}