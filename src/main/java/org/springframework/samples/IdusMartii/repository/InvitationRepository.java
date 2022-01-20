package org.springframework.samples.IdusMartii.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.IdusMartii.model.Invitation;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface InvitationRepository extends CrudRepository<Invitation, Integer>{
    @Query("SELECT i FROM Invitation i WHERE i.user = :user")
    public List<Invitation> findByUser(@Param("user") User user);  
    @Query("SELECT i FROM Invitation i WHERE i.user = :user AND i.match = :match")
    public List<Invitation> findByUserAndMatch(@Param("user") User user, @Param("match") Match match);  
}
