package org.springframework.samples.IdusMartii.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.data.repository.query.Param;


public interface MatchRepository extends CrudRepository<Match, Integer>{
	
    @Query("SELECT p.match FROM Player p WHERE p.user = :user")
    public List<Match> findMatchesFromUser(@Param("user") User user);
	
}
