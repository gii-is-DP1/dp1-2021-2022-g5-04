package org.springframework.samples.IdusMartii.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService2 {
	@Autowired
	private PlayerRepository2 playerRepository2;
	
	@Transactional
	public int playerCount() {
		return (int) playerRepository2.count();
	}
	
	@Transactional
	public Iterable<Player2> findAll(){
		return playerRepository2.findAll();
	}
	
	@Transactional
	public void savePlayer(Player2 player) throws DataAccessException {
		playerRepository2.save(player);
	}
	
	@Transactional
	public Player2 getPlayer(int id) throws DataAccessException {
		return playerRepository2.findById(String.valueOf(id)).get();
	}
}
