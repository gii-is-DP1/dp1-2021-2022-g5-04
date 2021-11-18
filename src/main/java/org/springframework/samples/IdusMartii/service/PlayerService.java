package org.springframework.samples.IdusMartii.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.IdusMartii.repository.PlayerRepository;
import org.springframework.samples.IdusMartii.model.Player;

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
}
