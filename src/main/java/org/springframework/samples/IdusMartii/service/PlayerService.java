package org.springframework.samples.IdusMartii.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.IdusMartii.repository.PlayerRepository;
import org.springframework.samples.IdusMartii.enumerates.Faction;
import org.springframework.samples.IdusMartii.enumerates.Plays;
import org.springframework.samples.IdusMartii.enumerates.Role;
import org.springframework.samples.IdusMartii.enumerates.Vote;
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
	public void deletePlayer(Player player) throws DataAccessException {
		playerRepository.delete(player);
	}

	@Transactional
	public Player findbyId(Integer ID) throws DataAccessException {
		return playerRepository.findById(ID).get();
	}
	@Transactional
	public Player findByMatchAndUser(Match match, User user) throws DataAccessException {
		return playerRepository.findByMatchAndUser(match, user);
	}
	
	@Transactional
	public List<Player> findByRole(Role role) throws DataAccessException {
		return playerRepository.findByRole(role);
	}
	@Transactional
	public boolean canVote(Player player, Match match) throws DataAccessException {
		if (player.getVote() == null && player.getRole() == Role.EDIL && match.getPlays() == Plays.EDIL) {
			return true;
		} else {
			return false;
		}
	}
	@Transactional
	public boolean canVoteYellow(Player player, Match match) throws DataAccessException {
		if (player.getVote() == null && player.getRole() == Role.EDIL && match.getPlays() == Plays.YELLOWEDIL) {
			return true;
		} else {
			return false;
		}
	}
	public Player playerYellow(Player player, Match match) throws DataAccessException {
		if (player.getVote() == Vote.YELLOW && player.getRole() == Role.EDIL && match.getPlays() == Plays.YELLOWEDIL) {
			return player;
		} else {
			return null;
		}
	}
	@Transactional
	public boolean showCards(Player player) throws DataAccessException {
		if (player.getCard2() == Faction.DROPPED) {
			return false;
		} else {
			return true;
		}
	}
	@Transactional
	public boolean checkVote(Player player, Match match) throws DataAccessException {
		if (player.getRole() == Role.PRETOR && match.getPlays() == Plays.PRETOR) {
			return true;
		} else {
			return false;
		}
	}
	@Transactional
	public boolean chooseFaction(Player player, Match match) throws DataAccessException {
		if (match.getPlays() == Plays.CONSUL && player.getCard2() != Faction.DROPPED) {
			boolean resultado = false;
			if (player.getRole() == Role.CONSUL && player != match.getPlayers().get(0)) {
				resultado = true;
			} else if (player.getRole() == Role.CONSUL && player == match.getPlayers().get(0) && match.getRound() == 1){
				resultado = true;
			}
			return resultado;
		} else {
			return false;
		}
	}
	@Transactional
	public boolean countVotes(Player player, Match match) throws DataAccessException {
		if (match.getPlays() == Plays.CONSUL && player.getRole() == Role.CONSUL && (player == match.getPlayers().get(0) || player.getCard2() == Faction.DROPPED)) {
			return true;
		} else {
			return false;
		}
	}
	@Transactional
	public boolean chooseRol(Player player, Match match) throws DataAccessException {
		if (match.getPlays() == Plays.CONSUL && player.getRole() == Role.CONSUL){
			return true;
		} else {
			return false;
		}
	}
}
