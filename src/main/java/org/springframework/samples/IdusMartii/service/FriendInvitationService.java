package org.springframework.samples.IdusMartii.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.IdusMartii.model.FriendInvitation;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.samples.IdusMartii.repository.FriendInvitationRepository;
import org.springframework.samples.IdusMartii.repository.FriendsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendInvitationService {
	
	 	@Autowired 
		FriendInvitationRepository friendInvitationRepository;
		@Autowired
		private FriendInvitationService friendInvitationService;
		@Autowired
		private CurrentUserService currentUserService;
		@Autowired
		private UserService userService;
		@Autowired
		private FriendsRepository friendsRepository;

		@Transactional
		public Iterable<FriendInvitation> findAll(){
			return friendInvitationRepository.findAll();
		}
	    
		@Transactional(readOnly = true)
		public FriendInvitation findById(Integer id) throws DataAccessException{
			return friendInvitationRepository.findById(id).get();
		}
		
		@Transactional
		public void saveFriendInvitation(FriendInvitation friendInvitation) throws DataAccessException {
			friendInvitationRepository.save(friendInvitation);
		}
	    @Transactional(readOnly = true)
		public List<User> findUserRequesters(User user) throws DataAccessException{
			return friendInvitationRepository.findUserRequesters(user);
		}
		@Transactional
		public void deleteFriendInvitation(FriendInvitation friendInvitation) throws DataAccessException {
			friendInvitationRepository.delete(friendInvitation);
		}
		@Transactional
		public void acceptFriendInvitation(int id_invt) throws DataAccessException {
			FriendInvitation friendInvitation = friendInvitationService.findById(id_invt);
			friendsRepository.saveFriends(friendInvitation.getUser_requester().getUsername(), friendInvitation.getUser_requested().getUsername());
			friendInvitationService.deleteFriendInvitation(friendInvitation);
		}
		

}
