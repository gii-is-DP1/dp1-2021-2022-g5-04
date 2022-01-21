package org.springframework.samples.IdusMartii.web;



import java.util.List;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

import org.springframework.samples.IdusMartii.service.MatchService;
import org.springframework.samples.IdusMartii.service.UserService;
import org.springframework.samples.IdusMartii.service.PlayerService;
import org.springframework.samples.IdusMartii.service.AchievementService;
import org.springframework.samples.IdusMartii.service.AchievementUserService;
import org.springframework.samples.IdusMartii.service.CurrentUserService;
import org.springframework.samples.IdusMartii.service.InvitationService;
import org.springframework.samples.IdusMartii.enumerates.Plays;
import org.springframework.samples.IdusMartii.enumerates.Role;
import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.Match;
import org.springframework.samples.IdusMartii.model.Player;
import org.springframework.samples.IdusMartii.model.User;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequestMapping("/matches")
public class MatchController {
	
	@Autowired
	private MatchService matchService;
	@Autowired
	private CurrentUserService currentUserService;
	@Autowired
	private UserService userService;
	@Autowired
	private PlayerService playerService;
	@Autowired
	private InvitationService invitationService;
	@Autowired
    AchievementUserService achievementUserService;
	@Autowired
    AchievementService achievementService;
	
	

	@GetMapping()
	public String matchesList(ModelMap modelMap) {
		log.info("Accediendo a la lista de partidas...");
		String vista = "matches/matchesListMenu";
		User user = userService.findUser(currentUserService.showCurrentUser()).get();
		log.info("Accediendo al servicio de partidas por el metodo matches()");
		log.debug("usuario: " + user);
		List<Match> matches = matchService.matches(user);
		log.info("Acceso al servicio de partidas por el metodo isAdmin()");
		log.debug("usuario: " + user);
		modelMap.addAttribute("admin", matchService.isAdmin(user));
		modelMap.addAttribute("matches", matches);
		return vista;
		
	}
	
	@GetMapping(path="/finished")
	public String matchesListFinished(ModelMap modelMap) {
		log.info("Accediendo a la lista de partidas terminadas...");
		String vista = "matches/matchesList";
		User user = userService.findUser(currentUserService.showCurrentUser()).get();
		log.info("Accediendo al servicio de partidas por el metodo matchesfinished()");
		List<Match> matches = matchService.matchesFinished();
		log.info("Acceso al servicio de partidas por el metodo isAdmin()");
		log.debug("usuario: " + user);
		modelMap.addAttribute("admin", matchService.isAdmin(user));
		modelMap.addAttribute("matches", matches);
		modelMap.addAttribute("CorP", "terminadas");
		return vista;
	}
	
	@GetMapping(path="/progress")
	public String matchesListProgress(ModelMap modelMap) {
		log.info("Accediendo a la lista de partidas en curso...");
		String vista = "matches/matchesList";
		User user = userService.findUser(currentUserService.showCurrentUser()).get();
		log.info("Accediendo al servicio de partidas por el metodo matchesInProgress_NotFinished()");
		List<Match> matches = matchService.matchesInProgress_NotFinished();
		log.info("Accediendo al servicio de partidas por el metodo isAdmin()");
		modelMap.addAttribute("admin", matchService.isAdmin(user));
		modelMap.addAttribute("matches", matches);
		modelMap.addAttribute("CorP", "en progreso");
		return vista;
	}
	
	@GetMapping(path="/created")
	public String matchesListCreated(ModelMap modelMap) {
		log.info("Accediendo a la lista de partidas creadas...");
		String vista = "matches/matchesList";
		User user = userService.findUser(currentUserService.showCurrentUser()).get();
		log.info("Accediendo al servicio de partidas por el metodo matchesCreated()");
		List<Match> matches = matchService.matchesCreated(user);
		log.info("Acceso al servicio de partidas por el metodo isAdmin()");
		log.debug("usuario: " + user);
		modelMap.addAttribute("admin", matchService.isAdmin(user));
		modelMap.addAttribute("matches", matches);
		modelMap.addAttribute("CorP", "creadas");
		return vista;
	}
	
