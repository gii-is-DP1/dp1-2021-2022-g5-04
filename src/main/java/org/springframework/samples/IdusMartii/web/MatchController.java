package org.springframework.samples.IdusMartii.web;


import java.util.ArrayList;
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
import org.springframework.samples.IdusMartii.service.AuthoritiesService;
import org.springframework.samples.IdusMartii.service.CurrentUserService;
import org.springframework.samples.IdusMartii.service.InvitationService;
import org.springframework.samples.IdusMartii.IdusMartiiApplication;
import org.springframework.samples.IdusMartii.enumerates.Faction;
import org.springframework.samples.IdusMartii.enumerates.Plays;
import org.springframework.samples.IdusMartii.enumerates.Role;
import org.springframework.samples.IdusMartii.enumerates.Vote;
import org.springframework.samples.IdusMartii.model.Achievement;
import org.springframework.samples.IdusMartii.model.Invitation;
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
		String vista = "matches/matchesListMenu";
		User user = userService.findUser(currentUserService.showCurrentUser()).get();
		List<Match> matches = matchService.matches(user);
		modelMap.addAttribute("admin", matchService.isAdmin(user));
		modelMap.addAttribute("matches", matches);
		return vista;
		
	}
	
	@GetMapping(path="/finished")
	public String matchesListFinished(ModelMap modelMap) {
		String vista = "matches/matchesList";
		User user = userService.findUser(currentUserService.showCurrentUser()).get();
		List<Match> matches = matchService.matchesFinished();
		modelMap.addAttribute("admin", matchService.isAdmin(user));
		modelMap.addAttribute("matches", matches);
		modelMap.addAttribute("CorP", "terminadas");
		return vista;
	}
	
	@GetMapping(path="/progress")
	public String matchesListProgress(ModelMap modelMap) {
		String vista = "matches/matchesList";
		User user = userService.findUser(currentUserService.showCurrentUser()).get();
		List<Match> matches = matchService.matchesInProgress_NotFinished();
		modelMap.addAttribute("admin", matchService.isAdmin(user));
		modelMap.addAttribute("matches", matches);
		modelMap.addAttribute("CorP", "en progreso");
		return vista;
	}
	
	@GetMapping(path="/created")
	public String matchesListCreated(ModelMap modelMap) {
		String vista = "matches/matchesList";
		User user = userService.findUser(currentUserService.showCurrentUser()).get();
		List<Match> matches = matchService.matchesCreated(user);
		modelMap.addAttribute("admin", matchService.isAdmin(user));
		modelMap.addAttribute("matches", matches);
		modelMap.addAttribute("CorP", "creadas");
		return vista;
	}
	
	@GetMapping(path="/lobby")
	public String matchesListLobby(ModelMap modelMap) {
		String vista = "matches/matchesList";
		User user = userService.findUser(currentUserService.showCurrentUser()).get();
		List<Match> matches = matchService.matchesLobby();
		modelMap.addAttribute("admin", matchService.isAdmin(user));
		modelMap.addAttribute("matches", matches);
		modelMap.addAttribute("CorP", "en lobby");
		return vista;
	}
	
	@GetMapping(path="/played")
	public String matchesListPlayed(ModelMap modelMap) {
		String vista = "matches/matchesList";
		User user = userService.findUser(currentUserService.showCurrentUser()).get();
		List<Match> matches = matchService.matches(user);
		modelMap.addAttribute("admin", matchService.isAdmin(user));
		modelMap.addAttribute("matches", matches);
		modelMap.addAttribute("CorP", "jugadas");
		return vista;
	}
	
	@GetMapping(path="/spectator")
	public String spectatorMode(ModelMap modelMap) {
		String vista = "matches/spectatorModeList";
		User user = userService.findUser(currentUserService.showCurrentUser()).get();
		List<Match> matches = matchService.matchesInProgress_NotFinished();
		modelMap.addAttribute("admin", matchService.isAdmin(user));
		modelMap.addAttribute("matches", matches);
		return vista;
	}
	
	@GetMapping(path="/{id_match}/spectator")
	public String spectatorModeMatch(ModelMap modelMap, @PathVariable("id_match") int id_match, HttpServletResponse response) {
		String vista = "matches/spectatorMode";
		Match match = this.matchService.findById(id_match);
		modelMap.addAttribute("match", match);
		return vista;
	}
	
	@GetMapping(path="/new")
	public String crearPartida(ModelMap modelMap) {
		String vista = "matches/crearPartida";
		User user = userService.findUser(currentUserService.showCurrentUser()).get();
		modelMap.addAttribute("match", new Match());
		modelMap.addAttribute("admin", matchService.isAdmin(user));
		return vista;
	}
	
	@PostMapping(path="/save")
	public String guardarPartida(@Valid Match match, BindingResult result, ModelMap modelMap) {
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
			matchService.saveMatch(match);
			playerService.savePlayer(host);
			modelMap.addAttribute("message", "¡Jugador guardado correctamente!");
			return "redirect:/matches/" + match.getId() + "/new";
	}
	
	@PostMapping(path="/{id_match}/{id_invt}/aceptar")
	public String aceptarPartida(ModelMap modelMap, @PathVariable("id_match") int id_match, @PathVariable("id_invt") int id_invt) {
			Match match = matchService.findById(id_match);
			User user = userService.findUser(currentUserService.showCurrentUser()).get();
			invitationService.acceptInvitation(id_invt, match, user);
			return "redirect:/matches/" + match.getId() + "/new";
	}

	@GetMapping(path="/{id}/new")
	public String editarPartida(ModelMap modelMap, @PathVariable("id") int id) {
		String vista = "matches/editarPartida";
		Match match = this.matchService.findById(id);
		String currentUser = currentUserService.showCurrentUser();
		User user = userService.findbyUsername(currentUser);
		Player player = playerService.findByMatchAndUser(match, user);
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
		String vista = "matches/partidaEnCurso";
		response.addHeader("Refresh","20"); //Es una decisión de diseño y la otra alternativa es implementar el RestController, pero no la cogimos por falta de tiempo
		Match match = this.matchService.findById(id);
		if(match.getRound()==0){
			match.setRound(1);
		}
		String currentuser = currentUserService.showCurrentUser();
		User usuario = userService.findUser(currentUserService.showCurrentUser()).get();
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
		modelMap.addAttribute("current", currentuser);
		modelMap.addAttribute("match", match);
		modelMap.addAttribute("ediles", playerService.findByRole(match, Role.EDIL));
		modelMap.addAttribute("admin", matchService.isAdmin(usuario));
		if (match.getRound() == 3 || matchService.sufragium(match) != Faction.MERCHANT) {
			return "redirect:/matches/" + id + "/ganador";
		} else {
			return vista;
		}
	}
	@GetMapping(path="/{id}/rolesAsignados")
	public String rolesAsignados(ModelMap modelMap, @PathVariable("id") int id) {
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
		Match match = this.matchService.findById(id);
		matchService.startMatch(match);
		List<Player> g = playerService.jugadoresPartida(match);
		for (int i = 0; i<g.size();i++) {
			User u = g.get(i).getUser();
			String username = u.getUsername();
			playerService.findbyUsername(username);
			List<Achievement> jugadas = achievementService.findByAchievementType("jugadas");
			for(int k = 0; k<jugadas.size();k++) {
				if (achievementUserService.checkAchievementJugadas(u,jugadas.get(k).getValor()) == true) {
					achievementUserService.saveAchievementUser(u.getUsername(),jugadas.get(k).getId());
				}
			}
		}
		playerService.roleAndCardsAsignation(match);
		match.setRound(1);
		matchService.saveMatch(match);
		return  "redirect:/matches/" + id + "/match";
	}

		

}

