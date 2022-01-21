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
		private FriendInvitationService friendInvitationService;
		@Autowired
		private FriendsService friendsService;

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
		
		@Transactional(rollbackFor = Exception.class)
		public void saveFriendInvitation(FriendInvitation friendInvitation) throws DataAccessException {
			log.info("Creando invitación de amistad...");
			log.debug("Atributo: " + friendInvitation);
			if (friendInvitation.getUser_requested() != friendInvitation.getUser_requester()) {
				friendInvitationRepository.save(friendInvitation);
			} else {
				throw new DataAccessException("Un usuario no puede enviarse una solicitud de amistad a si mismo") {};
			}
		}
		
	    @Transactional(readOnly = true)
		public List<FriendInvitation> findFriendInvitationsByUserRequested(User user) throws DataAccessException{
	    	log.info("Buscando Solicitudes de amistad de usuario...");
	    	log.debug("Usuario: " + user);
			return friendInvitationRepository.findFriendInvitationsByUserRequested(user);
		}
		@Transactional
		public void deleteFriendInvitation(FriendInvitation friendInvitation) throws DataAccessException {
			log.info("Rechazando solicitud de amistad...");
			log.debug("Atributo: " + friendInvitation);
			friendInvitationRepository.delete(friendInvitation);
		}
		@Transactional
		public void acceptFriendInvitation(int id_invt) throws DataAccessException {
			log.info("Aceptando solicitud de amistad...");
			log.debug("Id de solicitud: " + id_invt);
			FriendInvitation friendInvitation = friendInvitationService.findById(id_invt);
			friendsService.saveFriends(friendInvitation.getUser_requester().getUsername(), friendInvitation.getUser_requested().getUsername());
			friendInvitationService.deleteFriendInvitation(friendInvitation);
		}
		@Transactional
		public void deleteFriendInvitationsFromUser(User user) throws DataAccessException {
			List<FriendInvitation> friendInvitationsFromUser = friendInvitationRepository.findFriendInvitationsByUserRequester(user);
			for (FriendInvitation fi: friendInvitationsFromUser) {
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
}
