package org.springframework.samples.IdusMartii.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.IdusMartii.model.FriendInvitation;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.samples.IdusMartii.repository.FriendInvitationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FriendInvitationService {
	
	 	@Autowired 
		FriendInvitationRepository friendInvitationRepository;
		@Autowired
		private UserService userService;

		@Transactional
		public Iterable<FriendInvitation> findAll(){
			log.info("Acceso al metodo findAll() de Solicitudes de amistad.");
			return friendInvitationRepository.findAll();
		}
	    
		@Transactional(readOnly = true)
		public FriendInvitation findById(Integer id) throws DataAccessException{
			log.info("Buscando FriendInvitation por Id");
			log.debug("Atributo: " + id);
			return friendInvitationRepository.findById(id).get();
		}
		
		@Transactional
		public String saveFriendInvitation(FriendInvitation friendInvitation) throws DataAccessException{
			log.info("Creando invitación de amistad...");
			log.debug("Atributo: " + friendInvitation);
			if (friendInvitation.getUser_requested() != friendInvitation.getUser_requester()) {
				friendInvitationRepository.save(friendInvitation);
				return "/welcome";
			} 
			return "/welcome";
		}
		
	    @Transactional(readOnly = true)
		public List<FriendInvitation> findFriendInvitationsByUserRequested(User user) throws DataAccessException{
	    	log.info("Buscando Solicitudes de amistad de usuario...");
	    	log.debug("Usuario: " + user);
			return friendInvitationRepository.findFriendInvitationsByUserRequested(user);
		}
		@Transactional
		public String deleteFriendInvitation(FriendInvitation friendInvitation, User current) throws DataAccessException{
			log.info("Rechazando solicitud de amistad...");
			log.debug("Atributo: " + friendInvitation);
			if (friendInvitation.getUser_requested() != friendInvitation.getUser_requester()) {
				friendInvitationRepository.delete(friendInvitation);
			} 
			return "redirect:/friendInvitations";
		}
		@Transactional
		public String acceptFriendInvitation(int id_invt, User current) throws DataAccessException {
			log.info("Aceptando solicitud de amistad...");
			log.debug("Id de solicitud: " + id_invt);
			FriendInvitation friendInvitation = this.findById(id_invt);
			if(current == friendInvitation.getUser_requested()) {
				this.saveFriends(friendInvitation.getUser_requester().getUsername(), friendInvitation.getUser_requested().getUsername());
				this.deleteFriendInvitation(friendInvitation, current);
				
			} 
			return "redirect:/friendInvitations";
		}
		@Transactional
		public void deleteFriendInvitationsFromUser(User user) throws DataAccessException {
			List<FriendInvitation> friendInvitationsFromUser = friendInvitationRepository.findFriendInvitationsByUserRequester(user);
			List<FriendInvitation> friendInvitationsRequestedToUser = friendInvitationRepository.findFriendInvitationsByUserRequested(user);
			for (FriendInvitation fi: friendInvitationsFromUser) {
				friendInvitationRepository.delete(fi);
			}
			for (FriendInvitation fi: friendInvitationsRequestedToUser) {
				friendInvitationRepository.delete(fi);
			}
		}
		@Transactional
		public boolean letFriendRequest(User userRequester, User userRequested) {
			if (userRequester.getFriends().contains(userRequested)) {
				return false;
			} else {
				return true;
			}
		}
		@Transactional(rollbackFor = Exception.class)
	public void saveFriends(String username1, String username2) throws DataAccessException {
    log.info("Llamada al metodo saveFriends(String, String)");
    log.debug("atributos: " + username1 + ", " + username2);
		if (username1 != username2) {
			friendInvitationRepository.saveFriends1(username1, username2);
			friendInvitationRepository.saveFriends2(username2, username1);
		} else {
			throw new DataAccessException("Un usuario no puede agregarse a si mismo como amigo") {};
		}
	}
	
	@Transactional
	public void deleteAllFriendsFromUser(User user) throws DataAccessException {
		List<String> friendsFromUser = friendInvitationRepository.findUserFriendsFromUsername(user.getUsername());
		for (String s: friendsFromUser) {
			userService.deleteFriend(user, s);
		}
	}
}
