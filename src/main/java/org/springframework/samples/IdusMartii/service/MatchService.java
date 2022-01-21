package org.springframework.samples.IdusMartii.service;

import org.springframework.samples.IdusMartii.repository.MatchRepository;
import org.springframework.samples.IdusMartii.repository.PlayerRepository;
import org.springframework.samples.IdusMartii.enumerates.Faction;
import org.springframework.samples.IdusMartii.enumerates.Plays;
import org.springframework.samples.IdusMartii.enumerates.Role;
import org.springframework.samples.IdusMartii.enumerates.Vote;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.samples.IdusMartii.model.Player;
import org.springframework.samples.IdusMartii.model.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;

@Slf4j
@Service
public class MatchService {
    @Autowired 
    private MatchRepository matchRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private AuthoritiesService authoritiesService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private AchievementService achievementService;
    @Autowired
    private AchievementUserService achievementUserService;
    
	@Transactional
	public Iterable<Match> findAll(){
		log.info("Buscando partidas...");
		return matchRepository.findAll();
	}

	@Transactional
	public boolean isAdmin(User user) throws DataAccessException {
		if (authoritiesService.getAuthorities(user.getUsername())) {
			log.info("Eres administrador. Estás haciendo un buen trabajo, sigue así.");
			return true;
		} else {
			return false;
		}
	}
	
    @Transactional
    public List<Match> matches(User user) throws DataAccessException {
    	log.info("Buscando partidas...");
    	log.debug("Usuario: " + user);
    	if (!isAdmin(user)) {
    		List<Match> matches = playerService.findMatchesFromUser(user);
    		return matches;
    	} else {
    		return (List<Match>)findAll();
    	}
    }
    
	@Transactional
    public List<Match> matchesCreated(User user) throws DataAccessException {
    	log.info("Buscando partidas...");
    	log.debug("Usuario: " + user);
    	if (!isAdmin(user)) {
    		List<Match> matches = playerService.findMatchesFromHost(user);
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
			if(m.getRound()==0){
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
    public List<Player> findWinners(Match match){
    	log.info("Buscando ganadores...");
    	Faction faccion = match.getWinner();
    	return playerRepository.findWinners(match, faccion);
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
    	Faction faccionGanadora = Faction.MERCHANT;
    	if (match.getVotesInFavor() - match.getVotesAgainst() >= 2 && match.getRound() == 3 && checkNumberOfLoyals(match)) {
    		faccionGanadora = Faction.LOYAL;
    	} else if (match.getVotesAgainst() - match.getVotesInFavor() >= 2 && match.getRound() == 3 && checkNumberOfTraitors(match)) {
    		faccionGanadora = Faction.TRAITOR;
    	} else if (numeroJugadores == 5) {
    		if (match.getVotesInFavor() >= 13 && checkNumberOfTraitors(match)) {
    			faccionGanadora = Faction.TRAITOR;
    		} else if (match.getVotesAgainst() == 13 && checkNumberOfLoyals(match)) {
    			faccionGanadora = Faction.LOYAL;
    		}
    	} else if (numeroJugadores == 6) {
    		if (match.getVotesInFavor() >= 15 && checkNumberOfTraitors(match)) {
    			faccionGanadora = Faction.TRAITOR;
    		} else if (match.getVotesAgainst() == 15 && checkNumberOfLoyals(match)) {
    			faccionGanadora = Faction.LOYAL;
    		}
    	} else if (numeroJugadores == 7) {
    		if (match.getVotesInFavor() >= 17 && checkNumberOfTraitors(match)) {
    			faccionGanadora = Faction.TRAITOR;
    		} else if (match.getVotesAgainst() == 17 && checkNumberOfLoyals(match)) {
    			faccionGanadora = Faction.LOYAL;
    		}
    	} else if (numeroJugadores == 8) {
    		if (match.getVotesInFavor() >= 20 && checkNumberOfTraitors(match)) {
    			faccionGanadora = Faction.TRAITOR;
    		} else if (match.getVotesAgainst() == 20 && checkNumberOfLoyals(match)) {
    			faccionGanadora = Faction.LOYAL;
    		}
    	}
    	if (faccionGanadora == Faction.MERCHANT && match.getRound() != 3) {
    		throw new DataAccessException("La partida no ha terminado aún") {};
    	} else {
    		return faccionGanadora;
    	}
    }
    
}