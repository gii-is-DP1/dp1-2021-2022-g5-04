package org.springframework.samples.IdusMartii.service;

import org.springframework.samples.IdusMartii.repository.InvitationRepository;
import org.springframework.samples.IdusMartii.model.Invitation;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.samples.IdusMartii.model.Player;
import org.springframework.samples.IdusMartii.model.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;

@Service
public class InvitationService {
    @Autowired 
	InvitationRepository invitationRepository;
	@Autowired
	private InvitationService invitationService;
	@Autowired
	private CurrentUserService currentUserService;
	@Autowired
	private UserService userService;
	@Autowired
	private MatchService matchService;
	@Autowired
	private PlayerService playerService;

	@Transactional
	public Iterable<Invitation> findAll(){
		return invitationRepository.findAll();
	}
    
	@Transactional(readOnly = true)
	public Invitation findById(Integer id) throws DataAccessException{
		return invitationRepository.findById(id).get();
	}
	
	@Transactional
	public void saveInvitation(Invitation invitation) throws DataAccessException {
		invitationRepository.save(invitation);
	}
    @Transactional(readOnly = true)
	public List<Invitation> findByUser(User user) throws DataAccessException{
		return invitationRepository.findByUser(user);
	}
	@Transactional(readOnly = true)
	public List<Invitation> findByUserAndMatch(User user, Match match) throws DataAccessException{
		return invitationRepository.findByUserAndMatch(user, match);
	}
	@Transactional
	public void deleteInvitation(Invitation invitation) throws DataAccessException {
		invitationRepository.delete(invitation);
	}
	@Transactional
	public void acceptInvitation(int id_invt, Match match, User user) throws DataAccessException {
		Invitation invitation = invitationService.findById(id_invt);
			invitationService.deleteInvitation(invitation);
			Player player = new Player();
			player.setUser(user);
			player.setMatch(match);
			matchService.saveMatch(match);
			playerService.savePlayer(player);
	}
	@Transactional
	public void deleteAllInvitationsFromUser(User user) {
		List<Invitation> invitationsFromUser = invitationRepository.findByUser(user);
		for (Invitation i: invitationsFromUser) {
			invitationRepository.delete(i);
		}
	}
    
    
}
