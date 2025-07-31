package com.footballsearch.repository;

import com.footballsearch.model.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends MongoRepository<Team, String> {

    Optional<Team> findByName(String name);

    List<Team> findByLeagueId(String leagueId);

    List<Team> findByCountry(String country);

    @Query("{ 'isActive': true }")
    List<Team> findAllActiveTeams();

    @Query("{ 'isActive': true, 'leagueId': ?0 }")
    List<Team> findActiveTeamsByLeague(String leagueId);
}