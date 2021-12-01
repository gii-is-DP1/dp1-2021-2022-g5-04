package org.springframework.samples.IdusMartii.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.IdusMartii.repository.PlayerRepository;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.samples.IdusMartii.model.Player;
import java.util.List;

@Service
public class PlayerService {
	@Autowired
	private PlayerRepository playerRepository;
	
	@Transactional
	public int playerCount() {
		return (int) playerRepository.count();
	}
	
	@Transactional
	public Iterable<Player> findAll(){
		return playerRepository.findAll();
	}
	
	@Transactional
	public void savePlayer(Player player) throws DataAccessException {
		playerRepository.save(player);
	}
	
	
	
    
	@Transactional
	public Player findbyId(Integer ID) throws DataAccessException {
		return playerRepository.findById(ID).get();
	}
	@Transactional
	public Player findByMatchAndUsername(Match match, User user) throws DataAccessException {
		return playerRepository.findByMatchAndUsername(match, user);
	}
	
	

}
