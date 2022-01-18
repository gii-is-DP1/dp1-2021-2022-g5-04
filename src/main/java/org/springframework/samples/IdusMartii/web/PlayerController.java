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

import org.springframework.samples.IdusMartii.service.InvitationService;

import org.springframework.samples.IdusMartii.service.AuthoritiesService;
import org.springframework.samples.IdusMartii.service.CurrentUserService;

import org.springframework.samples.IdusMartii.service.MatchService;
import org.springframework.samples.IdusMartii.service.PlayerService;
import org.springframework.samples.IdusMartii.service.UserService;
import org.springframework.samples.IdusMartii.enumerates.Faction;
import org.springframework.samples.IdusMartii.enumerates.Plays;
import org.springframework.samples.IdusMartii.enumerates.Role;
import org.springframework.samples.IdusMartii.enumerates.Vote;
import org.springframework.samples.IdusMartii.model.Player;
import org.springframework.samples.IdusMartii.model.User;
import org.springframework.samples.IdusMartii.model.Match;

@Controller
@RequestMapping("/players")
public class PlayerController {
	
	@Autowired
	private PlayerService playerService;
	@Autowired
	private MatchService matchService;
	@Autowired
	private InvitationService invitationService;
	@Autowired
	private CurrentUserService currentUserService;
	@Autowired
	private UserService userService;
	@GetMapping()
	public String listadoJugadores(ModelMap modelMap) {
		String vista = "players/listadoJugadores";
		Iterable<Player> players = playerService.findAll();
		modelMap.addAttribute("players", players);
		return vista;
	}
	
	@GetMapping(path="/new")
	public String crearJugador(ModelMap modelMap) {
		String vista = "players/editarJugador";
		modelMap.addAttribute("player", new Player());
		return vista;
	}
	@GetMapping(path="/{id}/{idMatch}/revisar")
    public String revisarVoto(@PathVariable("id") int id, @PathVariable("idMatch") int idMatch, ModelMap modelMap) {
        String vista = "players/revisadoVoto";
		User user = userService.findUser(currentUserService.showCurrentUser()).get();
        Player player = playerService.findbyId(id);
        modelMap.addAttribute("player", player);
        modelMap.addAttribute("idMatch", idMatch);
        modelMap.addAttribute("admin", matchService.isAdmin(user));
        if (player.getVote() == Vote.YELLOW) {
        	return "redirect:/players/" + id + "/" + idMatch + "/revisarVotoYellow";
        } else {
            return vista;
        }
    
    }
	

	@PostMapping(path="/new")
	public String guardarJugador(@Valid Player player, BindingResult result, ModelMap modelMap) {
		String vista = "players/listadoJugadores";
		if (result.hasErrors()) {
			modelMap.addAttribute("players", player);
			return "players/editarJugador";
		} else {
			playerService.savePlayer(player);
			modelMap.addAttribute("message", "¡Jugador guardado correctamente!");
		}
		return vista;
	}
	
	@PostMapping(path="/{idPlayer}/{idMatch}/expulsar")
	public String expulsarJugador(@PathVariable("idPlayer") int id, @PathVariable("idMatch") int matchId) {
		Player player = playerService.findbyId(id);
		User user = player.getUser();
		
		if(invitationService.findByUser(user).size()==0){
			playerService.deletePlayer(player);
		}
		else{
			Match match = matchService.findById(matchId);
			playerService.deletePlayerWithInvitaton(player, match, user);
		}
		
			
		return "redirect:/matches/" + matchId + "/new";
	}

	@PostMapping(path="/{id}/{idMatch}/{voto}")
	public String guardarVoto(ModelMap modelMap, @PathVariable("id")int id, @PathVariable("idMatch") int idMatch, @PathVariable("voto") Vote voto) {
			Player player = playerService.findbyId(id);
			player.setVote(voto);
			playerService.savePlayer(player);
			Match match = matchService.findById(idMatch);
			List<Player> jugadores = match.getPlayers();
			int votos = playerService.calcularVotos(jugadores);
			matchService.votacionCompletada(votos, match);
			return "redirect:/matches/" + idMatch + "/match";
	}
	@PostMapping(path="/{id}/{idMatch}/{card1}/ElegirCartaFaccion1")
	public String elecciónCarta1(ModelMap modelMap, @PathVariable("id") int id, @PathVariable("idMatch") int idMatch,@PathVariable("card1") Faction card1) {
		Player player = playerService.findbyId(id);
		player.setCard1(card1);
		player.setCard2(Faction.DROPPED);
		playerService.savePlayer(player);
		return "redirect:/players/" + idMatch + "/NuevoTurno";
	}



