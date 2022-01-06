package org.springframework.samples.IdusMartii.repository;

import org.springframework.samples.IdusMartii.enumerates.Faction;
import org.springframework.samples.IdusMartii.enumerates.Role;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.IdusMartii.model.Player;
import java.util.List;


public interface PlayerRepository extends CrudRepository<Player, Integer>{
	
    @Query("SELECT p FROM Player p WHERE p.match = :match AND p.user = :user")
    public Player findByMatchAndUser(@Param("match") Match match, @Param("user") User user);
    
    @Query("SELECT p FROM Player p WHERE p.user = :username")
    public List<Player> findByUsername (@Param("username") String username);


    @Query("SELECT p FROM Player p WHERE p.match = :match")
    public List <Player> findByMatchId(@Param("match") Match match);
  
    @Query("SELECT p FROM Player p WHERE p.match = :match AND p.role = :role")
    public List<Player> findByRole(@Param("match") Match match, @Param("role") Role role); 
    
    @Query("SELECT p FROM Player p WHERE p.match = :match AND p.card1 = :faction")
    public List<Player> findByFaction(@Param("match") Match match, @Param("faction") Faction faction);
    
    @Query("SELECT p.match FROM Player p WHERE p.user = :user")
    public List<Match> findMatchesFromUser(@Param("user") User user);
}
