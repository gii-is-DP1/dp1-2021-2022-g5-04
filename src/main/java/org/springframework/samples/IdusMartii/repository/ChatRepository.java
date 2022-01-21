package org.springframework.samples.IdusMartii.repository;
import java.util.List;

import org.springframework.samples.IdusMartii.model.Match;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.IdusMartii.model.Chat;
import org.springframework.samples.IdusMartii.model.User;



public interface ChatRepository extends CrudRepository<Chat, Integer>{
    @Query("SELECT c FROM Chat c WHERE c.match = :match")
    public List<Chat> findByMatch(@Param("match") Match match);   
    // @Query("SELECT c FROM Chat c WHERE c.match = :match AND c.user = :user")
    // public List<Chat> findByMatchAndUser(@Param("match") Match match, @Param("user") User user);    
}
