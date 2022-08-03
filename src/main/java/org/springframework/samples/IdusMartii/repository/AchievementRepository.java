package org.springframework.samples.IdusMartii.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.AchievementType;
import org.springframework.samples.IdusMartii.model.User;

public interface AchievementRepository extends CrudRepository<Achievement, Integer>{
	
	@Modifying
    @Query(nativeQuery = true, value="SELECT a.* from Achievements a JOIN Achievement_User ap WHERE ap.user_id = :user AND ap.achievement_id = a.id")
    public List <Achievement> findByUser(@Param("user") User user);
    
    @Query("SELECT t FROM AchievementType t")
    public List<AchievementType> findAllAchievementsTypes();
    
    @Query("SELECT t FROM AchievementType t WHERE t.name = :name")
    public AchievementType findAchievementTypeByName(@Param("name") String name);
        
    @Query("SELECT a FROM Achievement a WHERE a.achievementType = :tipo")
    public List<Achievement> findByAchievementType(@Param("tipo") AchievementType tipo);

    @Modifying
	@Query(value="INSERT INTO ACHIEVEMENT_USER(user_username, achievements_id) VALUES (:username,:id)",nativeQuery=true)
    public void saveAchievementUser(@Param("username") String username, @Param("id") Integer id);
}