	@GetMapping(path="/lobby")
	public String matchesListLobby(ModelMap modelMap) {
		log.info("Accediendo a la lista de partidas en lobby...");
		String vista = "matches/matchesList";
		User user = userService.findUser(currentUserService.showCurrentUser()).get();
		log.info("Accediendo al servicio de partidas por el metodo matchesLobby()");
		List<Match> matches = matchService.matchesLobby();
		log.info("Accediendo al servicio de partidas por el metodo isAdmin()");
		modelMap.addAttribute("admin", matchService.isAdmin(user));
		modelMap.addAttribute("matches", matches);
		modelMap.addAttribute("CorP", "en lobby");
		return vista;
	}
	
	@GetMapping(path="/played")
	public String matchesListPlayed(ModelMap modelMap) {
		log.info("Accediendo a la lista de partidas jugadas...");		
		String vista = "matches/matchesList";
		User user = userService.findUser(currentUserService.showCurrentUser()).get();
		log.info("Accediendo al servicio de partidas por el metodo matches()");
		List<Match> matches = matchService.matches(user);
		log.info("Acceso al servicio de partidas por el metodo isAdmin()");
		log.debug("usuario: " + user);
		modelMap.addAttribute("admin", matchService.isAdmin(user));
		modelMap.addAttribute("matches", matches);
		modelMap.addAttribute("CorP", "jugadas");
		return vista;
	}
	
	@GetMapping(path="/spectator")
	public String spectatorMode(ModelMap modelMap, HttpServletResponse response) {
		log.info("Accediendo a la lista de partidas que se pueden espectar...");
		String vista = "matches/spectatorModeList";
		response.addHeader("Refresh","30");
		User user = userService.findUser(currentUserService.showCurrentUser()).get();
		log.info("Accediendo al servicio de partidas por el metodo matchesInProgress_NotFinished()");
		List<Match> matches = matchService.matchesInProgress_NotFinished();
		log.info("Acceso al servicio de partidas por el metodo isAdmin()");
		log.debug("usuario: " + user);
		modelMap.addAttribute("admin", matchService.isAdmin(user));
		modelMap.addAttribute("matches", matches);
		return vista;
	}
	
	@GetMapping(path="/{id_match}/spectator")
	public String spectatorModeMatch(ModelMap modelMap, @PathVariable("id_match") int id_match, HttpServletResponse response) {
		log.info("Accediendo a la partida...");
		String vista = "matches/spectatorMode";
		response.addHeader("Refresh","20");
		log.info("Accediendo al servicio de partidas por el metodo findById()");
		log.debug("id: " + id_match);
		Match match = this.matchService.findById(id_match);
		modelMap.addAttribute("match", match);
		return vista;
	}
	
	@GetMapping(path="/new")
	public String crearPartida(ModelMap modelMap) {
		log.info("Acceso a formulario de creacion de partida.");
		String vista = "matches/crearPartida";
		User user = userService.findUser(currentUserService.showCurrentUser()).get();
		modelMap.addAttribute("match", new Match());
		log.info("Acceso al servicio de partidas por el metodo isAdmin()");
		log.debug("usuario: " + user);
		modelMap.addAttribute("admin", matchService.isAdmin(user));
		return vista;
	}
	
	@PostMapping(path="/save")
	public String guardarPartida(@Valid Match match, BindingResult result, ModelMap modelMap) {
		log.info("Creando partida...");
			match.setRound(0);
			match.setTurn(0);
			match.setVotesInFavor(0);
			match.setVotesAgainst(0);
			match.setPlays(Plays.EDIL);
			Player host = new Player();
			host.setUser(userService.findUser(currentUserService.showCurrentUser()).get());
			host.setName("host");
			host.setRole(Role.CONSUL);
			host.setMatch(match);
			log.info("Acceso al servicio de partidas por el metodo saveMatch()");
			log.debug("partida: " + match);
			matchService.saveMatch(match);
			log.info("Acceso al servicio de jugadores por el metodo savePlayer()");
			log.debug("jugador: " + host);
			playerService.savePlayer(host);
			return "redirect:/matches/" + match.getId() + "/new";
	}
	
