package org.springframework.samples.idusmartii.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.idusmartii.enumerates.Faction;
import org.springframework.samples.idusmartii.enumerates.Plays;
import org.springframework.samples.idusmartii.enumerates.Role;
import org.springframework.samples.idusmartii.enumerates.Vote;
import org.springframework.samples.idusmartii.model.Match;
import org.springframework.samples.idusmartii.model.Player;
import org.springframework.samples.idusmartii.model.User;
import org.springframework.samples.idusmartii.repository.MatchRepository;

@Slf4j
@Service
public class MatchService {
    @Autowired 
    private MatchRepository matchRepository;
	@Autowired
    private UserService userService;
    @Autowired
    private PlayerService playerService;

    
	@Transactional
	public Iterable<Match> findAll(){
		log.info("Buscando partidas...");
		return matchRepository.findAll();
	}

	@Transactional
	public List<Match> findMatchesFromUser(User user) throws DataAccessException {
		return matchRepository.findMatchesFromUser(user);
	}
	@Transactional
	public List<Match> findMatchesFromHost(User user) throws DataAccessException {
		List<Match> matchesResult = new ArrayList<Match>();
		List<Match> matchesIterate = matchRepository.findMatchesFromUser(user);
		for (Match m : matchesIterate){
			if(m.getPlayers().get(0).getUser()==user){
				matchesResult.add(m);
			}
		}
		return matchesResult;
	}

	
    @Transactional
    public List<Match> matches(User user) throws DataAccessException {
    	log.info("Buscando partidas...");
    	log.debug("Usuario: " + user);
    	if (!userService.isAdmin(user)) {
    		List<Match> matches = findMatchesFromUser(user);
    		return matches;
    	} else {
    		return (List<Match>)findAll();
    	}
    }
    
	@Transactional
    public List<Match> matchesCreated(User user) throws DataAccessException {
    	log.info("Buscando partidas...");
    	log.debug("Usuario: " + user);
    	if (!userService.isAdmin(user)) {
    		List<Match> matches = findMatchesFromHost(user);
    		return matches;
    	} else {
    		return (List<Match>)findAll();
    	}
    }
	
	@Transactional
    public boolean matchContainUser(Match match, User user) throws DataAccessException {
    	log.info("Buscando partidas...");
    	log.debug("Usuario: " + user);
		List<Player> players = match.getPlayers();
		List<User> users = new ArrayList<User>();
		for (Player p:players){
			users.add(p.getUser());
		}
    	return users.contains(user);
    }
	
	@Transactional
    public List<Match> matchesInProgress_NotFinished() throws DataAccessException {
    	log.info("Buscando partidas...");
		List<Match> matchesInProgress_NotFinished = new ArrayList<>();
		Iterable<Match> matches = matchRepository.findAll();
		for(Match m:matches){
			if(!m.isFinished() && m.getRound()!=0){
				matchesInProgress_NotFinished.add(m);
			}
		}
    	return matchesInProgress_NotFinished;
    }
	
	@Transactional
    public List<Match> matchesFinished() throws DataAccessException {
    	log.info("Buscando partidas...");
		List<Match> matchesFinished = new ArrayList<>();
		Iterable<Match> matches = matchRepository.findAll();
		for(Match m:matches){
			if(m.isFinished()){
				matchesFinished.add(m);
			}
		}
    	return matchesFinished;
    }
	
	@Transactional
    public List<Match> matchesLobby() throws DataAccessException {
    	log.info("Buscando partidas...");
		List<Match> matchesFinished = new ArrayList<>();
		Iterable<Match> matches = matchRepository.findAll();
		for(Match m:matches){
			if(m.getRound()==0 && !m.isFinished()){
				matchesFinished.add(m);
			}

		}
    	return matchesFinished;
    }
	
	@Transactional(readOnly = true)
	public Match findById(Integer id) throws DataAccessException {
		log.info("Buscando partida...");
    	log.debug("Id: " + id);
		return matchRepository.findById(id).get();
	}
	
	@Transactional
	public void saveMatch(Match match) throws DataAccessException {
    	log.info("Guardando partidas...");
    	log.debug("Partida: " + match);
		matchRepository.save(match);
	}
	
	@Transactional
	public boolean isHost(Player player, Match match) throws DataAccessException {
    	log.info("Comprobando si el player es host...");
    	log.debug("Jugador: " + player);
    	log.debug("Partida: " + match);
		if (match.getPlayers().get(0) == player) {
			log.info("Es host.");
			return true;
		} else {
			log.info("No es host.");
			return false;
		}
	}
	
	@Transactional
	public List<Vote> votes(Match match) throws DataAccessException {
		log.info("Generando votos...");
		List<Vote> votes = new ArrayList<>();
		votes.add(Vote.GREEN); votes.add(Vote.RED);
		if (match.getRound() == 2) {
			votes.add(Vote.YELLOW);
		}
		return votes;
	}
	
	@Transactional
	public String votedUser(Match match) throws DataAccessException {
		log.info("Obteniendo jugador con voto...");
		String player = null;
		for (Player p: match.getPlayers()) {
			if (p.getVote() != null) {
				player = p.getUser().getUsername();
			}
		} 
		return player;
	}
	
