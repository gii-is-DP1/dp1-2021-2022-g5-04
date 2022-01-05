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
	public List<Player> jugadoresPartida(Match match) throws DataAccessException {
		return playerRepository.findByMatchId(match);
	}
	
	@Transactional
	public Player findByMatchAndUser(Match match, User user) throws DataAccessException {
		return playerRepository.findByMatchAndUser(match, user);
	}
	
	@Transactional
	public List<Player> findByRole(Match match, Role role) throws DataAccessException {
		return playerRepository.findByRole(match, role);
	}
	
	@Transactional
	public List<Match> findMatchesFromUser(User user) throws DataAccessException {
		return playerRepository.findMatchesFromUser(user);
	}
	@Transactional
	public boolean canVote(Player player, Match match) throws DataAccessException {
		if (player.getVote() == null && player.getRole() == Role.EDIL && (match.getPlays() == Plays.EDIL || match.getPlays() == Plays.YELLOWEDIL)) {
			return true;
		} else {
			return false;
		}
	}
	public Player playerYellow(Match match) throws DataAccessException {
		Player jugador = null;
		for (Player p: match.getPlayers()) {
			if (p.isAsigned() && p.getVote() == null && p.getRole() == Role.EDIL && match.getPlays() == Plays.YELLOWEDIL) {
				jugador = p;
			} 
		}
		return jugador;
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
			} else if (player.getRole() == Role.CONSUL && player == match.getPlayers().get(0) && match.getRound() == 1 && afterVotes(player, match)){
				resultado = true;
			}
			return resultado;
		} else {
			return false;
		}
	}
	@Transactional
	public boolean countVotes(Player player, Match match) throws DataAccessException {
		if (match.getPlays() == Plays.CONSUL && player.getRole() == Role.CONSUL && (player == match.getPlayers().get(0) || player.getCard2() == Faction.DROPPED) && afterVotes(player, match) && !chooseFaction(player, match)) {
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
	@Transactional
	public boolean afterVotes(Player player, Match match) throws DataAccessException {
		boolean resultado = false;
		int i = 0;
		for (Player p: playerRepository.findByRole(match, Role.EDIL)) {
			if (p.getVote() != null) {
				i += 1;
			}
		}
		if (i == 2) {
			resultado = true;
		}
		return resultado;
	}
	@Transactional
	public List<Player> jugadoresConVoto(Role role, Match match) throws DataAccessException {
		List<Player> jugadoresConVoto = findByRole(match, role);
		for (Player j: jugadoresConVoto) {
			if(j.getVote() == Vote.GREEN) {
				match.setVotesInFavor(match.getVotesInFavor() + 1);
			} else if (j.getVote() == Vote.RED) {
				match.setVotesAgainst(match.getVotesAgainst() + 1);
			}
		}
		return jugadoresConVoto;
	}
	@Transactional
	public void asignarRoles(Match match, List<Player> jugadores, List<Role> roles) throws DataAccessException {
		if(match.getRound() == 0 && match.getTurn() < jugadores.size() - 1) {
			for (int i = 0; i <= jugadores.size() - 1; i++) {
				roles.add(jugadores.get((i)).getRole());
			}
			for (int i = 0; i <= jugadores.size() - 1; i++) {
				jugadores.get((i+1)%jugadores.size()).setRole(roles.get(i));
			}
		} else if (match.getRound() == 1 || (match.getRound() == 0 && match.getTurn() == jugadores.size() - 1)){
			for (int i = 0; i <= jugadores.size() - 1; i++) {
				roles.add(jugadores.get((i)).getRole());
			}
			for (int i = 0; i <= jugadores.size() - 1; i++) {
				if (roles.get(i) == Role.CONSUL) {
					jugadores.get((i+1)%jugadores.size()).setRole(roles.get(i));
					jugadores.get(i).setRole(Role.NO_ROL);
				}
			}
		}
	}
	@Transactional	
	public int calcularVotos(List<Player> jugadores) throws DataAccessException {
		int i = 0;
		for (Player p: jugadores) {
			if (p.getVote() != null) {
				i += 1;
			}
		}
		return i;
	}
	@Transactional
	public boolean winnerLoyal(Player jugador, Faction faction) throws DataAccessException {
		if (jugador.getCard1() == Faction.LOYAL && Faction.LOYAL == faction) {
			return true;
		} else {
			return false;
		}
	}
	@Transactional
	public boolean winnerTraitor(Player jugador, Faction faction) throws DataAccessException {
		if (jugador.getCard1() == Faction.TRAITOR && Faction.TRAITOR == faction) {
			return true;
		} else {
			return false;
		}
	}
	@Transactional
	public boolean winnerMerchant(Player jugador, Faction faction) throws DataAccessException {
		if (jugador.getCard1() == Faction.MERCHANT && Faction.MERCHANT == faction) {
			return true;
		} else {
			return false;
		}
	}
	@Transactional
	public void rolesAsigned(Match match) throws DataAccessException {
		for (Player p: match.getPlayers()) {
			if (p.getRole() != Role.CONSUL && !p.isAsigned()) {
				p.setRole(Role.NO_ROL);
				savePlayer(p);
			}
		}
	}
}
