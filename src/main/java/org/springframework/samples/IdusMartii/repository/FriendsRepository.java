package org.springframework.samples.IdusMartii.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.User;

public interface FriendsRepository extends CrudRepository<User, Integer>{
	
	@Modifying
	@Query(value="INSERT INTO FRIENDS(user_username, friends_username) VALUES (:username1,:username2)", nativeQuery=true)
    public void saveFriends1(@Param("username1") String username1, @Param("username2") String username2);
	
	@Modifying
	@Query(value="INSERT INTO FRIENDS(user_username, friends_username) VALUES (:username2,:username1)", nativeQuery=true)
    public void saveFriends2(@Param("username2") String username2, @Param("username1") String username1);

	
    
    
    
    

}