	@Transactional
	public List<Player> noHostPlayers(Match match) throws DataAccessException {
		List<Player> noHostPlayers = new ArrayList<Player>();
		for (Player p: match.getPlayers()) {
			if (match.getPlayers().get(0) != p) {
				noHostPlayers.add(p);
			}
		}
		return noHostPlayers;
	}
	
	@Transactional
	public boolean roundI(Match match) throws DataAccessException {
		if (match.getRound() == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	@Transactional
	public boolean roundII(Match match) throws DataAccessException {
		if (match.getRound() == 2) {
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
	public boolean startMatchButton(Match match) throws DataAccessException {
		if (match.getPlayers().size() >= 5) {
			return true;
		} else {
			return false;
		}
	}
	
	@Transactional
	public void startMatch(Match match) throws DataAccessException {
		log.info("Empezar partida.");
		playerService.roleAndCardsAsignation(match);
		match.setRound(1);
		saveMatch(match);
	}
	
	@Transactional
	public boolean HideInvitationButton(Match match) throws DataAccessException {
		if (match.getPlayers().size() < 8) {
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
    	log.info("Avanzar turno.");
    	if ((match.getTurn() + 1) >= jugadores.size()) {
			match.setRound(match.getRound() + 1);
			match.setTurn(0);
			match.setPlays(Plays.CONSUL);
		} else {
			if (match.getRound() == 2) {
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
    private boolean checkNumberOfLoyals(Match match) throws DataAccessException {
    	List<Player> loyals = new ArrayList<Player>();
    	for (Player p: match.getPlayers()) {
    		if (p.getCard1() == Faction.LOYAL) {
    			loyals.add(p);
    		}
    	}
    	return loyals.size() > 0;
    }
    @Transactional
    private boolean checkNumberOfTraitors(Match match) throws DataAccessException {
    	List<Player> traitors = new ArrayList<Player>();
    	for (Player p: match.getPlayers()) {
    		if (p.getCard1() == Faction.TRAITOR) {
    			traitors.add(p);
    		}
    	}
    	return traitors.size() > 0;
    }
    
    
    @Transactional
    public Faction sufragium(Match match) throws DataAccessException {
    	int numeroJugadores = match.getPlayers().size();
    	Faction faccionGanadora = null;
    	if (match.getVotesInFavor() - match.getVotesAgainst() >= 2 && match.getRound() == 3 && checkNumberOfLoyals(match)) {
    		faccionGanadora = Faction.LOYAL;
    	} else if (match.getVotesAgainst() - match.getVotesInFavor() >= 2 && match.getRound() == 3 && checkNumberOfTraitors(match)) {
    		faccionGanadora = Faction.TRAITOR;
    	} else if (numeroJugadores == 5) {
    		if (match.getVotesInFavor() >= 13 ) {
    			if(checkNumberOfTraitors(match)) {
        			faccionGanadora = Faction.TRAITOR;
    			}
    			else {
    				faccionGanadora = Faction.MERCHANT;
    			}
    		}
    		else if (match.getVotesAgainst() >= 13) {
    			if(checkNumberOfLoyals(match)) {
    			faccionGanadora = Faction.LOYAL;
    			} 
    			else {
    				faccionGanadora = Faction.MERCHANT;
    			}
    		}
    	} else if (numeroJugadores == 6) {
    		if (match.getVotesInFavor() >= 15) {
    			if(checkNumberOfTraitors(match)) {
        			faccionGanadora = Faction.TRAITOR;
    			}
    			else {
    				faccionGanadora = Faction.MERCHANT;
    			}
    		}
    		else if (match.getVotesAgainst() >= 15) {
    			if(checkNumberOfLoyals(match)) {
    			faccionGanadora = Faction.LOYAL;
    			} 
    			else {
    				faccionGanadora = Faction.MERCHANT;
    			}
    		}
    	} else if (numeroJugadores == 7) {
    		if (match.getVotesInFavor() >= 17) {
    			if(checkNumberOfTraitors(match)) {
        			faccionGanadora = Faction.TRAITOR;
    			}
    			else {
    				faccionGanadora = Faction.MERCHANT;
    			}
    		}
    		else if (match.getVotesAgainst() >= 17) {
    			if(checkNumberOfLoyals(match)) {
    			faccionGanadora = Faction.LOYAL;
    			} 
    			else {
    				faccionGanadora = Faction.MERCHANT;
    			}
    		}
    	} else if (numeroJugadores == 8) {
    		if (match.getVotesInFavor() >= 20) {
    			if(checkNumberOfTraitors(match)) {
        			faccionGanadora = Faction.TRAITOR;
    			}
    			else {
    				faccionGanadora = Faction.MERCHANT;
    			}
    		}
    		else if (match.getVotesAgainst() >= 20) {
    			if(checkNumberOfLoyals(match)) {
    			faccionGanadora = Faction.LOYAL;
    			} 
    			else {
    				faccionGanadora = Faction.MERCHANT;
    			}
    		}
    	}
    		return faccionGanadora;
    }
}