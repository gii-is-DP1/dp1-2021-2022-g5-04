package org.springframework.samples.IdusMartii.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.User;

public interface AchievementRepository extends CrudRepository<Achievement, Integer>{
	
	@Modifying
    @Query(nativeQuery = true, value="SELECT a.* from Achievements a JOIN Achievement_User ap WHERE ap.user_id = ?1 AND ap.achievement_id = a.id")
    public List <Achievement> findByUser(@Param("user") User user);
    
    
    
    

}
