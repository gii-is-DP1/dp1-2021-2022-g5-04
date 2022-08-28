package org.springframework.samples.idusmartii.repository;


import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.idusmartii.model.Match;
import org.springframework.samples.idusmartii.model.User;


public interface MatchRepository extends CrudRepository<Match, Integer>{

    @Query("SELECT p.match FROM Player p WHERE p.user = :user")
    public List<Match> findMatchesFromUser(@Param("user") User user);
	
	
}
