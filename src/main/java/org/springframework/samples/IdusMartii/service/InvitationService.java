package org.springframework.samples.IdusMartii.service;

import org.springframework.samples.IdusMartii.repository.InvitationRepository;
import org.springframework.samples.IdusMartii.model.Invitation;
import org.springframework.samples.IdusMartii.model.Match;
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
	
    
    
}
