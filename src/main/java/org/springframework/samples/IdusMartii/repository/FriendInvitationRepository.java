package org.springframework.samples.idusmartii.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.idusmartii.model.FriendInvitation;
import org.springframework.samples.idusmartii.model.User;

public interface FriendInvitationRepository extends CrudRepository<FriendInvitation, Integer>{
	
	@Query("SELECT i FROM FriendInvitation i WHERE i.user_requested = :user")
    public List<FriendInvitation> findFriendInvitationsByUserRequested(@Param("user") User user);  
	
	@Query("SELECT i FROM FriendInvitation i WHERE i.user_requester = :user")
	public List<FriendInvitation> findFriendInvitationsByUserRequester(@Param("user") User user);

	@Modifying
	@Query(value="INSERT INTO FRIENDS(user_username, friends_username) VALUES (:username1,:username2) AND INSERT INTO FRIENDS(user_username, friends_username) VALUES (:username2,:username1)",nativeQuery=true)
    public void saveFriends(@Param("username") String username1, @Param("username2") String username2);
	
	@Modifying
	@Query(value="INSERT INTO FRIENDS(user_username, friends_username) VALUES (:username1,:username2)", nativeQuery=true)
    public void saveFriends1(@Param("username1") String username1, @Param("username2") String username2);
	
	@Modifying
	@Query(value="INSERT INTO FRIENDS(user_username, friends_username) VALUES (:username2,:username1)", nativeQuery=true)
    public void saveFriends2(@Param("username2") String username2, @Param("username1") String username1);

	@Query(value="SELECT f.friends_username FROM FRIENDS f WHERE f.user_username = :username", nativeQuery=true)
	public List<String> findUserFriendsFromUsername(@Param("username") String username);
}
