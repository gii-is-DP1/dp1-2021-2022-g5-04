package org.springframework.samples.IdusMartii.web;



import java.util.List;

import javax.servlet.jsp.jstl.sql.Result;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.samples.IdusMartii.service.CurrentUserService;
import org.springframework.samples.IdusMartii.service.MatchService;
import org.springframework.samples.IdusMartii.service.PlayerService;
import org.springframework.samples.IdusMartii.service.UserService;
import org.springframework.samples.IdusMartii.enumerates.Faction;
import org.springframework.samples.IdusMartii.enumerates.Role;
import org.springframework.samples.IdusMartii.enumerates.Vote;
import org.springframework.samples.IdusMartii.model.Player;
import org.springframework.samples.IdusMartii.model.Match;

@Controller
@RequestMapping("/players")
public class PlayerController {
	
	@Autowired
	private PlayerService playerService;
	@Autowired
	private CurrentUserService currentUserService;
	@Autowired
	private UserService userService;

	@Autowired
	private MatchService matchService;

		
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
        Player player = playerService.findbyId(id);
        modelMap.addAttribute("idMatch", idMatch);
        modelMap.addAttribute("player", player);
        return vista;
    }
	
	@GetMapping(path="{id}/{idMatch}/votacion")
	public String usuarioVotado(ModelMap modelMap, @PathVariable("id") int id, @PathVariable("idMatch") int idMatch) {
		Match match = matchService.findById(idMatch);
		List<Player> jugadores = match.getPlayers();
		int i = 0;
		for (Player j: jugadores) {
			if (j.getVote() != null) {
				String nombre = j.getUser().getUsername();
				modelMap.addAttribute("usuario_votado", nombre);
				i += 1 ;
			}
		}
		if (i == 2) {
			match.setC(match.getC() + 1);
			matchService.saveMatch(match);
		}
		return "redirect:/matches/" + idMatch + "/match";
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

	@PostMapping(path="/{id}/{idMatch}/{voto}")
	public String guardarVoto(ModelMap modelMap, @PathVariable("id")int id, @PathVariable("idMatch") int idMatch, @PathVariable("voto") Vote voto) {
			Player player = playerService.findbyId(id);
			player.setVote(voto);
			playerService.savePlayer(player);
			return "redirect:/players/" + id + "/" + idMatch + "/votacion";
	}
	@PostMapping(path="/{id}/{idMatch}/{card1}/ElegirCartaFacción1")
	public String elecciónCarta1(ModelMap modelMap, @PathVariable("id") int id, @PathVariable("idMatch") int idMatch,@PathVariable("card1") Faction card1) {

		// String vista = "matches/listadoPartida";

	
			//match.setId(id);
	
		Player player = playerService.findbyId(id);
		player.setCard1(card1);
		player.setCard2(Faction.DROPPED);

		playerService.savePlayer(player);

		return "redirect:/matches/" + idMatch + "/match";
		
	}



	@PostMapping(path="/{id}/{idMatch}/{card2}/ElegirCartaFacción2")
	public String elecciónCarta2(ModelMap modelMap, @PathVariable("id") int id,@PathVariable("idMatch") int idMatch ,@PathVariable("card2") Faction card2) {

		// String vista = "matches/listadoPartida";

	
			//match.setId(id);
	
	
		Player player = playerService.findbyId(id);
		player.setCard1(card2);
		player.setCard2(Faction.DROPPED);

		playerService.savePlayer(player);
		return "redirect:/matches/" + idMatch + "/match";
		
	}
	@PostMapping(path="/{id}/{idMatch}/cambiarVoto")
	public String cambiarVoto(ModelMap modelMap, @PathVariable("id") int id, @PathVariable("idMatch") int idMatch) {
		Match match = matchService.findById(idMatch);
		Player player = playerService.findbyId(id);
		if (player.getVote() == Vote.RED) {
			player.setVote(Vote.GREEN);
		} else {
			player.setVote(Vote.RED);
		}
		playerService.savePlayer(player);
		match.setC(match.getC() + 1);
		matchService.saveMatch(match);
		
		
		
		return "redirect:/matches/" +idMatch + "/match";
	}
	@PostMapping(path="/{id}/{idMatch}/noCambiarVoto")
	public String noCambiarVoto(ModelMap modelMap, @PathVariable("id") int id, @PathVariable("idMatch") int idMatch) {
		Match match = matchService.findById(idMatch);
		match.setC(match.getC() + 1);
		matchService.saveMatch(match);
		
		return "redirect:/matches/" +idMatch + "/match";
	}
	
	@PostMapping(path="/{id}/{idMatch}/NuevoTurno")
	public String NuevoTurno(ModelMap modelMap, @PathVariable("id") int id, @PathVariable("idMatch") int idMatch) {
		Match match = matchService.findById(idMatch);
		match.setC(0);
		match.setTurn(match.getTurn()+1);
		matchService.saveMatch(match);
		
		return "redirect:/matches/" +idMatch + "/match";
	}
	@PostMapping(path="/{id}/{idMatch}/asignarEdil")
	public String asignarEdil(ModelMap modelMap, @PathVariable("id") int id, @PathVariable("idMatch") int idMatch) {
		
		Player player = playerService.findbyId(id);
		player.setRole(Role.EDIL);
		playerService.savePlayer(player);
		
		return "redirect:/matches/" + idMatch + "/match";
		
	}
	
	@PostMapping(path="/{id}/{idMatch}/asignarPretor")
	public String asignarPretor(ModelMap modelMap, @PathVariable("id") int id, @PathVariable("idMatch") int idMatch) {
		
		Player player = playerService.findbyId(id);
		player.setRole(Role.PRETOR);
		playerService.savePlayer(player);
		
		return "redirect:/matches/" + idMatch + "/match";
		
	}
}



