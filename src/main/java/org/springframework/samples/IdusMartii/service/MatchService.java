package org.springframework.samples.IdusMartii.service;

import org.springframework.samples.IdusMartii.repository.MatchRepository;
import org.springframework.samples.IdusMartii.repository.PlayerRepository;
import org.springframework.samples.IdusMartii.enumerates.Faction;
import org.springframework.samples.IdusMartii.enumerates.Plays;
import org.springframework.samples.IdusMartii.enumerates.Role;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.samples.IdusMartii.model.Player;
import org.springframework.samples.IdusMartii.model.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;

@Service
public class MatchService {
    @Autowired 
	MatchRepository matchRepository;
    @Autowired
    PlayerRepository playerRepository;

	@Transactional
	public Iterable<Match> findAll(){
		return matchRepository.findAll();
	}

 
    
	@Transactional(readOnly = true)
	public Match findById(Integer id) throws DataAccessException {
		return matchRepository.findById(id).get();
	}
	
	@Transactional
	public void saveMatch(Match match) throws DataAccessException {
		matchRepository.save(match);
	}
	
	@Transactional
	public boolean roundI(Match match) throws DataAccessException {
		if (match.getRound() == 0) {
			return true;
		} else {
			return false;
		}
	}
	@Transactional
	public boolean roundII(Match match) throws DataAccessException {
		if (match.getRound() == 1) {
			return true;
		} else {
			return false;
		}
	}
	@Transactional
	public List<Player> playersWithNoConsulRole(Match match) throws DataAccessException {
		List<Player> resultado = new ArrayList<Player>();
		List<Player> players = match.getPlayers();
		for(Player p: players) {
			if(p.getRole() != Role.CONSUL && !p.isAsigned()) {
				resultado.add(p);
			}
		}
		return resultado;
	}
	@Transactional
	public boolean pretorNotAsigned(Match match) throws DataAccessException {
		boolean resultado = true;
		List<Player> players = match.getPlayers();
		for (Player p: players) {
			if (p.getRole() == Role.PRETOR && p.isAsigned()) {
				resultado = false;
			}
		}
		return resultado;
	}
	@Transactional
	public boolean startMatch(Match match) throws DataAccessException {
		if (match.getPlayers().size() >= 5) {
			return true;
		} else {
			return false;
		}
	}
	@Transactional
	public boolean edilNotAsigned(Match match) throws DataAccessException {
		boolean resultado = true;
		List<Player> players = match.getPlayers();
		int i = 0;
		for (Player p: players) {
			if (p.getRole() == Role.EDIL && p.isAsigned()) {
				i += 1;
			}
		}
		if (i == 2) {
			resultado = false;
		}
		return resultado;
	}
    @Transactional
    public void avanzarTurno(Match match, List<Player> jugadores) throws DataAccessException {
    	if ((match.getTurn() + 1) >= jugadores.size()) {
			match.setRound(match.getRound() + 1);
			match.setTurn(0);
			match.setPlays(Plays.CONSUL);
		} else {
			if (match.getRound() == 1) {
				match.setTurn(match.getTurn() + 1);
				match.setPlays(Plays.CONSUL);
				for (Player p: jugadores) {
					if(p.isAsigned()) {
						p.setAsigned(false);
					}
				}
			} else {
				match.setTurn(match.getTurn() + 1);
				match.setPlays(Plays.EDIL);
			}
		}
    }
    @Transactional
    public void votacionCompletada(int votos, Match match) throws DataAccessException {
    	if(votos == 2) {
			if (match.getPlays() == Plays.YELLOWEDIL) {
				match.setPlays(Plays.CONSUL);
				saveMatch(match);
			} else {
				match.setPlays(Plays.PRETOR);
				saveMatch(match);
			}
		}
    }
    @Transactional
    public Faction sufragium(Match match) throws DataAccessException {
    	int numeroJugadores = match.getPlayers().size();
    	Faction faccionGanadora = Faction.MERCHANT;
    	if (match.getVotesInFavor() - match.getVotesAgainst() >= 2 && match.getRound() == 2) {
    		faccionGanadora = Faction.LOYAL;
    	} else if (match.getVotesAgainst() - match.getVotesInFavor() >= 2 && match.getRound() == 2) {
    		faccionGanadora = Faction.TRAITOR;
    	} else if (numeroJugadores == 5) {
    		if (match.getVotesInFavor() == 13) {
    			faccionGanadora = Faction.TRAITOR;
    		} else if (match.getVotesAgainst() == 13) {
    			faccionGanadora = Faction.LOYAL;
    		}
    	} else if (numeroJugadores == 6) {
    		if (match.getVotesInFavor() == 15) {
    			faccionGanadora = Faction.TRAITOR;
    		} else if (match.getVotesAgainst() == 15) {
    			faccionGanadora = Faction.LOYAL;
    		}
    	} else if (numeroJugadores == 7) {
    		if (match.getVotesInFavor() == 17) {
    			faccionGanadora = Faction.TRAITOR;
    		} else if (match.getVotesAgainst() == 17) {
    			faccionGanadora = Faction.LOYAL;
    		}
    	} else if (numeroJugadores == 8) {
    		if (match.getVotesInFavor() == 20) {
    			faccionGanadora = Faction.TRAITOR;
    		} else if (match.getVotesAgainst() == 20) {
    			faccionGanadora = Faction.LOYAL;
    		}
    	}
    	return faccionGanadora;
    }
    
}