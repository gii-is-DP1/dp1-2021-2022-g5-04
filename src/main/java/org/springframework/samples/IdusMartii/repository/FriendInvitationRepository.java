package org.springframework.samples.IdusMartii.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.IdusMartii.model.FriendInvitation;
import org.springframework.samples.IdusMartii.model.User;

public interface FriendInvitationRepository extends CrudRepository<FriendInvitation, Integer>{
	
	@Query("SELECT i FROM FriendInvitation i WHERE i.user_requested = :user")
    public List<FriendInvitation> findFriendInvitationsByUserRequested(@Param("user") User user);  
	
	@Query("SELECT i FROM FriendInvitation i WHERE i.user_requester = :user")
	public List<FriendInvitation> findFriendInvitationsByUserRequester(@Param("user") User user);
}