	@PostMapping(path="/{id_match}/{id_invt}/aceptar")
	public String aceptarPartida(ModelMap modelMap, @PathVariable("id_match") int id_match, @PathVariable("id_invt") int id_invt) {
		log.info("Aceptando solicitud de invitacion a partida...");
		log.info("Acceso al servicio de partidas por el metodo findById()");
		log.debug("Id : " + id_match);
		Match match = matchService.findById(id_match);
			User user = userService.findUser(currentUserService.showCurrentUser()).get();
		log.info("Acceso al servicio de invitaciones de partidas por el metodo acceptInvitation()");
		log.debug("Id: " + id_invt + ", partida: " + match + ", usuario: "+ user);
			invitationService.acceptInvitation(id_invt, match, user);
			return "redirect:/matches/" + match.getId() + "/new";
	}

	@GetMapping(path="/{id}/new")
	public String editarPartida(ModelMap modelMap, @PathVariable("id") int id) {
		log.info("");
		String vista = "matches/editarPartida";
		log.info("Acceso al servicio de partidas por el metodo saveMatch()");
		log.debug("Id : " + id);
		Match match = this.matchService.findById(id);
		String currentUser = currentUserService.showCurrentUser();
		User user = userService.findbyUsername(currentUser);
		log.info("Acceso al servicio de jugadores por el metodo findByMatchAndUser()");
		log.debug("partida: " + match + ", usuario: " + user);
		Player player = playerService.findByMatchAndUser(match, user);
		log.info("Acceso al servicio de partidas por el metodo noHostPlayers()");
		log.debug("partida: " + match);
		List<Player> noHostPlayers = matchService.noHostPlayers(match);
		modelMap.addAttribute("current", currentUser);
		modelMap.addAttribute("match", match);
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("noHostPlayers", noHostPlayers);
		modelMap.addAttribute("startMatch", matchService.startMatchButton(match));
		modelMap.addAttribute("hideInvitationButton", matchService.HideInvitationButton(match));
		modelMap.addAttribute("isHost", matchService.isHost(player, match));
		modelMap.addAttribute("admin", matchService.isAdmin(user));
		return vista;
	}

