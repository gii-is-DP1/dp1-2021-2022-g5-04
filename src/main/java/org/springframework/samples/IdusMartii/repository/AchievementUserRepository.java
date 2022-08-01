package org.springframework.samples.IdusMartii.repository;

import java.util.List;
import java.util.Map;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.User;

public interface AchievementUserRepository extends CrudRepository<Achievement, Integer>{
	
	@Modifying
	@Query(value="INSERT INTO ACHIEVEMENT_USER(user_username, achievements_id) VALUES (:username,:id)",nativeQuery=true)
    public void saveAchievementUser(@Param("username") String username, @Param("id") Integer id);

	@Query("SELECT p.user.username, count(p) FROM Player p WHERE p.match.finished = :finished ORDER BY count(p) DESC")
    public List<Tuple> topMatchPlaying(@Param("finished") boolean finished);
	@Query("SELECT p.user.username, count(p) FROM Player p WHERE p.match.finished = :finished AND p.card1 = p.match.winner ORDER BY count(p) DESC")
    public List<Tuple> topWins(@Param("finished") boolean finished);
	@Query("SELECT p.user.username, count(p) FROM Player p WHERE p.match.finished = :finished AND p.card1 != p.match.winner ORDER BY count(p) DESC")
    public List<Tuple> topLoss(@Param("finished") boolean finished);



	

	
    
    
    
    

}
