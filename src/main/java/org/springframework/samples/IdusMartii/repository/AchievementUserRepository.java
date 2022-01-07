package org.springframework.samples.IdusMartii.repository;

import java.util.List;
import java.util.Optional;

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

	
    
    
    
    

}