	@GetMapping(path="/{id}/match")
	public String comenzarPartida(ModelMap modelMap, @PathVariable("id") int id, HttpServletResponse response) {
		log.info("Comenzando partida...");
		//response.addHeader("Refresh","20");
		log.info("Acceso al servicio de partidas por el metodo findById()");
		log.debug("Id : " + id);
		Match match = this.matchService.findById(id);
		if(match.getRound()==0){
			match.setRound(1);
		}
		String currentuser = currentUserService.showCurrentUser();
		User usuario = userService.findUser(currentUserService.showCurrentUser()).get();
		log.info("Acceso al servicio de jugadores por el metodo findByMatchAndUser()");
		log.debug("partida: " + match + ", usuario: " + usuario);		
		Player player_actual = playerService.findByMatchAndUser(match, usuario);
		modelMap.addAttribute("votos", matchService.votes(match));
		modelMap.addAttribute("votedUser", matchService.votedUser(match));
		modelMap.addAttribute("voteCondition", playerService.showVoteCondition(player_actual.getVote()));
		modelMap.addAttribute("voteCard", playerService.showVoteCard(player_actual.getVote()));
		modelMap.addAttribute("playerY", playerService.playerYellow(match));
		modelMap.addAttribute("mostrarCartas", playerService.showCards(player_actual));
		modelMap.addAttribute("roleCard", playerService.showCardRole(player_actual));
		modelMap.addAttribute("card1", playerService.showFactionCard(player_actual.getCard1()));
		modelMap.addAttribute("card2", playerService.showFactionCard(player_actual.getCard2()));
		modelMap.addAttribute("votar", playerService.canVote(player_actual, match));
		modelMap.addAttribute("revisarVoto", playerService.checkVote(player_actual, match));
		modelMap.addAttribute("elegirFaccion", playerService.chooseFaction(player_actual, match));
		modelMap.addAttribute("contarVotos", playerService.countVotes(player_actual, match));
		modelMap.addAttribute("ronda1", matchService.roundI(match));
		modelMap.addAttribute("ronda2", matchService.roundII(match));
		modelMap.addAttribute("elegirRol", playerService.chooseRol(player_actual, match));
		modelMap.addAttribute("jugadoresSinRolConsul", matchService.playersWithNoConsulRole(match));
		modelMap.addAttribute("edilesSinAsignar", matchService.edilNotAsigned(match));
		modelMap.addAttribute("pretorSinAsignar", matchService.pretorNotAsigned(match));
		modelMap.addAttribute("votoAmarilloRevisado", match.getPlays() == Plays.YELLOWEDIL);
		modelMap.addAttribute("edilAmarilloRevisado", player_actual == playerService.playerYellow(match));
		modelMap.addAttribute("numeroJugadoresCinco", match.getPlayers().size() == 5);
		modelMap.addAttribute("current", currentuser);
		modelMap.addAttribute("match", match);
		modelMap.addAttribute("ediles", playerService.findByRole(match, Role.EDIL));
		modelMap.addAttribute("admin", matchService.isAdmin(usuario));
		return matchService.partidaEnCurso(match, id);
	}
	@GetMapping(path="/{id}/rolesAsignados")
	public String rolesAsignados(ModelMap modelMap, @PathVariable("id") int id) {
		log.info("Acesso al metodo rolesAsignados()");
		Match match = matchService.findById(id);
		playerService.rolesAsigned(match);
		match.setPlays(Plays.EDIL);
		matchService.saveMatch(match);
		return "redirect:/matches/" + id + "/match";
	}
	@GetMapping(path="/{id}/ganador") 
	public String ganador(ModelMap modelMap, @PathVariable("id") int id, HttpServletResponse response) {
		response.addHeader("Refresh","20");
		String vista = "matches/ganador";
		Match match = this.matchService.findById(id);
		User usuario = userService.findUser(currentUserService.showCurrentUser()).get();
		Player player_actual = playerService.findByMatchAndUser(match, usuario);
		modelMap.addAttribute("faccionGanadora", matchService.sufragium(match));
		modelMap.addAttribute("cartaFaccion", playerService.showFactionCard(matchService.sufragium(match)));
		modelMap.addAttribute("winner", playerService.winner(player_actual, matchService.sufragium(match)));
		modelMap.addAttribute("votosAFavor", match.getVotesInFavor());
		modelMap.addAttribute("votosEnContra", match.getVotesAgainst());
		modelMap.addAttribute("admin", matchService.isAdmin(usuario));
		match.setFinished(true);
		match.setWinner(matchService.sufragium(match));
		matchService.saveMatch(match);
		matchService.registrarGanadores(match);
		return vista;
	}
	
	@PostMapping(path="/{id}/game/save")
	public String guardarPartidaEmpezada(ModelMap modelMap, @PathVariable("id") int id) {
		log.info("Acceso al servicio de partidas por el metodo findById()");
		log.debug("Id : " + id);
		Match match = this.matchService.findById(id);
		log.info("Acceso al servicio de partidas por el metodo startMatch()");
		log.debug("Partida : " + match);
		matchService.startMatch(match);
		log.info("Acceso al servicio de jugadores por el metodo jugadoresPartida()");
		log.debug("Partida : " + match);
		List<Player> g = playerService.jugadoresPartida(match);
		for (int i = 0; i<g.size();i++) {
			User u = g.get(i).getUser();
			String username = u.getUsername();
		log.info("Acesso al servicio de jugadores por el metodo findbyUsername()");
		log.debug("Nombre de usuario: " + username);
			playerService.findbyUsername(username);
		log.info("Acesso al servicio de logros por el metodo findByAchievementType()");
		log.debug("tipo: jugadas");
			List<Achievement> jugadas = achievementService.findByAchievementType("jugadas");
			for(int k = 0; k<jugadas.size();k++) {
				log.info("Acceso a 2 metodos del servicio de logrosJugadores");
				if (achievementUserService.checkAchievementJugadas(u,jugadas.get(k).getValor()) == true) {
					achievementUserService.saveAchievementUser(u.getUsername(),jugadas.get(k).getId());
				}
			}
		}
		log.info("Acesso al servicio de jugadores por el metodo roleAndCardsAsignation()");
		log.debug("Partida : " + match);
		playerService.roleAndCardsAsignation(match);
		match.setRound(1);
		log.info("Guardando partida...");
		matchService.saveMatch(match);
		return  "redirect:/matches/" + id + "/match";
	}

		

}