	@PostMapping(path="/{id}/{idMatch}/{card2}/ElegirCartaFaccion2")
	public String elecciónCarta2(ModelMap modelMap, @PathVariable("id") int id,@PathVariable("idMatch") int idMatch ,@PathVariable("card2") Faction card2) {
		Player player = playerService.findbyId(id);
		player.setCard1(card2);
		player.setCard2(Faction.DROPPED);
		playerService.savePlayer(player);
		return "redirect:/players/" + idMatch + "/NuevoTurno";
	}
	@GetMapping(path="/{idMatch}/NuevoTurno")
	public String nuevoTurnoGet(ModelMap modelMap, @PathVariable("idMatch") int idMatch) {
		Match match = matchService.findById(idMatch);
		List<Player> jugadoresConVoto = playerService.jugadoresConVoto(Role.EDIL, match);
		jugadoresConVoto.get(0).setVote(null);
		jugadoresConVoto.get(1).setVote(null);
		List<Player> jugadores = match.getPlayers();
		List<Role> roles = new ArrayList<Role>();
		playerService.asignarRoles(match, jugadores, roles);
		matchService.avanzarTurno(match, jugadores);
		matchService.saveMatch(match);
		Faction sufragium = matchService.sufragium(match);
		if (match.getRound() == 3 || sufragium != Faction.MERCHANT) {
			return "redirect:/matches/" + idMatch + "/ganador";
		} else {
			return "redirect:/matches/" +idMatch + "/match";
		}
	}
	@PostMapping(path="/{idMatch}/NuevoTurno")
	public String nuevoTurnoPost(ModelMap modelMap, @PathVariable("idMatch") int idMatch) {
		Match match = matchService.findById(idMatch);
		List<Player> jugadoresConVoto = playerService.jugadoresConVoto(Role.EDIL, match);
		jugadoresConVoto.get(0).setVote(null);
		jugadoresConVoto.get(1).setVote(null);
		List<Player> jugadores = match.getPlayers();
		List<Role> roles = new ArrayList<Role>();
		playerService.asignarRoles(match, jugadores, roles);
		matchService.avanzarTurno(match, jugadores);
		matchService.saveMatch(match);
		if (match.getRound() == 3 || matchService.sufragium(match) != Faction.MERCHANT) {
			return "redirect:/matches/" + idMatch + "/ganador";
		} else {
			return "redirect:/matches/" +idMatch + "/match";
		}
	}
	@PostMapping(path="/{id}/{idMatch}/cambiarVoto")
	public String cambiarVoto(ModelMap modelMap, @PathVariable("id") int id, @PathVariable("idMatch") int idMatch) {
		Match match = matchService.findById(idMatch);
		Player player = playerService.findbyId(id);
		playerService.changeVote(player);
		match.setPlays(Plays.CONSUL);
		matchService.saveMatch(match);
		return "redirect:/matches/" +idMatch + "/match";
	}
	
	@GetMapping(path="/{id}/{idMatch}/revisarVotoYellow")
	public String cambiarVotoYellow(ModelMap modelMap, @PathVariable("id") int id, @PathVariable("idMatch") int idMatch) {
		Match match = matchService.findById(idMatch);
		Player player = playerService.findbyId(id);
		match.setPlays(Plays.YELLOWEDIL);
		player.setVote(null);
		matchService.saveMatch(match);
		return "redirect:/matches/" +idMatch + "/match";
	}
	
	
	@PostMapping(path="/{id}/{idMatch}/noCambiarVoto")
	public String noCambiarVoto(ModelMap modelMap, @PathVariable("id") int id, @PathVariable("idMatch") int idMatch) {
		
		Match match = matchService.findById(idMatch);
		match.setPlays(Plays.CONSUL);
		matchService.saveMatch(match);
		
		return "redirect:/matches/" +idMatch + "/match";
	}
	//Añadir a seguridad que sea el jugador que debe entrar a estos sitios
	@PostMapping(path="/{id}/{idMatch}/asignarEdil")
	public String asignarEdil(ModelMap modelMap, @PathVariable("id") int id, @PathVariable("idMatch") int idMatch) {
		
		Player player = playerService.findbyId(id);
		player.setRole(Role.EDIL);
		player.setAsigned(true);
		playerService.savePlayer(player);
		Match match = matchService.findById(idMatch);
		if (!matchService.edilNotAsigned(match) && !matchService.pretorNotAsigned(match)) {
			return "redirect:/matches/" + idMatch + "/rolesAsignados";
		} else {
			return "redirect:/matches/" + idMatch + "/match";
		}
		
	}
	
	@PostMapping(path="/{id}/{idMatch}/asignarPretor")
	public String asignarPretor(ModelMap modelMap, @PathVariable("id") int id, @PathVariable("idMatch") int idMatch) {
		
		Player player = playerService.findbyId(id);
		player.setRole(Role.PRETOR);
		player.setAsigned(true);
		playerService.savePlayer(player);
		Match match = matchService.findById(idMatch);
		if (!matchService.edilNotAsigned(match) && !matchService.pretorNotAsigned(match)) {
			return "redirect:/matches/" + idMatch + "/rolesAsignados";
		} else {
			return "redirect:/matches/" + idMatch + "/match";
		}
	}
}



