package org.springframework.samples.idusmartii.repository;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.idusmartii.model.Achievement;
import org.springframework.samples.idusmartii.model.AchievementType;
import org.springframework.samples.idusmartii.model.User;

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

	@Query("SELECT p.user.username, count(p) FROM Player p WHERE p.match.finished = :finished GROUP BY p.user.username ORDER BY count(p) DESC")
    public List<Tuple> topMatchPlaying(@Param("finished") boolean finished);
	@Query("SELECT p.user.username, count(p) FROM Player p WHERE p.match.finished = :finished AND p.card1 = p.match.winner GROUP BY p.user.username ORDER BY count(p) DESC")
    public List<Tuple> topWins(@Param("finished") boolean finished);
	@Query("SELECT p.user.username, count(p) FROM Player p WHERE p.match.finished = :finished AND p.card1 != p.match.winner GROUP BY p.user.username ORDER BY count(p) DESC")
    public List<Tuple> topLoss(@Param("finished") boolean finished);
}
