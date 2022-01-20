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

import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;

@Slf4j
@Service
public class InvitationService {
    @Autowired 
	InvitationRepository invitationRepository;
	@Autowired
	private InvitationService invitationService;
	@Autowired
	private MatchService matchService;
	@Autowired
	private PlayerService playerService;

	@Transactional
	public Iterable<Invitation> findAll(){
		log.info("Acceso al metodo FindAll() de invitaciones de partidas.");
		return invitationRepository.findAll();
	}
    
	@Transactional(readOnly = true)
	public Invitation findById(Integer id) throws DataAccessException{
		log.info("Acceso a FindById() de invitaciones de partida");
		log.debug("Id = " + id);
		return invitationRepository.findById(id).get();
	}
	
	@Transactional
	public void saveInvitation(Invitation invitation) throws DataAccessException {
		log.info("Guardando invitación...");
		invitationRepository.save(invitation);
	}
    @Transactional(readOnly = true)
	public List<Invitation> findByUser(User user) throws DataAccessException{
    	log.info("Buscando invitaciones a partida deñ usuario");
    	log.debug("Atributo: "+ user);
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
		log.info("Aceptando invitacion...");
		log.debug("Atributos: id invitación-> " + id_invt + "  Partida-> "+ match + "  User-> " + user);
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
		log.info("Borrando invitaciones de User");
		log.debug("User : "+ user);
		List<Invitation> invitationsFromUser = invitationRepository.findByUser(user);
		for (Invitation i: invitationsFromUser) {
			invitationRepository.delete(i);
		}
	}
    
    
}
