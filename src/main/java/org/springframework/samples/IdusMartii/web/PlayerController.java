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
import org.springframework.samples.IdusMartii.repository.PlayerRepository;
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
			Match match = matchService.findById(idMatch);
			List<Player> jugadores = match.getPlayers();
			int i = 0;
			for (Player j: jugadores) {
				if (j.getVote() != null) {
					i += 1;
				}
			}
			if (i == 2) {
				match.setC(match.getC() + 1);
				matchService.saveMatch(match);
			}
			
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
	public String nuevoTurno(ModelMap modelMap, @PathVariable("idMatch") int idMatch) {
		Match match = matchService.findById(idMatch);
		
		List<Player> jugadoresConVoto = playerService.findByRole(Role.EDIL);
		for (Player j: jugadoresConVoto) {
			if(j.getVote() == Vote.GREEN) {
				match.setVotoaFavor(match.getVotoaFavor() + 1);
			} else if (j.getVote() == Vote.RED) {
				match.setVotoenContra(match.getVotoenContra() + 1);
			}
		}
		jugadoresConVoto.get(0).setVote(null);
		playerService.savePlayer(jugadoresConVoto.get(0));
		jugadoresConVoto.get(1).setVote(null);
		playerService.savePlayer(jugadoresConVoto.get(1));
		List<Player> jugadores = match.getPlayers();
		int i = 0;
		for (int j = 0; j == jugadores.size() - 1; j++) {
			if (jugadores.get(j).getRole() == Role.CONSUL) {
				i = j;
			}
			if (jugadores.get(j).getRole() != Role.NO_ROL) {
				Player jugador = jugadores.get(j);
				jugador.setRole(Role.NO_ROL);
				playerService.savePlayer(jugador);
			}
		}
		for (int k = 1; k == 4; k++){
			if ((i + k) >= jugadores.size()) {
				i = 0;
			}
			if (k == 1) {
				Player jugador = jugadores.get(i+k);
				jugador.setRole(Role.CONSUL);
				playerService.savePlayer(jugador);
			} else if (k == 2) {
				Player jugador = jugadores.get(i+k);
				jugador.setRole(Role.PRETOR);
				playerService.savePlayer(jugador);
			} else if (k == 3) {
				Player jugador = jugadores.get(i+k);
				jugador.setRole(Role.EDIL);
				playerService.savePlayer(jugador);
			} else if (k == 4) {
				Player jugador = jugadores.get(i+k);
				jugador.setRole(Role.EDIL);
				playerService.savePlayer(jugador);
			}
		}
		if ((match.getTurn() + 1) >= jugadores.size()){
			match.setRound(match.getRound() + 1);
			match.setTurn(0);
			match.setC(0);
		} else {
			match.setTurn(match.getTurn() + 1);
			match.setC(0);
		}
		matchService.saveMatch(match);
		
		if (match.getRound() == 2) {
			return "redirect:/matches/" + idMatch + "/ganador";
		} else {
			return "redirect:/matches/" +idMatch + "/match";
		}
	}
	@PostMapping(path="/{idMatch}/NuevoTurno")
	public String nuevoTurno2(ModelMap modelMap, @PathVariable("idMatch") int idMatch) {
		Match match = matchService.findById(idMatch);
		
		List<Player> jugadoresConVoto = playerService.findByRole(Role.EDIL);
		for (Player j: jugadoresConVoto) {
			if(j.getVote() == Vote.GREEN) {
				match.setVotoaFavor(match.getVotoaFavor() + 1);
			} else if (j.getVote() == Vote.RED) {
				match.setVotoenContra(match.getVotoenContra() + 1);
			}
		}
		jugadoresConVoto.get(0).setVote(null);
		playerService.savePlayer(jugadoresConVoto.get(0));
		jugadoresConVoto.get(1).setVote(null);
		playerService.savePlayer(jugadoresConVoto.get(1));
		List<Player> jugadores = match.getPlayers();
		int i = 0;
		for (int j = 0; j == jugadores.size() - 1; j++) {
			if (jugadores.get(j).getRole() == Role.CONSUL) {
				i = j;
			}
			if (jugadores.get(j).getRole() != Role.NO_ROL) {
				Player jugador = jugadores.get(j);
				jugador.setRole(Role.NO_ROL);
				playerService.savePlayer(jugador);
			}
		}
		for (int k = 1; k == 4; k++){
			if ((i + k) >= jugadores.size()) {
				i = 0;
			}
			if (k == 1) {
				Player jugador = jugadores.get(i+k);
				jugador.setRole(Role.CONSUL);
				playerService.savePlayer(jugador);
			} else if (k == 2) {
				Player jugador = jugadores.get(i+k);
				jugador.setRole(Role.PRETOR);
				playerService.savePlayer(jugador);
			} else if (k == 3) {
				Player jugador = jugadores.get(i+k);
				jugador.setRole(Role.EDIL);
				playerService.savePlayer(jugador);
			} else if (k == 4) {
				Player jugador = jugadores.get(i+k);
				jugador.setRole(Role.EDIL);
				playerService.savePlayer(jugador);
			}
		}
		if ((match.getTurn() + 1) >= jugadores.size()){
			match.setRound(match.getRound() + 1);
			match.setTurn(0);
			match.setC(0);
		} else {
			match.setTurn(match.getTurn() + 1);
			match.setC(0);
		}
		matchService.saveMatch(match);
		
		if (match.getRound() == 2) {
			return "redirect:/matches/" + idMatch + "/ganador";
		} else {
			return "redirect:/matches/" +idMatch + "/match";
		}
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



