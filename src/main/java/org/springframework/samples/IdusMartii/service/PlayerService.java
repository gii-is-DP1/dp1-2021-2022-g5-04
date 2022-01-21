package org.springframework.samples.IdusMartii.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import lombok.extern.slf4j.Slf4j;

import org.springframework.samples.IdusMartii.repository.InvitationRepository;
import org.springframework.samples.IdusMartii.repository.PlayerRepository;
import org.springframework.samples.IdusMartii.enumerates.Faction;
import org.springframework.samples.IdusMartii.enumerates.Plays;
import org.springframework.samples.IdusMartii.enumerates.Role;
import org.springframework.samples.IdusMartii.enumerates.Vote;
import org.springframework.samples.IdusMartii.model.Invitation;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.samples.IdusMartii.model.Player;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PlayerService {
	@Autowired
	private PlayerRepository playerRepository;
	@Autowired 
	private InvitationRepository invitationRepository;
	@Autowired
	private MatchService matchService;
	@Autowired
	private AuthoritiesService authoritiesService;
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
		log.info("Guardando jugador...");
		playerRepository.save(player);
	}
	
	@Transactional
	public void deletePlayer(Player player) throws DataAccessException {
		playerRepository.delete(player);
	}
	
	@Transactional
	public String deletePlayerFromMatch(Player player, Match match, User current, int matchId, ModelMap modelMap) throws DataAccessException {
		log.info("Eliminando jugador de una partida...");
		if(current == match.getPlayers().get(0).getUser()) {
			match.getPlayers().remove(player);
			matchService.saveMatch(match);
			return "redirect:/matches/" + matchId + "/new";
		} else {
			modelMap.addAttribute("message", "No puedes expulsar a un jugador sin ser el host de la partida");
			return "/exception";
		}
		
	}
	
	@Transactional
	public String deletePlayerWithInvitaton(Player player, Match match, User user, User current, int matchId, ModelMap modelMap) throws DataAccessException {
		List<Invitation> invitations = invitationRepository.findByUserAndMatch(user, match);
		for(Invitation invitation:invitations){
			invitationRepository.delete(invitation);
		}
		return deletePlayerFromMatch(player, match, current, matchId, modelMap);
	}

	@Transactional
	public Player findbyId(Integer ID) throws DataAccessException {
		log.info("Buscando jugador...");
		log.debug("ID: " + ID);
		return playerRepository.findById(ID).get();
	}
	
	@Transactional
	public List<Player> findbyUsername(String username) throws DataAccessException {
		log.info("Buscando jugador...");
		log.debug("username: " + username);
		return playerRepository.findByUsername(username);
	}
	
	@Transactional
	public List<Player> jugadoresPartida(Match match) throws DataAccessException {
		log.info("Buscando jugadores de la partida...");
		log.debug("Partida: " + match);
		return playerRepository.findByMatchId(match);
	}
	@Transactional
	public void changeVote(Player player) throws DataAccessException {
		if (player.getVote() == Vote.RED) {
			player.setVote(Vote.GREEN);
		} else {
			player.setVote(Vote.RED);
		}
		savePlayer(player);
	}
	
	@Transactional
	public Player findByMatchAndUser(Match match, User user) throws DataAccessException {
		return playerRepository.findByMatchAndUser(match, user);
	}
	
	@Transactional
	public List<Player> findbyUsernameMatchFinished(String username) throws DataAccessException {
		List<Player> players = playerRepository.findByUsername(username);
		List<Player> result = new ArrayList<Player>();
		for(Player p : players){
			if(p.getMatch().isFinished()){
				result.add(p);
			}

		}
		
		return result;
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
	public List<Match> findMatchesFromHost(User user) throws DataAccessException {
		List<Match> matchesResult = new ArrayList<Match>();
		List<Match> matchesIterate = playerRepository.findMatchesFromUser(user);
		for (Match m : matchesIterate){
			if(m.getPlayers().get(0).getUser()==user){
				matchesResult.add(m);
			}
		}
		return matchesResult;
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
	public void roleAndCardsAsignation(Match match) throws DataAccessException {
		List<Player> players = match.getPlayers();
		for (int i = 0; i< players.size(); i++) {
			if (i == 0) {
				players.get(i).setRole(Role.CONSUL);
			}
			else if (i == 1) {
				players.get(i).setRole(Role.PRETOR);
			}
			else if (i == 2) {
				players.get(i).setRole(Role.EDIL);
			}
			else if (i == 3) {
				players.get(i).setRole(Role.EDIL);
			}
			else {
				players.get(i).setRole(Role.NO_ROL);
			}
		}
		List<Faction> cards = new ArrayList<Faction>();
		for (int i = 0; i<players.size()-1;i++) {
			cards.add(Faction.LOYAL);
			cards.add(Faction.TRAITOR);
		}
		cards.add(Faction.MERCHANT);
		cards.add(Faction.MERCHANT);
		
		for (int i = 0; i<players.size();i++) {
			Integer r = (int) Math.floor(Math.random()*(cards.size()-1));
			players.get(i).setCard1(cards.get(r));
			cards.remove(cards.get(r));
			r = (int) Math.floor(Math.random()*(cards.size()-1));
			players.get(i).setCard2(cards.get(r));
			cards.remove(cards.get(r));
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
			} else if (player.getRole() == Role.CONSUL && player == match.getPlayers().get(0) && match.getRound() == 2 && afterVotes(player, match)){
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
		if(match.getRound() == 1 && match.getTurn() < jugadores.size() - 1) {
			for (int i = 0; i <= jugadores.size() - 1; i++) {
				roles.add(jugadores.get((i)).getRole());
			}
			for (int i = 0; i <= jugadores.size() - 1; i++) {
				jugadores.get((i+1)%jugadores.size()).setRole(roles.get(i));
			}
		} else if (match.getRound() == 2 || (match.getRound() == 1 && match.getTurn() == jugadores.size() - 1)){
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
	public boolean winner(Player jugador, Faction faction) throws DataAccessException {
		if (jugador.getCard1() == faction) {
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
	
	@Transactional
	public List<Player> findWinners(Match match){
		return playerRepository.findWinners(match, match.getWinner());
	}
	@Transactional
	public String showCardRole(Player player) throws DataAccessException {
		if (player.getRole()==Role.CONSUL) {
			String result =  "consul";
			return result;
		}
		else if (player.getRole()==Role.EDIL){
			String result =  "edil";
			return result;
		}
		else if (player.getRole()==Role.PRETOR) {
			String result =  "pretor";
			return result;

		}
		else{
			String result =  "no_role";
			return result;

		}
		
	}
	@Transactional
	public String showFactionCard(Faction faction) throws DataAccessException {
		if (faction==Faction.LOYAL) {
			String result =  "loyal";
			return result;
		}
		else if (faction==Faction.MERCHANT) {
			String result =  "merchant";
			return result;
		}
		else{
			String result =  "traitor";
			return result;
		}
		
	}
	@Transactional
	public boolean showVoteCondition(Vote vote) throws DataAccessException {
		if (vote == null) {
			return false;
		} else {
			return true;
		}
	}
	@Transactional
	public String showVoteCard(Vote vote) throws DataAccessException {
		if (vote==Vote.GREEN) {
			String result =  "green";
			return result;
		} else if (vote==Vote.RED) {
			String result =  "red";
			return result;
		} else {
			String result =  "yellow";
			return result;
		}
	}
	@Transactional
	public String continueTurn(Match match, int idMatch) throws DataAccessException {
		String retornar = "";
		if (match.getRound() < 3) {
			try {
				matchService.sufragium(match);
			} catch (DataAccessException e) {
				retornar = "redirect:/matches/" + idMatch + "/match";
			}
		} else {
			retornar = "redirect:/matches/" + idMatch + "/ganador";
		}
		return retornar;
	}
	@Transactional
	public List<Player> findPlayersFromUser(User user) {
		return playerRepository.findPlayersFromUser(user);
	}
	
	@Transactional
	public void deleteAllPlayersFromUser(User user) {
		List<Player> playersFromUser = findPlayersFromUser(user);
		for (Player p: playersFromUser) {
			playerRepository.delete(p);
		}
	}
	@Transactional
	public boolean isAdmin(User user) throws DataAccessException {
		if (authoritiesService.getAuthorities(user.getUsername())) {
			log.info("El user es admin");
			return true;
		} else {
			log.info("El user no es admin");
			return false;
		}
	}
}
