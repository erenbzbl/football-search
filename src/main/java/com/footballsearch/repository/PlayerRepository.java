package com.footballsearch.repository;

import com.footballsearch.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {

    Optional<Player> findByName(String name);

    @Query("{ 'teamIds': ?0 }")
    List<Player> findByTeamId(String teamId);

    @Query("{ 'teamIds': { '$in': [?0, ?1] } }")
    List<Player> findByBothTeams(String team1Id, String team2Id);

    @Query("{ 'isActive': true }")
    List<Player> findAllActivePlayers();

    @Query("{ 'name': { '$regex': ?0, '$options': 'i' } }")
    List<Player> findByNameContainingIgnoreCase(String name);
}